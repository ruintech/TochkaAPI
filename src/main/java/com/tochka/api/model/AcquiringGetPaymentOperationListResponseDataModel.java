package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringGetPaymentOperationListResponseDataModel
 *
 * @param data Data
 * @param links Links
 * @param meta Meta
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringGetPaymentOperationListResponseDataModel(
        @JsonProperty("Data") AcquiringGetPaymentOperationListResponseModel data,
        @JsonProperty("Links") PaginatedLinkModel links,
        @JsonProperty("Meta") MetaModel meta) {

    /** Строитель {@link AcquiringGetPaymentOperationListResponseDataModel}. */
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

    /** Строитель {@link AcquiringGetPaymentOperationListResponseDataModel}. */
    public static final class Builder {

        private AcquiringGetPaymentOperationListResponseModel data;
        private PaginatedLinkModel links;
        private MetaModel meta;

        public Builder data(AcquiringGetPaymentOperationListResponseModel data) {
            this.data = data;
            return this;
        }

        public Builder links(PaginatedLinkModel links) {
            this.links = links;
            return this;
        }

        public Builder meta(MetaModel meta) {
            this.meta = meta;
            return this;
        }

        public AcquiringGetPaymentOperationListResponseDataModel build() {
            return new AcquiringGetPaymentOperationListResponseDataModel(this.data, this.links, this.meta);
        }
    }
}
