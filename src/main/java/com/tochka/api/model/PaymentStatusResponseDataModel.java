package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PaymentStatusResponseDataModel
 *
 * @param data Data
 * @param links Links
 * @param meta Meta
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentStatusResponseDataModel(
        @JsonProperty("Data") PaymentStatusResponseModel data,
        @JsonProperty("Links") LinkModel links,
        @JsonProperty("Meta") MetaModel meta) {

    /** Строитель {@link PaymentStatusResponseDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data)
                .links(this.links)
                .meta(this.meta);
    }

    /** Строитель {@link PaymentStatusResponseDataModel}. */
    public static final class Builder {

        private PaymentStatusResponseModel data;
        private LinkModel links;
        private MetaModel meta;

        public Builder data(PaymentStatusResponseModel data) {
            this.data = data;
            return this;
        }

        public Builder links(LinkModel links) {
            this.links = links;
            return this;
        }

        public Builder meta(MetaModel meta) {
            this.meta = meta;
            return this;
        }

        public PaymentStatusResponseDataModel build() {
            return new PaymentStatusResponseDataModel(this.data, this.links, this.meta);
        }
    }
}
