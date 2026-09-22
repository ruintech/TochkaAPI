package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringCreatePaymentOperationWithReceiptRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringCreatePaymentOperationWithReceiptRequestDataModel(
        @JsonProperty("Data") AcquiringCreatePaymentOperationWithReceiptRequestModel data) {

    /** Builder for {@link AcquiringCreatePaymentOperationWithReceiptRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Builder for {@link AcquiringCreatePaymentOperationWithReceiptRequestDataModel}. */
    public static final class Builder {

        private AcquiringCreatePaymentOperationWithReceiptRequestModel data;

        public Builder data(AcquiringCreatePaymentOperationWithReceiptRequestModel data) {
            this.data = data;
            return this;
        }

        public AcquiringCreatePaymentOperationWithReceiptRequestDataModel build() {
            return new AcquiringCreatePaymentOperationWithReceiptRequestDataModel(this.data);
        }
    }
}
