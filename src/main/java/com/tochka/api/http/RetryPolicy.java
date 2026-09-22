package com.tochka.api.http;

import java.time.Duration;
import java.util.Set;

/**
 * Политика повторов запроса.
 *
 * <p>По умолчанию повторяются только идемпотентные методы ({@code GET}, {@code PUT},
 * {@code DELETE}) — повтор {@code POST} может создать второй платёж или вторую платёжную
 * ссылку, если первый запрос на самом деле дошёл до банка, а потерялся только ответ.
 * Исключение — код 429: он означает, что запрос точно не был обработан, поэтому повторяется
 * при любом методе.
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

    /** Три попытки, экспоненциальная пауза от 500 мс, повтор на 429/500/502/503/504 и сетевых сбоях. */
    public static RetryPolicy defaults() {
        return builder().build();
    }

    /** Повторы выключены. */
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
     * Стоит ли повторить запрос, завершившийся ответом с указанным статусом.
     *
     * @param attempt    номер завершившейся попытки, начиная с 1
     * @param method     HTTP-метод запроса
     * @param statusCode статус ответа
     */
    public boolean shouldRetry(int attempt, String method, int statusCode) {
        if (attempt >= maxAttempts || !retryableStatuses.contains(statusCode)) {
            return false;
        }
        return statusCode == 429 || isRetryableMethod(method);
    }

    /**
     * Стоит ли повторить запрос, оборвавшийся на сетевой ошибке.
     *
     * @param attempt номер завершившейся попытки, начиная с 1
     * @param method  HTTP-метод запроса
     */
    public boolean shouldRetryAfterIoError(int attempt, String method) {
        return attempt < maxAttempts && isRetryableMethod(method);
    }

    private boolean isRetryableMethod(String method) {
        return retryNonIdempotent || IDEMPOTENT_METHODS.contains(method.toUpperCase());
    }

    /**
     * Пауза перед следующей попыткой.
     *
     * @param attempt    номер завершившейся попытки, начиная с 1
     * @param retryAfter значение заголовка {@code Retry-After} в секундах, либо {@code null}
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

    /** Строитель политики повторов. */
    public static final class Builder {
        private int maxAttempts = 3;
        private Duration initialBackoff = Duration.ofMillis(500);
        private Duration maxBackoff = Duration.ofSeconds(10);
        private double multiplier = 2.0;
        private Set<Integer> retryableStatuses = Set.of(429, 500, 502, 503, 504);
        private boolean retryNonIdempotent = false;

        /** Общее количество попыток, включая первую. Значение 1 отключает повторы. */
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
         * Разрешает повтор неидемпотентных методов ({@code POST}). Включайте осознанно: повтор
         * создания платежа или платёжной ссылки может продублировать операцию.
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
