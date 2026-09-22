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
 * Verifies the signature of a Tochka Bank webhook and turns it into a typed event.
 *
 * <p>A webhook body is not JSON but a bare JWT string signed with RS256 (the header says
 * {@code Content-Type: text/plain}). The data may only be trusted after the signature is checked
 * against the bank public key — otherwise anyone could have sent the request.
 *
 * <p>By default the key is taken from {@value #TOCHKA_PUBLIC_KEY_URL}, cached for
 * {@link Builder#keyCacheTtl(Duration)} and re-read automatically once a signature stops
 * matching, so the integration survives a key rotation. Usually this is all it takes:
 *
 * <pre>{@code
 * WebhookVerifier verifier = WebhookVerifier.usingTochkaPublicKey();
 *
 * // the request body is the whole JWT string, Content-Type: text/plain
 * WebhookEvent event = verifier.verify(requestBody);
 * }</pre>
 *
 * <p>If the application must not fetch anything at runtime, set the key explicitly with
 * {@link Builder#publicKeyPem(String)} or {@link Builder#publicKey(PublicKey)}. Then watch for
 * key rotation yourself: signatures will simply stop matching and nothing will refresh the key.
 *
 * <p>Answer the bank with status 200: on any other response the webhook is repeated 30 times
 * at 10 second intervals.
 */
public final class WebhookVerifier {

    /** URL where Tochka publishes the webhook public key (JWK). */
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
     * A verifier using the key published by the bank at {@value #TOCHKA_PUBLIC_KEY_URL}. The key
     * is downloaded on the first verification and cached for 6 hours.
     */
    public static WebhookVerifier usingTochkaPublicKey() {
        return builder().build();
    }

    private static HttpClient defaultHttpClient(SSLContext sslContext) {
        // The key lives on enter.tochka.com, which serves Ministry of Digital Development
        // certificates: without them the handshake dies on PKIX before the request is sent.
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .sslContext(sslContext != null ? sslContext : RussianTrustedCa.sslContext())
                .build();
    }

    /**
     * Verifies the signature and parses the event.
     *
     * @param jwt the request body — the whole JWT string
     * @return a typed event; an unknown type comes back as {@link UnknownWebhookEvent}
     * @throws WebhookVerificationException if the signature does not match or the format is wrong
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
     * Verifies the signature and returns the payload as a JSON tree — for the case when fields
     * missing from the typed events are needed.
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
            // A manually supplied key has nowhere to be re-read from: only a downloaded one is refetched.
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
     * Parses a public key from PEM (including from a certificate) or from JWKS.
     *
     * @param material contents of a PEM file, or JSON such as {@code {"keys":[{"kty":"RSA",...}]}}
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

    /** Builder for {@link WebhookVerifier}. */
    public static final class Builder {
        private PublicKey publicKey;
        private URI publicKeyUrl = URI.create(TOCHKA_PUBLIC_KEY_URL);
        private Duration keyCacheTtl = Duration.ofHours(6);
        private HttpClient httpClient;
        private SSLContext sslContext;

        /** A ready public key. */
        public Builder publicKey(PublicKey publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        /** A public key in PEM format — as is, including the {@code BEGIN}/{@code END} lines. */
        public Builder publicKeyPem(String pem) {
            this.publicKey = parseKey(pem);
            return this;
        }

        /**
         * URL where the public key is published (PEM or JWK). Defaults to
         * {@value WebhookVerifier#TOCHKA_PUBLIC_KEY_URL}. The key is cached for
         * {@link #keyCacheTtl(Duration)} and re-read once a signature stops matching.
         */
        public Builder publicKeyUrl(URI publicKeyUrl) {
            this.publicKeyUrl = Objects.requireNonNull(publicKeyUrl, "publicKeyUrl");
            return this;
        }

        /** Key cache lifetime; 6 hours by default. */
        public Builder keyCacheTtl(Duration keyCacheTtl) {
            this.keyCacheTtl = keyCacheTtl;
            return this;
        }

        /** A custom HTTP client for downloading the key. */
        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * SSL context used to download the key. Defaults to {@link RussianTrustedCa#sslContext()}:
         * without the Ministry of Digital Development certificates a connection to
         * {@code enter.tochka.com} cannot be established.
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
