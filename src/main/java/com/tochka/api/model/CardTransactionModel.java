package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CardTransactionModel
 *
 * @param accountId Уникальный и неизменный идентификатор счёта. Example: "40817810802000000008/044525104"
 * @param pan Pan. Маскированный номер карты транзакции
 * @param dateTime Дата и время транзакции. Используется стандарт ISO8601. Example:
 *        "2019-01-01T06:06:06.364+00:00"
 * @param amount Оригинальная сумма и валюта
 * @param accountAmount Сумма и валюта в валюте счета
 * @param terminalData TerminalData
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CardTransactionModel(
        @JsonProperty("accountId") String accountId,
        @JsonProperty("pan") String pan,
        @JsonProperty("dateTime") String dateTime,
        @JsonProperty("Amount") CardTransactionAmountModel amount,
        @JsonProperty("AccountAmount") CardTransactionAmountModel accountAmount,
        @JsonProperty("TerminalData") CardTransactionTerminalData terminalData) {

    /** Builder for {@link CardTransactionModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .accountId(this.accountId)
                .pan(this.pan)
                .dateTime(this.dateTime)
                .amount(this.amount)
                .accountAmount(this.accountAmount)
                .terminalData(this.terminalData);
    }

    /** Builder for {@link CardTransactionModel}. */
    public static final class Builder {

        private String accountId;
        private String pan;
        private String dateTime;
        private CardTransactionAmountModel amount;
        private CardTransactionAmountModel accountAmount;
        private CardTransactionTerminalData terminalData;

        /** Уникальный и неизменный идентификатор счёта. Example: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Pan. Маскированный номер карты транзакции */
        public Builder pan(String pan) {
            this.pan = pan;
            return this;
        }

        /** Дата и время транзакции. Используется стандарт ISO8601. Example:
        "2019-01-01T06:06:06.364+00:00" */
        public Builder dateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        /** Оригинальная сумма и валюта */
        public Builder amount(CardTransactionAmountModel amount) {
            this.amount = amount;
            return this;
        }

        /** Сумма и валюта в валюте счета */
        public Builder accountAmount(CardTransactionAmountModel accountAmount) {
            this.accountAmount = accountAmount;
            return this;
        }

        public Builder terminalData(CardTransactionTerminalData terminalData) {
            this.terminalData = terminalData;
            return this;
        }

        public CardTransactionModel build() {
            return new CardTransactionModel(
                    this.accountId,
                    this.pan,
                    this.dateTime,
                    this.amount,
                    this.accountAmount,
                    this.terminalData);
        }
    }
}
