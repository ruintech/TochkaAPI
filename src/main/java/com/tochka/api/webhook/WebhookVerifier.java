package com.tochka.api.webhook;

import com.fasterxml.jackson.databind.JsonNode;
import com.tochka.api.exception.TochkaException;
import com.tochka.api.http.Json;
import com.tochka.api.tls.RussianTrustedCa;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;

/**
 * Проверяет подпись вебхука Точка Банка и превращает его в типизированное событие.
 *
 * <p>В теле вебхука приходит не JSON, а «голая» строка JWT, подписанная алгоритмом RS256
 * (заголовок {@code Content-Type: text/plain}). Доверять данным можно только после проверки
 * подписи публичным ключом банка — иначе запрос мог прислать кто угодно.
 *
 * <p>По умолчанию ключ берётся с {@value #TOCHKA_PUBLIC_KEY_URL}, кешируется на
 * {@link Builder#keyCacheTtl(Duration)} и автоматически перечитывается, если подпись перестала
 * сходиться, — так интеграция переживёт смену ключа банком. Поэтому обычно достаточно:
 *
 * <pre>{@code
 * WebhookVerifier verifier = WebhookVerifier.usingTochkaPublicKey();
 *
 * // тело запроса — строка JWT целиком, Content-Type: text/plain
 * WebhookEvent event = verifier.verify(requestBody);
 * }</pre>
 *
 * <p>Если приложение не должно ходить за ключом в рантайме, задайте его явно —
 * {@link Builder#publicKeyPem(String)} или {@link Builder#publicKey(PublicKey)}. Тогда следите
 * за сменой ключа сами: подписи перестанут сходиться, а обновить ключ будет некому.
 *
 * <p>Отвечайте банку кодом 200: на любой другой ответ вебхук будет повторён 30 раз с
 * интервалом 10 секунд.
 */
public final class WebhookVerifier {

    /** Адрес, по которому Точка публикует публичный ключ вебхуков (JWK). */
    public static final String TOCHKA_PUBLIC_KEY_URL = "https://enter.tochka.com/doc/openapi/static/keys/public";

    private static final Base64.Decoder URL_DECODER = Base64.getUrlDecoder();

    private final PublicKey staticKey;
    private final URI keyUrl;
    private final Duration keyCacheTtl;
    private final HttpClient httpClient;

    private volatile PublicKey cachedKey;
    private volatile Instant cachedAt;

