package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ContentInvoicef
 *
 * @param invoicef Содержимое счета-фактуры
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContentInvoicef(
        @JsonProperty("Invoicef") InvoicefModel invoicef) {

    /** Строитель {@link ContentInvoicef}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .invoicef(this.invoicef);
    }

    /** Строитель {@link ContentInvoicef}. */
    public static final class Builder {

        private InvoicefModel invoicef;

        /** Содержимое счета-фактуры */
        public Builder invoicef(InvoicefModel invoicef) {
            this.invoicef = invoicef;
            return this;
        }

        public ContentInvoicef build() {
            return new ContentInvoicef(this.invoicef);
        }
    }
}
