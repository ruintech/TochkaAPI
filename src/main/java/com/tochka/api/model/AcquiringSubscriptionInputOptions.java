package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringSubscriptionInputOptions
 *
 * @param trancheCount Количество списаний по подписке. Например: 12 (необязательное)
 * @param period Периодичность списания. Например: "Month" (необязательное)
 * @param daysInPeriod Длина периодичности в днях. Работает только с периодом Day. Например: 14 (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringSubscriptionInputOptions(
        @JsonProperty("trancheCount") Integer trancheCount,
        @JsonProperty("period") AcquiringSubscriptionPeriodInput period,
        @JsonProperty("daysInPeriod") Integer daysInPeriod) {

    /** Строитель {@link AcquiringSubscriptionInputOptions}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .trancheCount(this.trancheCount)
                .period(this.period)
                .daysInPeriod(this.daysInPeriod);
    }

    /** Строитель {@link AcquiringSubscriptionInputOptions}. */
    public static final class Builder {

        private Integer trancheCount;
        private AcquiringSubscriptionPeriodInput period;
        private Integer daysInPeriod;

        /** Количество списаний по подписке. Например: 12 */
        public Builder trancheCount(Integer trancheCount) {
            this.trancheCount = trancheCount;
            return this;
        }

        /** Периодичность списания. Например: "Month" */
        public Builder period(AcquiringSubscriptionPeriodInput period) {
            this.period = period;
            return this;
        }

        /** Длина периодичности в днях. Работает только с периодом Day. Например: 14 */
        public Builder daysInPeriod(Integer daysInPeriod) {
            this.daysInPeriod = daysInPeriod;
            return this;
        }

        public AcquiringSubscriptionInputOptions build() {
            return new AcquiringSubscriptionInputOptions(this.trancheCount, this.period, this.daysInPeriod);
        }
    }
}
