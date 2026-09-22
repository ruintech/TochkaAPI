package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GetCashboxQrCodeOperationInfoResponseDataModel
 *
 * @param data Data
 * @param links Links
 * @param meta Meta
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetCashboxQrCodeOperationInfoResponseDataModel(
        @JsonProperty("Data") GetCashboxQrCodeOperationInfoResponseModel data,
        @JsonProperty("Links") LinkModel links,
        @JsonProperty("Meta") MetaModel meta) {

    /** Builder for {@link GetCashboxQrCodeOperationInfoResponseDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data)
                .links(this.links)
                .meta(this.meta);
    }

    /** Builder for {@link GetCashboxQrCodeOperationInfoResponseDataModel}. */
    public static final class Builder {

        private GetCashboxQrCodeOperationInfoResponseModel data;
        private LinkModel links;
        private MetaModel meta;

        public Builder data(GetCashboxQrCodeOperationInfoResponseModel data) {
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

        public GetCashboxQrCodeOperationInfoResponseDataModel build() {
            return new GetCashboxQrCodeOperationInfoResponseDataModel(this.data, this.links, this.meta);
        }
    }
}
