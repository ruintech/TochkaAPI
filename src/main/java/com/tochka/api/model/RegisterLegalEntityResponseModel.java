package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RegisterLegalEntityResponseModel
 *
 * @param data Data
 * @param links Links
 * @param meta Meta
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterLegalEntityResponseModel(
        @JsonProperty("Data") OptionalLegalId data,
        @JsonProperty("Links") LinkModel links,
        @JsonProperty("Meta") MetaModel meta) {

    /** Builder for {@link RegisterLegalEntityResponseModel}. */
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

    /** Builder for {@link RegisterLegalEntityResponseModel}. */
    public static final class Builder {

        private OptionalLegalId data;
        private LinkModel links;
        private MetaModel meta;

        public Builder data(OptionalLegalId data) {
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

        public RegisterLegalEntityResponseModel build() {
            return new RegisterLegalEntityResponseModel(this.data, this.links, this.meta);
        }
    }
}
