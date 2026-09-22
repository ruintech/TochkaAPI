package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringSubscriptionInputOptions
 *
 * @param trancheCount Количество списаний по подписке. Example: 12 (optional)
 * @param period Периодичность списания. Example: "Month" (optional)
 * @param daysInPeriod Длина периодичности в днях. Работает только с периодом Day. Example: 14 (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringSubscriptionInputOptions(
        @JsonProperty("trancheCount") Integer trancheCount,
        @JsonProperty("period") AcquiringSubscriptionPeriodInput period,
        @JsonProperty("daysInPeriod") Integer daysInPeriod) {

    /** Builder for {@link AcquiringSubscriptionInputOptions}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .trancheCount(this.trancheCount)
                .period(this.period)
                .daysInPeriod(this.daysInPeriod);
    }

    /** Builder for {@link AcquiringSubscriptionInputOptions}. */
    public static final class Builder {

        private Integer trancheCount;
        private AcquiringSubscriptionPeriodInput period;
        private Integer daysInPeriod;

        /** Количество списаний по подписке. Example: 12 */
        public Builder trancheCount(Integer trancheCount) {
            this.trancheCount = trancheCount;
            return this;
        }

        /** Периодичность списания. Example: "Month" */
        public Builder period(AcquiringSubscriptionPeriodInput period) {
            this.period = period;
            return this;
        }

        /** Длина периодичности в днях. Работает только с периодом Day. Example: 14 */
        public Builder daysInPeriod(Integer daysInPeriod) {
            this.daysInPeriod = daysInPeriod;
            return this;
        }

        public AcquiringSubscriptionInputOptions build() {
            return new AcquiringSubscriptionInputOptions(this.trancheCount, this.period, this.daysInPeriod);
        }
    }
}
