package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BooleanResponse
 *
 * @param result Статус операции. Example: true
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BooleanResponse(
        @JsonProperty("result") Boolean result) {

    /** Builder for {@link BooleanResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .result(this.result);
    }

    /** Builder for {@link BooleanResponse}. */
    public static final class Builder {

        private Boolean result;

        /** Статус операции. Example: true */
        public Builder result(Boolean result) {
            this.result = result;
            return this;
        }

        public BooleanResponse build() {
            return new BooleanResponse(this.result);
        }
    }
}
