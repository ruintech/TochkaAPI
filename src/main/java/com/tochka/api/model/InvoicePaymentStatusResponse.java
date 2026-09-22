package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * InvoicePaymentStatusResponse
 *
 * @param paymentStatus Статус оплаты документа. Example: "payment_paid"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InvoicePaymentStatusResponse(
        @JsonProperty("paymentStatus") InvoicePaymentStatusEnum paymentStatus) {

    /** Builder for {@link InvoicePaymentStatusResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .paymentStatus(this.paymentStatus);
    }

    /** Builder for {@link InvoicePaymentStatusResponse}. */
    public static final class Builder {

        private InvoicePaymentStatusEnum paymentStatus;

        /** Статус оплаты документа. Example: "payment_paid" */
        public Builder paymentStatus(InvoicePaymentStatusEnum paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public InvoicePaymentStatusResponse build() {
            return new InvoicePaymentStatusResponse(this.paymentStatus);
        }
    }
}
