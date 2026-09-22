package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

/**
 * InitStatementModel
 *
 * @param accountId Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
 * @param statementId Идентификатор ресурса выписки. Например: "23489" (необязательное)
 * @param status Статус готовности выписки. Например: "Ready"
 * @param startDateTime Дата начала выписки. Используется стандарт ISO8601. Например: "2019-01-01"
 * @param endDateTime Дата окончания выписки. Используется стандарт ISO8601. Например: "2019-01-01"
 * @param creationDateTime Дата и время создания ресурса. Используется стандарт ISO8601. Например:
 *        "2019-01-01T06:06:06.364+00:00"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InitStatementModel(
        @JsonProperty("accountId") String accountId,
        @JsonProperty("statementId") String statementId,
        @JsonProperty("status") StatementStatus status,
        @JsonProperty("startDateTime") LocalDate startDateTime,
        @JsonProperty("endDateTime") LocalDate endDateTime,
        @JsonProperty("creationDateTime") String creationDateTime) {

    /** Строитель {@link InitStatementModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .accountId(this.accountId)
                .statementId(this.statementId)
                .status(this.status)
                .startDateTime(this.startDateTime)
                .endDateTime(this.endDateTime)
                .creationDateTime(this.creationDateTime);
    }

    /** Строитель {@link InitStatementModel}. */
    public static final class Builder {

        private String accountId;
        private String statementId;
        private StatementStatus status;
        private LocalDate startDateTime;
        private LocalDate endDateTime;
        private String creationDateTime;

        /** Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Идентификатор ресурса выписки. Например: "23489" */
        public Builder statementId(String statementId) {
            this.statementId = statementId;
            return this;
        }

        /** Статус готовности выписки. Например: "Ready" */
        public Builder status(StatementStatus status) {
            this.status = status;
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

        /** Дата и время создания ресурса. Используется стандарт ISO8601. Например:
        "2019-01-01T06:06:06.364+00:00" */
        public Builder creationDateTime(String creationDateTime) {
            this.creationDateTime = creationDateTime;
            return this;
        }

        public InitStatementModel build() {
            return new InitStatementModel(
                    this.accountId,
                    this.statementId,
                    this.status,
                    this.startDateTime,
                    this.endDateTime,
                    this.creationDateTime);
        }
    }
}
