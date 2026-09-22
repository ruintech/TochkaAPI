package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * B2BQRCodeModelResponseModel
 *
 * @param data Data
 * @param links Links
 * @param meta Meta
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record B2BQRCodeModelResponseModel(
        @JsonProperty("Data") RegisteredB2BQrCode data,
        @JsonProperty("Links") LinkModel links,
        @JsonProperty("Meta") MetaModel meta) {

    /** Строитель {@link B2BQRCodeModelResponseModel}. */
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

    /** Строитель {@link B2BQRCodeModelResponseModel}. */
    public static final class Builder {

        private RegisteredB2BQrCode data;
        private LinkModel links;
        private MetaModel meta;

        public Builder data(RegisteredB2BQrCode data) {
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

        public B2BQRCodeModelResponseModel build() {
            return new B2BQRCodeModelResponseModel(this.data, this.links, this.meta);
        }
    }
}
