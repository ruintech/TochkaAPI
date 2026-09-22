package com.tochka.api.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.tochka.api.exception.TochkaTransportException;
import com.tochka.api.exception.TochkaUnauthorizedException;
import com.tochka.api.http.Json;
import com.tochka.api.tls.RussianTrustedCa;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Клиент OAuth 2.0 Точка Банка: эндпоинты {@code /connect/token}, {@code /connect/authorize}
 * и {@code /connect/introspect}.
 *
 * <p>Полный сценарий подключения:
 * <ol>
 *   <li>{@link #clientCredentials(List)} — токен для работы с разрешениями;</li>
 *   <li>создание списка разрешений методом {@code consents().createNewConsent(...)};</li>
 *   <li>{@link #authorizationUrl(String, String, List, String)} — ссылка, по которой клиент
 *       подтверждает разрешения и возвращается с параметром {@code code};</li>
 *   <li>{@link #exchangeCode(String, String, List)} — обмен кода на пару токенов;</li>
 *   <li>{@link #refresh(String)} — обновление пары раз в сутки.</li>
 * </ol>
 */
public final class OAuth2Client {

    /** Базовый адрес OAuth-эндпоинтов Точка Банка. */
    public static final URI DEFAULT_CONNECT_URI = URI.create("https://enter.tochka.com/connect/");

    private final URI connectUri;
    private final String clientId;
    private final String clientSecret;
    private final HttpClient httpClient;

    private OAuth2Client(Builder builder) {
        this.connectUri = builder.connectUri;
        this.clientId = Objects.requireNonNull(builder.clientId, "clientId");
        this.clientSecret = Objects.requireNonNull(builder.clientSecret, "clientSecret");
        this.httpClient = builder.httpClient != null ? builder.httpClient : defaultHttpClient(builder.sslContext);
    }

    public static Builder builder() {
        return new Builder();
    }

    private static HttpClient defaultHttpClient(SSLContext sslContext) {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .followRedirects(HttpClient.Redirect.NEVER)
                .sslContext(sslContext != null ? sslContext : RussianTrustedCa.sslContext())
                .build();
    }

    /**
     * Поток {@code client_credentials}: приложение авторизуется само от себя. Полученный токен
     * подходит только для работы с разрешениями (создание и чтение consent).
     *
     * @param scopes области доступа, например {@code accounts}, {@code balances}, {@code sbp}
     */
    public TokenResponse clientCredentials(List<String> scopes) {
        Map<String, String> form = baseForm();
        form.put("grant_type", "client_credentials");
        putScope(form, scopes);
        return postToken(form);
    }

    /**
     * Ссылка, на которую нужно отправить клиента для подтверждения разрешений.
     *
     * @param consentId   идентификатор созданного списка разрешений
     * @param redirectUri адрес возврата; должен совпадать с указанным при регистрации приложения
     * @param scopes      области доступа — тот же набор, что и в остальных запросах потока
     * @param state       произвольная строка, которая вернётся вместе с кодом
     */
    public URI authorizationUrl(String consentId, String redirectUri, List<String> scopes, String state) {
        Map<String, String> query = new LinkedHashMap<>();
        query.put("client_id", clientId);
        query.put("response_type", "code");
        query.put("redirect_uri", redirectUri);
        if (state != null) {
            query.put("state", state);
        }
        putScope(query, scopes);
        query.put("consent_id", consentId);
        return connectUri.resolve("authorize?" + urlEncode(query));
    }

    /**
     * Обмен кода авторизации на пару токенов. Код живёт 5 минут.
     *
     * @param code        значение параметра {@code code}, пришедшее на {@code redirect_uri}
     * @param redirectUri тот же адрес, что и в ссылке подтверждения
     * @param scopes      тот же набор областей доступа, что и в ссылке подтверждения
     */
    public TokenResponse exchangeCode(String code, String redirectUri, List<String> scopes) {
        Map<String, String> form = baseForm();
        form.put("grant_type", "authorization_code");
        form.put("code", code);
        form.put("redirect_uri", redirectUri);
        putScope(form, scopes);
        return postToken(form);
    }

    /**
     * Обновление пары токенов. Дальше обновляйте по новому {@code refresh_token} — старый
     * перестаёт действовать.
     */
    public TokenResponse refresh(String refreshToken) {
        Map<String, String> form = baseForm();
        form.put("grant_type", "refresh_token");
        form.put("refresh_token", refreshToken);
        return postToken(form);
    }

    /**
     * Проверка токена. Банк отвечает JWT, в котором зашифрованы {@code customerCode} (claim
     * {@code sub}) и {@code client_id} (claim {@code aud}).
     *
     * @return тело ответа как есть — строка JWT
     */
    public String introspect(String accessToken) {
        Map<String, String> form = new LinkedHashMap<>();
        form.put("access_token", accessToken);
        HttpResponse<String> response = send(connectUri.resolve("introspect"), form);
        ensureSuccess(response);
        return response.body();
    }

    private Map<String, String> baseForm() {
        Map<String, String> form = new LinkedHashMap<>();
        form.put("client_id", clientId);
        form.put("client_secret", clientSecret);
        return form;
    }

    private static void putScope(Map<String, String> target, List<String> scopes) {
        if (scopes != null && !scopes.isEmpty()) {
            target.put("scope", String.join(" ", scopes));
        }
    }

    private TokenResponse postToken(Map<String, String> form) {
        HttpResponse<String> response = send(connectUri.resolve("token"), form);
        ensureSuccess(response);
        try {
            JsonNode node = Json.mapper().readTree(response.body());
            TokenResponse token = Json.mapper().treeToValue(node, TokenResponse.class);
            return token.withObtainedAt(Instant.now());
        } catch (IOException e) {
            throw new TochkaTransportException("Не удалось разобрать ответ /connect/token", e);
        }
    }

    private HttpResponse<String> send(URI uri, Map<String, String> form) {
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(urlEncode(form), StandardCharsets.UTF_8))
                .build();
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new TochkaTransportException("Запрос к " + uri + " не выполнен", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TochkaTransportException("Запрос к " + uri + " прерван", e);
        }
    }

    private static void ensureSuccess(HttpResponse<String> response) {
        if (response.statusCode() / 100 != 2) {
            throw new TochkaUnauthorizedException(
                    response.statusCode(),
                    String.valueOf(response.statusCode()),
                    null,
                    "Ошибка OAuth: " + response.body(),
                    List.of(),
                    response.body());
        }
    }

    private static String urlEncode(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                    .append('=')
                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return sb.toString();
    }

    /** Строитель {@link OAuth2Client}. */
    public static final class Builder {
        private URI connectUri = DEFAULT_CONNECT_URI;
        private String clientId;
        private String clientSecret;
        private HttpClient httpClient;
        private SSLContext sslContext;

        /** Идентификатор приложения, выданный при регистрации в интернет-банке. */
        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        /** Пароль приложения. */
        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        /** Базовый адрес OAuth-эндпоинтов; по умолчанию {@link #DEFAULT_CONNECT_URI}. */
        public Builder connectUri(String connectUri) {
            String normalized = connectUri.endsWith("/") ? connectUri : connectUri + "/";
            this.connectUri = URI.create(normalized);
            return this;
        }

        /** Готовый HTTP-клиент, если нужен свой пул соединений или прокси. */
        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * SSL-контекст. По умолчанию — {@link RussianTrustedCa#sslContext()}: эндпоинты OAuth
         * живут на {@code enter.tochka.com} с сертификатами Минцифры.
         */
        public Builder sslContext(SSLContext sslContext) {
            this.sslContext = sslContext;
            return this;
        }

        public OAuth2Client build() {
            return new OAuth2Client(this);
        }
    }
}
