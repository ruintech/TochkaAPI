package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

/**
 * StatementInitReqModel
 *
 * @param accountId Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
 * @param startDateTime Дата начала выписки. Используется стандарт ISO8601. Например: "2019-01-01"
 * @param endDateTime Дата окончания выписки. Используется стандарт ISO8601. Например: "2019-01-01"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StatementInitReqModel(
        @JsonProperty("accountId") String accountId,
        @JsonProperty("startDateTime") LocalDate startDateTime,
        @JsonProperty("endDateTime") LocalDate endDateTime) {

    /** Строитель {@link StatementInitReqModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .accountId(this.accountId)
                .startDateTime(this.startDateTime)
                .endDateTime(this.endDateTime);
    }

    /** Строитель {@link StatementInitReqModel}. */
    public static final class Builder {

        private String accountId;
        private LocalDate startDateTime;
        private LocalDate endDateTime;

        /** Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Дата начала выписки. Используется стандарт ISO8601. Например: "2019-01-01" */
        public Builder startDateTime(LocalDate startDateTime) {
            this.startDateTime = startDateTime;
            return this;
        }

        /** Дата окончания выписки. Используется стандарт ISO8601. Например: "2019-01-01" */
        public Builder endDateTime(LocalDate endDateTime) {
            this.endDateTime = endDateTime;
            return this;
        }

        public StatementInitReqModel build() {
            return new StatementInitReqModel(this.accountId, this.startDateTime, this.endDateTime);
        }
    }
}
