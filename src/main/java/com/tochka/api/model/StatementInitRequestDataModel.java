package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Метод создания выписки по счету
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StatementInitRequestDataModel(
        @JsonProperty("Data") StatementInitRequestModel data) {

    /** Строитель {@link StatementInitRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link StatementInitRequestDataModel}. */
    public static final class Builder {

        private StatementInitRequestModel data;

        public Builder data(StatementInitRequestModel data) {
            this.data = data;
            return this;
        }

        public StatementInitRequestDataModel build() {
            return new StatementInitRequestDataModel(this.data);
        }
    }
}
