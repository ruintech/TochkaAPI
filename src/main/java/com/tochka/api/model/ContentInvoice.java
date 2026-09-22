package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ContentInvoice
 *
 * @param invoice Содержимое счета на оплату
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContentInvoice(
        @JsonProperty("Invoice") InvoiceModel invoice) {

    /** Builder for {@link ContentInvoice}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .invoice(this.invoice);
    }

    /** Builder for {@link ContentInvoice}. */
    public static final class Builder {

        private InvoiceModel invoice;

        /** Содержимое счета на оплату */
        public Builder invoice(InvoiceModel invoice) {
            this.invoice = invoice;
            return this;
        }

        public ContentInvoice build() {
            return new ContentInvoice(this.invoice);
        }
    }
}
