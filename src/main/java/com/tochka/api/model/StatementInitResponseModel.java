package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * StatementInitResponseModel
 *
 * @param statement Statement
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StatementInitResponseModel(
        @JsonProperty("Statement") InitStatementModel statement) {

    /** Builder for {@link StatementInitResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .statement(this.statement);
    }

    /** Builder for {@link StatementInitResponseModel}. */
    public static final class Builder {

        private InitStatementModel statement;

        public Builder statement(InitStatementModel statement) {
            this.statement = statement;
            return this;
        }

        public StatementInitResponseModel build() {
            return new StatementInitResponseModel(this.statement);
        }
    }
}
