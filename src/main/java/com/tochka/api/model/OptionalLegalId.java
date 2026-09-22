package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * OptionalLegalId
 *
 * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов). Example: "LF0000000001"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OptionalLegalId(
        @JsonProperty("legalId") String legalId) {

    /** Builder for {@link OptionalLegalId}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .legalId(this.legalId);
    }

    /** Builder for {@link OptionalLegalId}. */
    public static final class Builder {

        private String legalId;

        /** Идентификатор зарегистрированного юрлица в СБП (12 символов). Example: "LF0000000001" */
        public Builder legalId(String legalId) {
            this.legalId = legalId;
            return this;
        }

        public OptionalLegalId build() {
            return new OptionalLegalId(this.legalId);
        }
    }
}
