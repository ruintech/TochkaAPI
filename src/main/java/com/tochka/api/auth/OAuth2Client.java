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
 * OAuth 2.0 client for Tochka Bank: the {@code /connect/token}, {@code /connect/authorize} and
 * {@code /connect/introspect} endpoints.
 *
 * <p>The full onboarding flow:
 * <ol>
 *   <li>{@link #clientCredentials(List)} — a token for working with consents;</li>
 *   <li>creating a consent via {@code consents().createNewConsent(...)};</li>
 *   <li>{@link #authorizationUrl(String, String, List, String)} — the link where the customer
 *       approves the permissions and comes back with a {@code code} parameter;</li>
 *   <li>{@link #exchangeCode(String, String, List)} — exchanging the code for a token pair;</li>
 *   <li>{@link #refresh(String)} — renewing the pair once a day.</li>
 * </ol>
 */
public final class OAuth2Client {

    /** Base URL of the Tochka Bank OAuth endpoints. */
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
     * The {@code client_credentials} flow: the application authorizes on its own behalf. The
     * resulting token is only good for working with consents (creating and reading them).
     *
     * @param scopes scopes, for example {@code accounts}, {@code balances}, {@code sbp}
     */
    public TokenResponse clientCredentials(List<String> scopes) {
        Map<String, String> form = baseForm();
        form.put("grant_type", "client_credentials");
        putScope(form, scopes);
        return postToken(form);
    }

    /**
     * The link the customer has to be sent to in order to approve the permissions.
     *
     * @param consentId   id of the created consent
     * @param redirectUri return address; must match the one registered with the application
     * @param scopes      scopes — the same set as in the other requests of the flow
     * @param state       arbitrary string that comes back together with the code
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
     * Exchanges an authorization code for a token pair. The code lives for 5 minutes.
     *
     * @param code        value of the {@code code} parameter delivered to {@code redirect_uri}
     * @param redirectUri the same address as in the approval link
     * @param scopes      the same set of scopes as in the approval link
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
     * Renews the token pair. Continue with the new {@code refresh_token}: the old one stops
     * working.
     */
    public TokenResponse refresh(String refreshToken) {
        Map<String, String> form = baseForm();
        form.put("grant_type", "refresh_token");
        form.put("refresh_token", refreshToken);
        return postToken(form);
    }

    /**
     * Introspects a token. The bank answers with a JWT carrying {@code customerCode} (the
     * {@code sub} claim) and {@code client_id} (the {@code aud} claim).
     *
     * @return the response body as is — a JWT string
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

    /** Builder for {@link OAuth2Client}. */
    public static final class Builder {
        private URI connectUri = DEFAULT_CONNECT_URI;
        private String clientId;
        private String clientSecret;
        private HttpClient httpClient;
        private SSLContext sslContext;

        /** Application id issued when the application was registered in the internet bank. */
        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        /** Application secret. */
        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        /** Base URL of the OAuth endpoints; {@link #DEFAULT_CONNECT_URI} by default. */
        public Builder connectUri(String connectUri) {
            String normalized = connectUri.endsWith("/") ? connectUri : connectUri + "/";
            this.connectUri = URI.create(normalized);
            return this;
        }

        /** A ready HTTP client, when a custom connection pool or proxy is needed. */
        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * SSL context. Defaults to {@link RussianTrustedCa#sslContext()}: the OAuth endpoints
         * live on {@code enter.tochka.com}, which serves Ministry of Digital Development certificates.
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
