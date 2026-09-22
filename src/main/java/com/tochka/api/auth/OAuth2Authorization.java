package com.tochka.api.auth;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * OAuth 2.0 authorization that refreshes the access token automatically using the refresh token.
 *
 * <p>The access token lives for 24 hours, the refresh token for 30 days. The pair is renewed as
 * soon as less than {@link Builder#safetyMargin(Duration)} is left. A new refresh token arrives
 * together with the new access token and replaces the previous one, so persist it via
 * {@link Builder#onTokenRefreshed(Consumer)} — otherwise the consent has to be approved again
 * after an application restart.
 *
 * <p>The class is thread-safe: concurrent requests will not trigger several refreshes in a row.
 */
public final class OAuth2Authorization implements Authorization {

    private final OAuth2Client client;
    private final Duration safetyMargin;
    private final Consumer<TokenResponse> onTokenRefreshed;

    private TokenResponse token;

    private OAuth2Authorization(Builder builder) {
        this.client = Objects.requireNonNull(builder.client, "client");
        this.token = Objects.requireNonNull(builder.token, "token");
        this.safetyMargin = builder.safetyMargin;
        this.onTokenRefreshed = builder.onTokenRefreshed;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String authorizationHeader() {
        return "Bearer " + currentToken().accessToken();
    }

    /** The current token; it is refreshed inside this call when needed. */
    public synchronized TokenResponse currentToken() {
        if (token.isExpired(safetyMargin) && token.refreshToken() != null) {
            refresh();
        }
        return token;
    }

    /** Refreshes the token pair right away, without waiting for the current one to expire. */
    public synchronized TokenResponse refresh() {
        TokenResponse refreshed = client.refresh(token.refreshToken());
        this.token = refreshed;
        if (onTokenRefreshed != null) {
            onTokenRefreshed.accept(refreshed);
        }
        return refreshed;
    }

    /** Builder for {@link OAuth2Authorization}. */
    public static final class Builder {
        private OAuth2Client client;
        private TokenResponse token;
        private Duration safetyMargin = Duration.ofMinutes(5);
        private Consumer<TokenResponse> onTokenRefreshed;

        public Builder client(OAuth2Client client) {
            this.client = client;
            return this;
        }

        /** The token pair obtained from {@link OAuth2Client#exchangeCode}. */
        public Builder token(TokenResponse token) {
            this.token = token;
            return this;
        }

        /**
         * Restores state after a restart from a stored refresh token. The access token is
         * obtained on the very first request.
         */
        public Builder refreshToken(String refreshToken) {
            this.token = new TokenResponse(null, refreshToken, "bearer", 0L, null, null, null);
            return this;
        }

        /** How long before expiry the token is refreshed; 5 minutes by default. */
        public Builder safetyMargin(Duration safetyMargin) {
            this.safetyMargin = safetyMargin;
            return this;
        }

        /** Called after every refresh — the right place to persist the refresh token. */
        public Builder onTokenRefreshed(Consumer<TokenResponse> onTokenRefreshed) {
            this.onTokenRefreshed = onTokenRefreshed;
            return this;
        }

        public OAuth2Authorization build() {
            return new OAuth2Authorization(this);
        }
    }
}
