package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BalanceModel
 *
 * @param accountId Уникальный и неизменный идентификатор счёта. Example: "40817810802000000008/044525104"
 * @param creditDebitIndicator Определяет является баланс кредитовым или дебетовым. Example: "Credit"
 * @param type Тип баланса, заполняется согласно ISO 20022. Example: "OpeningAvailable"
 * @param dateTime Дата и время построения отчета. Используется стандарт ISO8601. Example:
 *        "2019-01-01T06:06:06.364+00:00"
 * @param amount Amount
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BalanceModel(
        @JsonProperty("accountId") String accountId,
        @JsonProperty("creditDebitIndicator") ExternalBalanceTypeEnum creditDebitIndicator,
        @JsonProperty("type") ExternalBalanceStaticTypeEnum type,
        @JsonProperty("dateTime") String dateTime,
        @JsonProperty("Amount") BalanceAmountModel amount) {

    /** Builder for {@link BalanceModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .accountId(this.accountId)
                .creditDebitIndicator(this.creditDebitIndicator)
                .type(this.type)
                .dateTime(this.dateTime)
                .amount(this.amount);
    }

    /** Builder for {@link BalanceModel}. */
    public static final class Builder {

        private String accountId;
        private ExternalBalanceTypeEnum creditDebitIndicator;
        private ExternalBalanceStaticTypeEnum type;
        private String dateTime;
        private BalanceAmountModel amount;

        /** Уникальный и неизменный идентификатор счёта. Example: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Определяет является баланс кредитовым или дебетовым. Example: "Credit" */
        public Builder creditDebitIndicator(ExternalBalanceTypeEnum creditDebitIndicator) {
            this.creditDebitIndicator = creditDebitIndicator;
            return this;
        }

        /** Тип баланса, заполняется согласно ISO 20022. Example: "OpeningAvailable" */
        public Builder type(ExternalBalanceStaticTypeEnum type) {
            this.type = type;
            return this;
        }

        /** Дата и время построения отчета. Используется стандарт ISO8601. Example:
        "2019-01-01T06:06:06.364+00:00" */
        public Builder dateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder amount(BalanceAmountModel amount) {
            this.amount = amount;
            return this;
        }

        public BalanceModel build() {
            return new BalanceModel(this.accountId, this.creditDebitIndicator, this.type, this.dateTime, this.amount);
        }
    }
}