    private WebhookVerifier(Builder builder) {
        this.staticKey = builder.publicKey;
        this.keyUrl = builder.publicKeyUrl;
        this.keyCacheTtl = builder.keyCacheTtl;
        this.httpClient = builder.httpClient != null ? builder.httpClient : defaultHttpClient(builder.sslContext);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Проверяющий с ключом, опубликованным банком по адресу {@value #TOCHKA_PUBLIC_KEY_URL}.
     * Ключ скачивается при первой проверке и кешируется на 6 часов.
     */
    public static WebhookVerifier usingTochkaPublicKey() {
        return builder().build();
    }

    private static HttpClient defaultHttpClient(SSLContext sslContext) {
        // Ключ опубликован на enter.tochka.com, а это сертификаты Минцифры: без них рукопожатие
        // оборвётся на PKIX ещё до запроса.
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .sslContext(sslContext != null ? sslContext : RussianTrustedCa.sslContext())
                .build();
    }

    /**
     * Проверяет подпись и разбирает событие.
     *
     * @param jwt тело запроса — строка JWT целиком
     * @return типизированное событие; неизвестный тип вернётся как {@link UnknownWebhookEvent}
     * @throws WebhookVerificationException если подпись не сходится или формат некорректен
     */
    public WebhookEvent verify(String jwt) {
        JsonNode payload = verifyToJson(jwt);
        try {
            return Json.mapper().treeToValue(payload, WebhookEvent.class);
        } catch (IOException e) {
            throw new WebhookVerificationException("Не удалось разобрать полезную нагрузку вебхука", e);
        }
    }

    /**
     * Проверяет подпись и возвращает полезную нагрузку как дерево JSON — на случай, если нужны
     * поля, которых ещё нет в типизированных событиях.
     */
    public JsonNode verifyToJson(String jwt) {
        String token = jwt == null ? "" : jwt.trim();
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new WebhookVerificationException(
                    "Тело вебхука не является JWT: ожидались три части, получено " + parts.length);
        }

        JsonNode header = decodeJson(parts[0], "заголовок");
        String algorithm = header.path("alg").asText("");
        if (!"RS256".equals(algorithm)) {
            throw new WebhookVerificationException(
                    "Неподдерживаемый алгоритм подписи вебхука: " + (algorithm.isEmpty() ? "не указан" : algorithm));
        }

        byte[] signedData = (parts[0] + '.' + parts[1]).getBytes(StandardCharsets.US_ASCII);
        byte[] signature = decodeBase64Url(parts[2], "подпись");

        if (!verifySignature(resolveKey(false), signedData, signature)) {
            // Заданный вручную ключ перечитывать неоткуда — в сеть ходим только за скачанным.
            boolean verifiedAfterRefresh = staticKey == null
                    && verifySignature(resolveKey(true), signedData, signature);
            if (!verifiedAfterRefresh) {
                throw new WebhookVerificationException(
                        "Подпись вебхука не сходится — запрос пришёл не от Точка Банка");
            }
        }
        return decodeJson(parts[1], "полезная нагрузка");
    }

    private boolean verifySignature(PublicKey key, byte[] signedData, byte[] signature) {
        try {
            Signature verifier = Signature.getInstance("SHA256withRSA");
            verifier.initVerify(key);
            verifier.update(signedData);
            return verifier.verify(signature);
        } catch (GeneralSecurityException e) {
            throw new WebhookVerificationException("Не удалось проверить подпись вебхука", e);
        }
    }

    private PublicKey resolveKey(boolean forceRefresh) {
        if (staticKey != null) {
            return staticKey;
        }
        PublicKey key = cachedKey;
        boolean expired = cachedAt == null || Instant.now().isAfter(cachedAt.plus(keyCacheTtl));
        if (key == null || expired || forceRefresh) {
            synchronized (this) {
                if (forceRefresh || cachedKey == null
                        || cachedAt == null || Instant.now().isAfter(cachedAt.plus(keyCacheTtl))) {
                    cachedKey = fetchKey();
                    cachedAt = Instant.now();
                }
                key = cachedKey;
            }
        }
        return key;
    }

    private PublicKey fetchKey() {
        HttpRequest request = HttpRequest.newBuilder(keyUrl)
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();
        try {
            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() / 100 != 2) {
                throw new WebhookVerificationException(
                        "Не удалось получить публичный ключ по адресу " + keyUrl
                                + ": HTTP " + response.statusCode());
            }
            return parseKey(response.body());
        } catch (IOException e) {
            throw new WebhookVerificationException("Не удалось получить публичный ключ по адресу " + keyUrl, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WebhookVerificationException("Получение публичного ключа прервано", e);
        }
    }

    /**
     * Разбирает публичный ключ из PEM (в том числе из сертификата) или из JWKS.
     *
     * @param material содержимое PEM-файла или JSON вида {@code {"keys":[{"kty":"RSA",...}]}}
     */
    public static PublicKey parseKey(String material) {
        String trimmed = material == null ? "" : material.trim();
        if (trimmed.startsWith("{")) {
            return parseJwks(trimmed);
        }
        return parsePem(trimmed);
    }

    private static PublicKey parsePem(String pem) {
        if (pem.contains("BEGIN CERTIFICATE")) {
            try {
                CertificateFactory factory = CertificateFactory.getInstance("X.509");
                X509Certificate certificate = (X509Certificate) factory.generateCertificate(
                        new ByteArrayInputStream(pem.getBytes(StandardCharsets.UTF_8)));
                return certificate.getPublicKey();
            } catch (GeneralSecurityException e) {
                throw new TochkaException("Не удалось прочитать сертификат с публичным ключом", e);
            }
        }
        String base64 = pem
                .replaceAll("-----BEGIN (RSA )?PUBLIC KEY-----", "")
                .replaceAll("-----END (RSA )?PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        if (base64.isEmpty()) {
            throw new TochkaException("Публичный ключ пуст: ожидался PEM или JWKS");
        }
        try {
            byte[] der = Base64.getDecoder().decode(base64);
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(der));
        } catch (GeneralSecurityException | IllegalArgumentException e) {
            throw new TochkaException("Не удалось разобрать публичный ключ в формате PEM", e);
        }
    }

