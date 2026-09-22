package com.tochka.api.auth;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Авторизация по OAuth 2.0 с автоматическим обновлением access-токена по refresh-токену.
 *
 * <p>Access-токен живёт 24 часа, refresh — 30 дней. Клиент обновляет пару сам, когда до
 * истечения остаётся меньше {@link Builder#safetyMargin(Duration)}. Новый refresh-токен
 * приходит вместе с новым access-токеном и заменяет прежний, поэтому сохраняйте его через
 * {@link Builder#onTokenRefreshed(Consumer)} — иначе после перезапуска приложения придётся
 * заново проходить подтверждение разрешений.
 *
 * <p>Класс потокобезопасен: одновременные запросы не приведут к нескольким обновлениям подряд.
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

    /** Текущий токен; при необходимости он будет обновлён прямо в этом вызове. */
    public synchronized TokenResponse currentToken() {
        if (token.isExpired(safetyMargin) && token.refreshToken() != null) {
            refresh();
        }
        return token;
    }

    /** Принудительно обновляет пару токенов, не дожидаясь истечения текущей. */
    public synchronized TokenResponse refresh() {
        TokenResponse refreshed = client.refresh(token.refreshToken());
        this.token = refreshed;
        if (onTokenRefreshed != null) {
            onTokenRefreshed.accept(refreshed);
        }
        return refreshed;
    }

    /** Строитель {@link OAuth2Authorization}. */
    public static final class Builder {
        private OAuth2Client client;
        private TokenResponse token;
        private Duration safetyMargin = Duration.ofMinutes(5);
        private Consumer<TokenResponse> onTokenRefreshed;

        public Builder client(OAuth2Client client) {
            this.client = client;
            return this;
        }

        /** Пара токенов, полученная методом {@link OAuth2Client#exchangeCode}. */
        public Builder token(TokenResponse token) {
            this.token = token;
            return this;
        }

        /**
         * Восстановление состояния после перезапуска: сохранённый refresh-токен. Access-токен
         * будет получен при первом же запросе.
         */
        public Builder refreshToken(String refreshToken) {
            this.token = new TokenResponse(null, refreshToken, "bearer", 0L, null, null, null);
            return this;
        }

        /** За сколько до истечения обновлять токен; по умолчанию 5 минут. */
        public Builder safetyMargin(Duration safetyMargin) {
            this.safetyMargin = safetyMargin;
            return this;
        }

        /** Вызывается после каждого обновления — сюда стоит повесить сохранение refresh-токена. */
        public Builder onTokenRefreshed(Consumer<TokenResponse> onTokenRefreshed) {
            this.onTokenRefreshed = onTokenRefreshed;
            return this;
        }

        public OAuth2Authorization build() {
            return new OAuth2Authorization(this);
        }
    }
}
