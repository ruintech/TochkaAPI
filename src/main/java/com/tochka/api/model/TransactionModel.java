package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

/**
 * TransactionModel
 *
 * @param transactionId Уникальный идентификатор транзакции. Например: "23489" (необязательное)
 * @param paymentId Уникальный идентификатор платежа, по которому произошла транзакция. Например: "abcd-11234"
 *        (необязательное)
 * @param creditDebitIndicator Приход/Уход. Например: "Credit"
 * @param status Статус транзакции. Например: "Booked"
 * @param documentNumber Номер платежного документа. Например: "123456" (необязательное)
 * @param transactionTypeCode Код типа транзакции (Вид платежного документа). Например: "Платежный ордер" (необязательное)
 * @param documentProcessDate Дата отражения на балансе. Например: "2019-01-01" (необязательное)
 * @param description Назначение платежа. Например: "string" (необязательное)
 * @param amount Amount
 * @param debtorParty Информация о контрагенте в случае кредитной операции (необязательное)
 * @param debtorAccount Идентификация счета дебитора, в случае кредитной операции (необязательное)
 * @param debtorAgent Финансовое организация, обслуживающая счет дебитора (необязательное)
 * @param creditorParty Информация о контрагенте в случае дебетовой транзакции (необязательное)
 * @param creditorAccount Идентификация счета кредитора, в случае дебетовой транзакции (необязательное)
 * @param creditorAgent Финансовое организация, обслуживающая счет кредитора (необязательное)
 * @param taxFields Налоговые поля (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransactionModel(
        @JsonProperty("transactionId") String transactionId,
        @JsonProperty("paymentId") String paymentId,
        @JsonProperty("creditDebitIndicator") ExternalCreditDebitIndicatorEnum creditDebitIndicator,
        @JsonProperty("status") ExternalTransactionStatusEnum status,
        @JsonProperty("documentNumber") String documentNumber,
        @JsonProperty("transactionTypeCode") ExternalTransationTypeEnum transactionTypeCode,
        @JsonProperty("documentProcessDate") LocalDate documentProcessDate,
        @JsonProperty("description") String description,
        @JsonProperty("Amount") TransactionAmountModel amount,
        @JsonProperty("DebtorParty") ContractorInfoModel debtorParty,
        @JsonProperty("DebtorAccount") CashAccountInfoModel debtorAccount,
        @JsonProperty("DebtorAgent") ContractorBankInfoModel debtorAgent,
        @JsonProperty("CreditorParty") ContractorInfoModel creditorParty,
        @JsonProperty("CreditorAccount") CashAccountInfoModel creditorAccount,
        @JsonProperty("CreditorAgent") ContractorBankInfoModel creditorAgent,
        @JsonProperty("TaxFields") TaxFieldsModel taxFields) {

    /** Строитель {@link TransactionModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .transactionId(this.transactionId)
                .paymentId(this.paymentId)
                .creditDebitIndicator(this.creditDebitIndicator)
                .status(this.status)
                .documentNumber(this.documentNumber)
                .transactionTypeCode(this.transactionTypeCode)
                .documentProcessDate(this.documentProcessDate)
                .description(this.description)
                .amount(this.amount)
                .debtorParty(this.debtorParty)
                .debtorAccount(this.debtorAccount)
                .debtorAgent(this.debtorAgent)
                .creditorParty(this.creditorParty)
                .creditorAccount(this.creditorAccount)
                .creditorAgent(this.creditorAgent)
                .taxFields(this.taxFields);
    }

    /** Строитель {@link TransactionModel}. */
    public static final class Builder {

        private String transactionId;
        private String paymentId;
        private ExternalCreditDebitIndicatorEnum creditDebitIndicator;
        private ExternalTransactionStatusEnum status;
        private String documentNumber;
        private ExternalTransationTypeEnum transactionTypeCode;
        private LocalDate documentProcessDate;
        private String description;
        private TransactionAmountModel amount;
        private ContractorInfoModel debtorParty;
        private CashAccountInfoModel debtorAccount;
        private ContractorBankInfoModel debtorAgent;
        private ContractorInfoModel creditorParty;
        private CashAccountInfoModel creditorAccount;
        private ContractorBankInfoModel creditorAgent;
        private TaxFieldsModel taxFields;

        /** Уникальный идентификатор транзакции. Например: "23489" */
        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        /** Уникальный идентификатор платежа, по которому произошла транзакция. Например: "abcd-11234" */
        public Builder paymentId(String paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        /** Приход/Уход. Например: "Credit" */
        public Builder creditDebitIndicator(ExternalCreditDebitIndicatorEnum creditDebitIndicator) {
            this.creditDebitIndicator = creditDebitIndicator;
            return this;
        }

        /** Статус транзакции. Например: "Booked" */
        public Builder status(ExternalTransactionStatusEnum status) {
            this.status = status;
            return this;
        }

        /** Номер платежного документа. Например: "123456" */
        public Builder documentNumber(String documentNumber) {
            this.documentNumber = documentNumber;
            return this;
        }

        /** Код типа транзакции (Вид платежного документа). Например: "Платежный ордер" */
        public Builder transactionTypeCode(ExternalTransationTypeEnum transactionTypeCode) {
            this.transactionTypeCode = transactionTypeCode;
            return this;
        }

        /** Дата отражения на балансе. Например: "2019-01-01" */
        public Builder documentProcessDate(LocalDate documentProcessDate) {
            this.documentProcessDate = documentProcessDate;
            return this;
        }

        /** Назначение платежа. Например: "string" */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder amount(TransactionAmountModel amount) {
            this.amount = amount;
            return this;
        }

        /** Информация о контрагенте в случае кредитной операции */
        public Builder debtorParty(ContractorInfoModel debtorParty) {
            this.debtorParty = debtorParty;
            return this;
        }

        /** Идентификация счета дебитора, в случае кредитной операции */
        public Builder debtorAccount(CashAccountInfoModel debtorAccount) {
            this.debtorAccount = debtorAccount;
            return this;
        }

        /** Финансовое организация, обслуживающая счет дебитора */
        public Builder debtorAgent(ContractorBankInfoModel debtorAgent) {
            this.debtorAgent = debtorAgent;
            return this;
        }

        /** Информация о контрагенте в случае дебетовой транзакции */
        public Builder creditorParty(ContractorInfoModel creditorParty) {
            this.creditorParty = creditorParty;
            return this;
        }

        /** Идентификация счета кредитора, в случае дебетовой транзакции */
        public Builder creditorAccount(CashAccountInfoModel creditorAccount) {
            this.creditorAccount = creditorAccount;
            return this;
        }

        /** Финансовое организация, обслуживающая счет кредитора */
        public Builder creditorAgent(ContractorBankInfoModel creditorAgent) {
            this.creditorAgent = creditorAgent;
            return this;
        }

        /** Налоговые поля */
        public Builder taxFields(TaxFieldsModel taxFields) {
            this.taxFields = taxFields;
            return this;
        }

        public TransactionModel build() {
            return new TransactionModel(
                    this.transactionId,
                    this.paymentId,
                    this.creditDebitIndicator,
                    this.status,
                    this.documentNumber,
                    this.transactionTypeCode,
                    this.documentProcessDate,
                    this.description,
                    this.amount,
                    this.debtorParty,
                    this.debtorAccount,
                    this.debtorAgent,
                    this.creditorParty,
                    this.creditorAccount,
                    this.creditorAgent,
                    this.taxFields);
        }
    }
}
