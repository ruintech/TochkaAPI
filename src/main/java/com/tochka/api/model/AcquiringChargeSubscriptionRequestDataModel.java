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

    /** Builder for {@link AcquiringChargeSubscriptionRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Builder for {@link AcquiringChargeSubscriptionRequestDataModel}. */
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
