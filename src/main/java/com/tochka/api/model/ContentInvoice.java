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

    /** Строитель {@link ContentInvoice}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .invoice(this.invoice);
    }

    /** Строитель {@link ContentInvoice}. */
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
