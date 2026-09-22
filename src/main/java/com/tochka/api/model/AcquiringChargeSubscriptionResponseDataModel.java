package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringChargeSubscriptionResponseDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringChargeSubscriptionResponseDataModel(
        @JsonProperty("Data") BooleanResponse data) {

    /** Builder for {@link AcquiringChargeSubscriptionResponseDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Builder for {@link AcquiringChargeSubscriptionResponseDataModel}. */
    public static final class Builder {

        private BooleanResponse data;

        public Builder data(BooleanResponse data) {
            this.data = data;
            return this;
        }

        public AcquiringChargeSubscriptionResponseDataModel build() {
            return new AcquiringChargeSubscriptionResponseDataModel(this.data);
        }
    }
}