    private static PublicKey parseJwks(String json) {
        try {
            JsonNode root = Json.mapper().readTree(json);
            JsonNode key = root.has("keys") ? root.get("keys").path(0) : root;
            String modulus = key.path("n").asText(null);
            String exponent = key.path("e").asText(null);
            if (modulus == null || exponent == null) {
                throw new TochkaException("В JWKS нет полей n и e");
            }
            BigInteger n = new BigInteger(1, URL_DECODER.decode(modulus));
            BigInteger e = new BigInteger(1, URL_DECODER.decode(exponent));
            return KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(n, e));
        } catch (IOException | GeneralSecurityException | IllegalArgumentException e) {
            throw new TochkaException("Не удалось разобрать публичный ключ в формате JWKS", e);
        }
    }

    private static JsonNode decodeJson(String part, String what) {
        try {
            return Json.mapper().readTree(decodeBase64Url(part, what));
        } catch (IOException e) {
            throw new WebhookVerificationException("Не удалось разобрать " + what + " вебхука", e);
        }
    }

    private static byte[] decodeBase64Url(String value, String what) {
        try {
            return URL_DECODER.decode(value);
        } catch (IllegalArgumentException e) {
            throw new WebhookVerificationException(
                    "Не удалось декодировать " + what + " вебхука из base64url", e);
        }
    }

    /** Строитель {@link WebhookVerifier}. */
    public static final class Builder {
        private PublicKey publicKey;
        private URI publicKeyUrl = URI.create(TOCHKA_PUBLIC_KEY_URL);
        private Duration keyCacheTtl = Duration.ofHours(6);
        private HttpClient httpClient;
        private SSLContext sslContext;

        /** Готовый публичный ключ. */
        public Builder publicKey(PublicKey publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        /** Публичный ключ в формате PEM — как есть, вместе со строками {@code BEGIN}/{@code END}. */
        public Builder publicKeyPem(String pem) {
            this.publicKey = parseKey(pem);
            return this;
        }

        /**
         * Адрес, по которому опубликован публичный ключ (PEM или JWK). По умолчанию —
         * {@value WebhookVerifier#TOCHKA_PUBLIC_KEY_URL}. Ключ кешируется на
         * {@link #keyCacheTtl(Duration)} и перечитывается, если подпись перестала сходиться.
         */
        public Builder publicKeyUrl(URI publicKeyUrl) {
            this.publicKeyUrl = Objects.requireNonNull(publicKeyUrl, "publicKeyUrl");
            return this;
        }

        /** Время жизни кеша ключа; по умолчанию 6 часов. */
        public Builder keyCacheTtl(Duration keyCacheTtl) {
            this.keyCacheTtl = keyCacheTtl;
            return this;
        }

        /** Свой HTTP-клиент для загрузки ключа. */
        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * SSL-контекст для загрузки ключа. По умолчанию используется
         * {@link RussianTrustedCa#sslContext()} — без сертификатов Минцифры соединение с
         * {@code enter.tochka.com} не установится.
         */
        public Builder sslContext(SSLContext sslContext) {
            this.sslContext = sslContext;
            return this;
        }

        public WebhookVerifier build() {
            return new WebhookVerifier(this);
        }
    }
}
