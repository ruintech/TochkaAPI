package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Метод получения списка доступных счетов
 *
 * @param data Data
 * @param links Links
 * @param meta Meta
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OpenBankingAccountListResponseModel(
        @JsonProperty("Data") AccountListModel data,
        @JsonProperty("Links") LinkModel links,
        @JsonProperty("Meta") MetaModel meta) {

    /** Builder for {@link OpenBankingAccountListResponseModel}. */
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

    /** Builder for {@link OpenBankingAccountListResponseModel}. */
    public static final class Builder {

        private AccountListModel data;
        private LinkModel links;
        private MetaModel meta;

        public Builder data(AccountListModel data) {
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

        public OpenBankingAccountListResponseModel build() {
            return new OpenBankingAccountListResponseModel(this.data, this.links, this.meta);
        }
    }
}
