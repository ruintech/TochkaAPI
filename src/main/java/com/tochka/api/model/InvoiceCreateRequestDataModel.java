package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * InvoiceCreateRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InvoiceCreateRequestDataModel(
        @JsonProperty("Data") InvoiceCreateRequestModel data) {

    /** Builder for {@link InvoiceCreateRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Builder for {@link InvoiceCreateRequestDataModel}. */
    public static final class Builder {

        private InvoiceCreateRequestModel data;

        public Builder data(InvoiceCreateRequestModel data) {
            this.data = data;
            return this;
        }

        public InvoiceCreateRequestDataModel build() {
            return new InvoiceCreateRequestDataModel(this.data);
        }
    }
}
