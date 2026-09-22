package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SBPRefund
 *
 * @param bankCode Bankcode. БИК отправителя. Example: "044525104"
 * @param accountCode Accountcode. Уникальный и неизменный идентификатор счёта. Example: "40817810802000000008"
 * @param amount Amount. Cумма операции в рублях. Example: "10"
 * @param currency Currency. Валюта операции (optional)
 * @param qrcId Qrcid. ID qr-кода, по которому был сделан платеж. Example:
 *        "AS10007GLJ1216F4905A1MTT3CP7GK3N"
 * @param purpose Purpose. Назначение платежа. Example: "Оплата по счету № 1 от 01.01.2021. Без НДС"
 *        (optional)
 * @param refTransactionId Reftransactionid. Идентификатор транзакции, по которой осуществляется возврат. Example:
 *        "48232c9a-ce82-1593-3cb6-5c85a1ffef8f" (optional)
 * @param trxId Trxid. Идентификатор операции в НСПК, по которой можно осуществить возврат. Example:
 *        "A1A2S3D5F6G7H8J9K0C4S5C6D7V5D1K2" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SBPRefund(
        @JsonProperty("bankCode") String bankCode,
        @JsonProperty("accountCode") String accountCode,
        @JsonProperty("amount") String amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("qrcId") String qrcId,
        @JsonProperty("purpose") String purpose,
        @JsonProperty("refTransactionId") String refTransactionId,
        @JsonProperty("trxId") String trxId) {

    /** Builder for {@link SBPRefund}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .bankCode(this.bankCode)
                .accountCode(this.accountCode)
                .amount(this.amount)
                .currency(this.currency)
                .qrcId(this.qrcId)
                .purpose(this.purpose)
                .refTransactionId(this.refTransactionId)
                .trxId(this.trxId);
    }

    /** Builder for {@link SBPRefund}. */
    public static final class Builder {

        private String bankCode;
        private String accountCode;
        private String amount;
        private String currency;
        private String qrcId;
        private String purpose;
        private String refTransactionId;
        private String trxId;

        /** Bankcode. БИК отправителя. Example: "044525104" */
        public Builder bankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /** Accountcode. Уникальный и неизменный идентификатор счёта. Example: "40817810802000000008" */
        public Builder accountCode(String accountCode) {
            this.accountCode = accountCode;
            return this;
        }

        /** Amount. Cумма операции в рублях. Example: "10" */
        public Builder amount(String amount) {
            this.amount = amount;
            return this;
        }

        /** Currency. Валюта операции */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        /** Qrcid. ID qr-кода, по которому был сделан платеж. Example:
        "AS10007GLJ1216F4905A1MTT3CP7GK3N" */
        public Builder qrcId(String qrcId) {
            this.qrcId = qrcId;
            return this;
        }

        /** Purpose. Назначение платежа. Example: "Оплата по счету № 1 от 01.01.2021. Без НДС" */
        public Builder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        /** Reftransactionid. Идентификатор транзакции, по которой осуществляется возврат. Example:
        "48232c9a-ce82-1593-3cb6-5c85a1ffef8f" */
        public Builder refTransactionId(String refTransactionId) {
            this.refTransactionId = refTransactionId;
            return this;
        }

        /** Trxid. Идентификатор операции в НСПК, по которой можно осуществить возврат. Example:
        "A1A2S3D5F6G7H8J9K0C4S5C6D7V5D1K2" */
        public Builder trxId(String trxId) {
            this.trxId = trxId;
            return this;
        }

        public SBPRefund build() {
            return new SBPRefund(
                    this.bankCode,
                    this.accountCode,
                    this.amount,
                    this.currency,
                    this.qrcId,
                    this.purpose,
                    this.refTransactionId,
                    this.trxId);
        }
    }
}
