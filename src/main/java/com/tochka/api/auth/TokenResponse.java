package com.tochka.api.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

/**
 * Response of the {@code /connect/token} endpoint.
 *
 * @param accessToken  access token for API methods; lives for 24 hours
 * @param refreshToken refresh token; lives for 30 days. Absent in the client_credentials flow
 * @param tokenType    token type, always {@code bearer}
 * @param expiresIn    lifetime of the access token in seconds
 * @param state        arbitrary string passed when the consent was requested
 * @param userId       id of the user who approved the permissions
 * @param obtainedAt   the moment the token was obtained — {@link #expiresAt()} is based on it
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

    /** The moment the access token expires. */
    public Optional<Instant> expiresAt() {
        if (expiresIn == null) {
            return Optional.empty();
        }
        Instant base = obtainedAt == null ? Instant.now() : obtainedAt;
        return Optional.of(base.plusSeconds(expiresIn));
    }

    /**
     * Whether the token has expired, taking a safety margin into account.
     *
     * @param safetyMargin margin that makes the token count as expired early, to avoid a race
     */
    public boolean isExpired(Duration safetyMargin) {
        return expiresAt()
                .map(at -> Instant.now().isAfter(at.minus(safetyMargin)))
                .orElse(false);
    }

    /** A copy with the moment of acquisition filled in. */
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
