package com.tochka.api.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

/**
 * Ответ эндпоинта {@code /connect/token}.
 *
 * @param accessToken  токен доступа к методам API; живёт 24 часа
 * @param refreshToken токен обновления; живёт 30 дней. Отсутствует в потоке client_credentials
 * @param tokenType    тип токена, всегда {@code bearer}
 * @param expiresIn    срок жизни access-токена в секундах
 * @param state        произвольная строка, переданная при запросе подтверждения
 * @param userId       идентификатор пользователя, подтвердившего разрешения
 * @param obtainedAt   момент получения токена — по нему считается {@link #expiresAt()}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") Long expiresIn,
        @JsonProperty("state") String state,
        @JsonProperty("user_id") String userId,
        @JsonProperty("obtained_at") Instant obtainedAt) {

    /** Момент истечения access-токена. */
    public Optional<Instant> expiresAt() {
        if (expiresIn == null) {
            return Optional.empty();
        }
        Instant base = obtainedAt == null ? Instant.now() : obtainedAt;
        return Optional.of(base.plusSeconds(expiresIn));
    }

    /**
     * Истёк ли токен с учётом запаса.
     *
     * @param safetyMargin запас: токен считается истёкшим заранее, чтобы не попасть в гонку
     */
    public boolean isExpired(Duration safetyMargin) {
        return expiresAt()
                .map(at -> Instant.now().isAfter(at.minus(safetyMargin)))
                .orElse(false);
    }

    /** Копия с проставленным моментом получения. */
    public TokenResponse withObtainedAt(Instant instant) {
        return new TokenResponse(accessToken, refreshToken, tokenType, expiresIn, state, userId, instant);
    }

    @Override
    public String toString() {
        return "TokenResponse[tokenType=" + tokenType
                + ", expiresIn=" + expiresIn
                + ", accessToken=***"
                + (refreshToken == null ? "" : ", refreshToken=***")
                + (userId == null ? "" : ", userId=" + userId)
                + ']';
    }
}
