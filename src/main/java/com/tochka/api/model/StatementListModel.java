package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * StatementListModel
 *
 * @param statement Statement
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StatementListModel(
        @JsonProperty("Statement") List<StatementModel> statement) {

    /** Строитель {@link StatementListModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .statement(this.statement);
    }

    /** Строитель {@link StatementListModel}. */
    public static final class Builder {

        private List<StatementModel> statement;

        /** Statement */
        public Builder statement(List<StatementModel> statement) {
            this.statement = statement;
            return this;
        }

        public StatementListModel build() {
            return new StatementListModel(this.statement);
        }
    }
}
