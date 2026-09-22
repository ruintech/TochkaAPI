package com.tochka.api;

import com.tochka.api.http.RetryPolicy;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RetryPolicyTest {

    @Test
    void retriesIdempotentMethodsOnServerErrors() {
        RetryPolicy policy = RetryPolicy.defaults();

        assertTrue(policy.shouldRetry(1, "GET", 503));
        assertTrue(policy.shouldRetry(2, "DELETE", 500));
        assertFalse(policy.shouldRetry(3, "GET", 500), "the third attempt is the last one");
        assertFalse(policy.shouldRetry(1, "GET", 400));
    }

    @Test
    void doesNotRetryPostExceptOnTooManyRequests() {
        RetryPolicy policy = RetryPolicy.defaults();

        assertFalse(policy.shouldRetry(1, "POST", 500));
        assertTrue(policy.shouldRetry(1, "POST", 429), "429 means the request was not processed");
    }

    @Test
    void retriesPostWhenExplicitlyAllowed() {
        RetryPolicy policy = RetryPolicy.builder().retryNonIdempotent(true).build();

        assertTrue(policy.shouldRetry(1, "POST", 500));
    }

    @Test
    void honoursRetryAfterHeaderWithinMaxBackoff() {
        RetryPolicy policy = RetryPolicy.builder().maxBackoff(Duration.ofSeconds(10)).build();

        assertEquals(Duration.ofSeconds(3), policy.backoff(1, 3L));
        assertEquals(Duration.ofSeconds(10), policy.backoff(1, 600L));
    }

    @Test
    void backoffGrowsAndStaysWithinLimit() {
        RetryPolicy policy = RetryPolicy.builder()
                .initialBackoff(Duration.ofMillis(100))
                .maxBackoff(Duration.ofMillis(500))
                .build();

        assertTrue(policy.backoff(1, null).toMillis() >= 100);
        assertTrue(policy.backoff(5, null).toMillis() <= 600);
    }

    @Test
    void noneDisablesRetries() {
        assertFalse(RetryPolicy.none().shouldRetry(1, "GET", 500));
        assertFalse(RetryPolicy.none().shouldRetryAfterIoError(1, "GET"));
    }
}
