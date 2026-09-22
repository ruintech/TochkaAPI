package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringCreateSubscriptionWithReceiptRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringCreateSubscriptionWithReceiptRequestDataModel(
        @JsonProperty("Data") AcquiringCreateSubscriptionWithReceiptRequestModel data) {

    /** Builder for {@link AcquiringCreateSubscriptionWithReceiptRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Builder for {@link AcquiringCreateSubscriptionWithReceiptRequestDataModel}. */
    public static final class Builder {

        private AcquiringCreateSubscriptionWithReceiptRequestModel data;

        public Builder data(AcquiringCreateSubscriptionWithReceiptRequestModel data) {
            this.data = data;
            return this;
        }

        public AcquiringCreateSubscriptionWithReceiptRequestDataModel build() {
            return new AcquiringCreateSubscriptionWithReceiptRequestDataModel(this.data);
        }
    }
}
