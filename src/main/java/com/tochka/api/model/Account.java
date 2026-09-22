package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Account
 *
 * @param accountId Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
 * @param status Статус объекта. Например: "Active"
 * @param createdAt Время регистрации. Например: "2019-01-01T06:06:06.364+00:00"
 * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов). Например: "LF0000000001"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Account(
        @JsonProperty("accountId") String accountId,
        @JsonProperty("status") StatusEnum status,
        @JsonProperty("createdAt") String createdAt,
        @JsonProperty("legalId") String legalId) {

    /** Строитель {@link Account}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .accountId(this.accountId)
                .status(this.status)
                .createdAt(this.createdAt)
                .legalId(this.legalId);
    }

    /** Строитель {@link Account}. */
    public static final class Builder {

        private String accountId;
        private StatusEnum status;
        private String createdAt;
        private String legalId;

        /** Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Статус объекта. Например: "Active" */
        public Builder status(StatusEnum status) {
            this.status = status;
            return this;
        }

        /** Время регистрации. Например: "2019-01-01T06:06:06.364+00:00" */
        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /** Идентификатор зарегистрированного юрлица в СБП (12 символов). Например: "LF0000000001" */
        public Builder legalId(String legalId) {
            this.legalId = legalId;
            return this;
        }

        public Account build() {
            return new Account(this.accountId, this.status, this.createdAt, this.legalId);
        }
    }
}
