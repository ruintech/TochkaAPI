package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;

/**
 * AccountModel
 *
 * @param customerCode Уникальный код клиента. Например: "300000092"
 * @param accountId Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
 * @param transitAccount Идентификатор транзитного счета (необязательное)
 * @param status Статус счёта в форме кода. Например: "Enabled"
 * @param statusUpdateDateTime Дата и время изменения статуса счёта. Используется стандарт ISO8601. Например:
 *        "2019-01-01T06:06:06.364+00:00"
 * @param currency Валюта ведения счёта. Используется стандарт ISO 4217. Например: "RUB"
 * @param accountType Тип счёта (физическое или юридическое лицо). Например: "Personal"
 * @param accountSubType Подтип счёта. Например: "CurrentAccount"
 * @param registrationDate Дата регистрации счета. Например: "2020-10-20"
 * @param accountDetails Accountdetails (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AccountModel(
        @JsonProperty("customerCode") String customerCode,
        @JsonProperty("accountId") String accountId,
        @JsonProperty("transitAccount") String transitAccount,
        @JsonProperty("status") ExternalAccountStatusEnum status,
        @JsonProperty("statusUpdateDateTime") String statusUpdateDateTime,
        @JsonProperty("currency") String currency,
        @JsonProperty("accountType") ExternalTypeEnum accountType,
        @JsonProperty("accountSubType") ExternalAccountSubTypeEnum accountSubType,
        @JsonProperty("registrationDate") LocalDate registrationDate,
        @JsonProperty("accountDetails") List<AccountDetailModel> accountDetails) {

    /** Строитель {@link AccountModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .customerCode(this.customerCode)
                .accountId(this.accountId)
                .transitAccount(this.transitAccount)
                .status(this.status)
                .statusUpdateDateTime(this.statusUpdateDateTime)
                .currency(this.currency)
                .accountType(this.accountType)
                .accountSubType(this.accountSubType)
                .registrationDate(this.registrationDate)
                .accountDetails(this.accountDetails);
    }

    /** Строитель {@link AccountModel}. */
    public static final class Builder {

        private String customerCode;
        private String accountId;
        private String transitAccount;
        private ExternalAccountStatusEnum status;
        private String statusUpdateDateTime;
        private String currency;
        private ExternalTypeEnum accountType;
        private ExternalAccountSubTypeEnum accountSubType;
        private LocalDate registrationDate;
        private List<AccountDetailModel> accountDetails;

        /** Уникальный код клиента. Например: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /** Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Идентификатор транзитного счета */
        public Builder transitAccount(String transitAccount) {
            this.transitAccount = transitAccount;
            return this;
        }

        /** Статус счёта в форме кода. Например: "Enabled" */
        public Builder status(ExternalAccountStatusEnum status) {
            this.status = status;
            return this;
        }

        /** Дата и время изменения статуса счёта. Используется стандарт ISO8601. Например:
        "2019-01-01T06:06:06.364+00:00" */
        public Builder statusUpdateDateTime(String statusUpdateDateTime) {
            this.statusUpdateDateTime = statusUpdateDateTime;
            return this;
        }

        /** Валюта ведения счёта. Используется стандарт ISO 4217. Например: "RUB" */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        /** Тип счёта (физическое или юридическое лицо). Например: "Personal" */
        public Builder accountType(ExternalTypeEnum accountType) {
            this.accountType = accountType;
            return this;
        }

        /** Подтип счёта. Например: "CurrentAccount" */
        public Builder accountSubType(ExternalAccountSubTypeEnum accountSubType) {
            this.accountSubType = accountSubType;
            return this;
        }

        /** Дата регистрации счета. Например: "2020-10-20" */
        public Builder registrationDate(LocalDate registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        /** Accountdetails */
        public Builder accountDetails(List<AccountDetailModel> accountDetails) {
            this.accountDetails = accountDetails;
            return this;
        }

        public AccountModel build() {
            return new AccountModel(
                    this.customerCode,
                    this.accountId,
                    this.transitAccount,
                    this.status,
                    this.statusUpdateDateTime,
                    this.currency,
                    this.accountType,
                    this.accountSubType,
                    this.registrationDate,
                    this.accountDetails);
        }
    }
}
