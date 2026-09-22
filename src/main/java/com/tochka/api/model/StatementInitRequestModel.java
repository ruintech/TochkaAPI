package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * StatementInitRequestModel
 *
 * @param statement Statement
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StatementInitRequestModel(
        @JsonProperty("Statement") StatementInitReqModel statement) {

    /** Builder for {@link StatementInitRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .statement(this.statement);
    }

    /** Builder for {@link StatementInitRequestModel}. */
    public static final class Builder {

        private StatementInitReqModel statement;

        public Builder statement(StatementInitReqModel statement) {
            this.statement = statement;
            return this;
        }

        public StatementInitRequestModel build() {
            return new StatementInitRequestModel(this.statement);
        }
    }
}
