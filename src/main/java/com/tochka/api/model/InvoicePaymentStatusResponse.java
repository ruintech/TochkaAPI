package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * InvoicePaymentStatusResponse
 *
 * @param paymentStatus Статус оплаты документа. Например: "payment_paid"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InvoicePaymentStatusResponse(
        @JsonProperty("paymentStatus") InvoicePaymentStatusEnum paymentStatus) {

    /** Строитель {@link InvoicePaymentStatusResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .paymentStatus(this.paymentStatus);
    }

    /** Строитель {@link InvoicePaymentStatusResponse}. */
    public static final class Builder {

        private InvoicePaymentStatusEnum paymentStatus;

        /** Статус оплаты документа. Например: "payment_paid" */
        public Builder paymentStatus(InvoicePaymentStatusEnum paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public InvoicePaymentStatusResponse build() {
            return new InvoicePaymentStatusResponse(this.paymentStatus);
        }
    }
}
