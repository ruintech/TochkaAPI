package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringChargeSubscriptionRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringChargeSubscriptionRequestDataModel(
        @JsonProperty("Data") AcquiringChargeSubscriptionRequestModel data) {

    /** Строитель {@link AcquiringChargeSubscriptionRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link AcquiringChargeSubscriptionRequestDataModel}. */
    public static final class Builder {

        private AcquiringChargeSubscriptionRequestModel data;

        public Builder data(AcquiringChargeSubscriptionRequestModel data) {
            this.data = data;
            return this;
        }

        public AcquiringChargeSubscriptionRequestDataModel build() {
            return new AcquiringChargeSubscriptionRequestDataModel(this.data);
        }
    }
}
