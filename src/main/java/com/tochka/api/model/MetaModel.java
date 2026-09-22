package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * MetaModel
 *
 * @param totalPages Totalpages. Example: 1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MetaModel(
        @JsonProperty("totalPages") Integer totalPages) {

    /** Builder for {@link MetaModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .totalPages(this.totalPages);
    }

    /** Builder for {@link MetaModel}. */
    public static final class Builder {

        private Integer totalPages;

        /** Totalpages. Example: 1 */
        public Builder totalPages(Integer totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public MetaModel build() {
            return new MetaModel(this.totalPages);
        }
    }
}
