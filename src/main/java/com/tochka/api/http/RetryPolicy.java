package com.tochka.api.http;

import java.time.Duration;
import java.util.Set;

/**
 * Retry policy for API requests.
 *
 * <p>By default only idempotent methods ({@code GET}, {@code PUT}, {@code DELETE}) are retried:
 * repeating a {@code POST} can create a second payment or a second payment link if the first
 * request did reach the bank and only the response was lost. Status 429 is the exception — it
 * means the request was definitely not processed, so it is retried for any method.
 */
public final class RetryPolicy {

    private static final Set<String> IDEMPOTENT_METHODS = Set.of("GET", "HEAD", "PUT", "DELETE", "OPTIONS");

    private final int maxAttempts;
    private final Duration initialBackoff;
    private final Duration maxBackoff;
    private final double multiplier;
    private final Set<Integer> retryableStatuses;
    private final boolean retryNonIdempotent;

    private RetryPolicy(Builder builder) {
        this.maxAttempts = builder.maxAttempts;
        this.initialBackoff = builder.initialBackoff;
        this.maxBackoff = builder.maxBackoff;
        this.multiplier = builder.multiplier;
        this.retryableStatuses = Set.copyOf(builder.retryableStatuses);
        this.retryNonIdempotent = builder.retryNonIdempotent;
    }

    /** Three attempts, exponential backoff from 500 ms, retries on 429/500/502/503/504 and network errors. */
    public static RetryPolicy defaults() {
        return builder().build();
    }

    /** Retries disabled. */
    public static RetryPolicy none() {
        return builder().maxAttempts(1).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public int maxAttempts() {
        return maxAttempts;
    }

    /**
     * Whether a request that came back with the given status should be retried.
     *
     * @param attempt    number of the attempt that just finished, starting at 1
     * @param method     HTTP method of the request
     * @param statusCode status of the response
     */
    public boolean shouldRetry(int attempt, String method, int statusCode) {
        if (attempt >= maxAttempts || !retryableStatuses.contains(statusCode)) {
            return false;
        }
        return statusCode == 429 || isRetryableMethod(method);
    }

    /**
     * Whether a request that failed with a network error should be retried.
     *
     * @param attempt number of the attempt that just finished, starting at 1
     * @param method  HTTP method of the request
     */
    public boolean shouldRetryAfterIoError(int attempt, String method) {
        return attempt < maxAttempts && isRetryableMethod(method);
    }

    private boolean isRetryableMethod(String method) {
        return retryNonIdempotent || IDEMPOTENT_METHODS.contains(method.toUpperCase());
    }

    /**
     * Delay before the next attempt.
     *
     * @param attempt    number of the attempt that just finished, starting at 1
     * @param retryAfter value of the {@code Retry-After} header in seconds, or {@code null}
     */
    public Duration backoff(int attempt, Long retryAfter) {
        if (retryAfter != null && retryAfter >= 0) {
            Duration fromHeader = Duration.ofSeconds(retryAfter);
            return fromHeader.compareTo(maxBackoff) > 0 ? maxBackoff : fromHeader;
        }
        double millis = initialBackoff.toMillis() * Math.pow(multiplier, attempt - 1.0);
        long capped = (long) Math.min(millis, maxBackoff.toMillis());
        long jitter = (long) (capped * 0.2 * Math.random());
        return Duration.ofMillis(capped + jitter);
    }

    /** Builder for the retry policy. */
    public static final class Builder {
        private int maxAttempts = 3;
        private Duration initialBackoff = Duration.ofMillis(500);
        private Duration maxBackoff = Duration.ofSeconds(10);
        private double multiplier = 2.0;
        private Set<Integer> retryableStatuses = Set.of(429, 500, 502, 503, 504);
        private boolean retryNonIdempotent = false;

        /** Total number of attempts including the first one. A value of 1 disables retries. */
        public Builder maxAttempts(int maxAttempts) {
            if (maxAttempts < 1) {
                throw new IllegalArgumentException("maxAttempts должен быть не меньше 1");
            }
            this.maxAttempts = maxAttempts;
            return this;
        }

        public Builder initialBackoff(Duration initialBackoff) {
            this.initialBackoff = initialBackoff;
            return this;
        }

        public Builder maxBackoff(Duration maxBackoff) {
            this.maxBackoff = maxBackoff;
            return this;
        }

        public Builder multiplier(double multiplier) {
            this.multiplier = multiplier;
            return this;
        }

        public Builder retryableStatuses(Set<Integer> retryableStatuses) {
            this.retryableStatuses = retryableStatuses;
            return this;
        }

        /**
         * Allows retrying non-idempotent methods ({@code POST}). Enable it deliberately:
         * repeating the creation of a payment or a payment link can duplicate the operation.
         */
        public Builder retryNonIdempotent(boolean retryNonIdempotent) {
            this.retryNonIdempotent = retryNonIdempotent;
            return this;
        }

        public RetryPolicy build() {
            return new RetryPolicy(this);
        }
    }
}
