package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BooleanResponse
 *
 * @param result Статус операции. Например: true
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BooleanResponse(
        @JsonProperty("result") Boolean result) {

    /** Строитель {@link BooleanResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .result(this.result);
    }

    /** Строитель {@link BooleanResponse}. */
    public static final class Builder {

        private Boolean result;

        /** Статус операции. Например: true */
        public Builder result(Boolean result) {
            this.result = result;
            return this;
        }

        public BooleanResponse build() {
            return new BooleanResponse(this.result);
        }
    }
}
