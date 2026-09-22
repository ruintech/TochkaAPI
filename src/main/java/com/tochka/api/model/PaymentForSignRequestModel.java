package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * PaymentForSignRequestModel
 *
 * @param accountCode Номер счёта отправителя. Например: "40702810840020002503"
 * @param bankCode БИК отправителя. Например: "044525104"
 * @param payerINN ИНН лица, за которого поступит платёж. - 10-значный — для юрлиц - 12-значный — для физлиц -
 *        "0" — если у лица, за которого платите, нет ИНН в РФ. Например: "5001038736"
 *        (необязательное)
 * @param payerKPP КПП лица, за которого поступит платёж. Обязательное поле при платеже в бюджет или за
 *        нерезидента РФ. Значением может быть "0" или девятизначное число. - Если платите за себя в
 *        бюджет на единый налоговый счёт (ЕНС), укажите "0" - Если это платёж за другое лицо, которое
 *        не является налоговым резидентом РФ, передайте "0" или девятизначное число. Например:
 *        "500101001" (необязательное)
 * @param counterpartyBankBic БИК получателя. Например: "044525104"
 * @param counterpartyAccountNumber Счёт получателя. Например: "40702810840020002504"
 * @param counterpartyINN ИНН получателя. Например: "5001038736" (необязательное)
 * @param counterpartyKPP КПП получателя. Допустимые значения "0" или 9 значное число. Например: "500101001"
 *        (необязательное)
 * @param counterpartyName Получатель платежа. Например: "ООО \"БАЙКАЛ-СЕРВИС ТК\""
 * @param counterpartyBankCorrAccount Кор. счёт банка получателя. Например: "30101810745374525104" (необязательное)
 * @param paymentAmount Сумма платежа. Например: 700.33
 * @param paymentDate Дата платежа. Используется стандарт ISO8601. Дата платежа, приведенная к часовому поясу
 *        Москвы. Например: "2018-03-29"
 * @param paymentNumber Номер платежа. Например: 9195 (необязательное)
 * @param paymentPriority Приоритет платежа. Например: "5" (необязательное)
 * @param paymentPurpose Назначение платежа. Например: "Оплата по счету № 1 от 01.01.2021. Без НДС"
 * @param codePurpose Поле 20. Заполняется только при платеже физ лицам на счета:('40810', '40817', '40823',
 *        '40824', '40826', '423', '30232', '40803', '40813', '40820', '426'). Допустимые значения
 *        1,2,3,4,5 и пусто. Например: "1" (необязательное)
 * @param supplierBillId Код УИН (поле 22). Например: "1" (необязательное)
 * @param taxInfoDocumentDate Дата документа (поле 109). Используется стандарт ISO8601. Допустимо значение "0". Например:
 *        "2018-03-29" (необязательное)
 * @param taxInfoDocumentNumber Номера документа (поле 108). Например: "12" (необязательное)
 * @param taxInfoKBK КБК (поле 104). Например: "18210202020061000160" (необязательное)
 * @param taxInfoOKATO ОКАТО (поле 105). Например: "65401364000" (необязательное)
 * @param taxInfoPeriod Налоговый период (поле 107). Допустимо значение "0". Например: "МС.08.2009" (необязательное)
 * @param taxInfoReasonCode Основание (поле 106). Например: "ТП" (необязательное)
 * @param taxInfoStatus Статус (поле 101). Например: "08" (необязательное)
 * @param budgetPaymentCode Код выплат из бюджета на ФЛ (поле 110). Например: "1" (необязательное)
 * @param email Email для отправки платежного поручения. Например: "ivanov&#64;mail.com" (необязательное)
 * @param gisPhoneNumber Номер телефона для ГИС ГМП. Например: "+79999999999" (необязательное)
 * @param gisEmail Адрес электронной почты для ГИС ГМП. Например: "ivanov&#64;mail.com" (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentForSignRequestModel(
        @JsonProperty("accountCode") String accountCode,
        @JsonProperty("bankCode") String bankCode,
        @JsonProperty("payerINN") String payerINN,
        @JsonProperty("payerKPP") String payerKPP,
        @JsonProperty("counterpartyBankBic") String counterpartyBankBic,
        @JsonProperty("counterpartyAccountNumber") String counterpartyAccountNumber,
        @JsonProperty("counterpartyINN") String counterpartyINN,
        @JsonProperty("counterpartyKPP") String counterpartyKPP,
        @JsonProperty("counterpartyName") String counterpartyName,
        @JsonProperty("counterpartyBankCorrAccount") String counterpartyBankCorrAccount,
        @JsonProperty("paymentAmount") BigDecimal paymentAmount,
        @JsonProperty("paymentDate") LocalDate paymentDate,
        @JsonProperty("paymentNumber") Integer paymentNumber,
        @JsonProperty("paymentPriority") String paymentPriority,
        @JsonProperty("paymentPurpose") String paymentPurpose,
        @JsonProperty("codePurpose") String codePurpose,
        @JsonProperty("supplierBillId") String supplierBillId,
        @JsonProperty("taxInfoDocumentDate") String taxInfoDocumentDate,
        @JsonProperty("taxInfoDocumentNumber") String taxInfoDocumentNumber,
        @JsonProperty("taxInfoKBK") String taxInfoKBK,
        @JsonProperty("taxInfoOKATO") String taxInfoOKATO,
        @JsonProperty("taxInfoPeriod") String taxInfoPeriod,
        @JsonProperty("taxInfoReasonCode") String taxInfoReasonCode,
        @JsonProperty("taxInfoStatus") String taxInfoStatus,
        @JsonProperty("budgetPaymentCode") String budgetPaymentCode,
        @JsonProperty("email") String email,
        @JsonProperty("gisPhoneNumber") String gisPhoneNumber,
        @JsonProperty("gisEmail") String gisEmail) {

    /** Строитель {@link PaymentForSignRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .accountCode(this.accountCode)
                .bankCode(this.bankCode)
                .payerINN(this.payerINN)
                .payerKPP(this.payerKPP)
                .counterpartyBankBic(this.counterpartyBankBic)
                .counterpartyAccountNumber(this.counterpartyAccountNumber)
                .counterpartyINN(this.counterpartyINN)
                .counterpartyKPP(this.counterpartyKPP)
                .counterpartyName(this.counterpartyName)
                .counterpartyBankCorrAccount(this.counterpartyBankCorrAccount)
                .paymentAmount(this.paymentAmount)
                .paymentDate(this.paymentDate)
                .paymentNumber(this.paymentNumber)
                .paymentPriority(this.paymentPriority)
                .paymentPurpose(this.paymentPurpose)
                .codePurpose(this.codePurpose)
                .supplierBillId(this.supplierBillId)
                .taxInfoDocumentDate(this.taxInfoDocumentDate)
                .taxInfoDocumentNumber(this.taxInfoDocumentNumber)
                .taxInfoKBK(this.taxInfoKBK)
                .taxInfoOKATO(this.taxInfoOKATO)
                .taxInfoPeriod(this.taxInfoPeriod)
                .taxInfoReasonCode(this.taxInfoReasonCode)
                .taxInfoStatus(this.taxInfoStatus)
                .budgetPaymentCode(this.budgetPaymentCode)
                .email(this.email)
                .gisPhoneNumber(this.gisPhoneNumber)
                .gisEmail(this.gisEmail);
    }

    /** Строитель {@link PaymentForSignRequestModel}. */
    public static final class Builder {

        private String accountCode;
        private String bankCode;
        private String payerINN;
        private String payerKPP;
        private String counterpartyBankBic;
        private String counterpartyAccountNumber;
        private String counterpartyINN;
        private String counterpartyKPP;
        private String counterpartyName;
        private String counterpartyBankCorrAccount;
        private BigDecimal paymentAmount;
        private LocalDate paymentDate;
        private Integer paymentNumber;
        private String paymentPriority;
        private String paymentPurpose;
        private String codePurpose;
        private String supplierBillId;
        private String taxInfoDocumentDate;
        private String taxInfoDocumentNumber;
        private String taxInfoKBK;
        private String taxInfoOKATO;
        private String taxInfoPeriod;
        private String taxInfoReasonCode;
        private String taxInfoStatus;
        private String budgetPaymentCode;
        private String email;
        private String gisPhoneNumber;
        private String gisEmail;

        /** Номер счёта отправителя. Например: "40702810840020002503" */
        public Builder accountCode(String accountCode) {
            this.accountCode = accountCode;
            return this;
        }

        /** БИК отправителя. Например: "044525104" */
        public Builder bankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /** ИНН лица, за которого поступит платёж. - 10-значный — для юрлиц - 12-значный — для физлиц -
        "0" — если у лица, за которого платите, нет ИНН в РФ. Например: "5001038736" */
        public Builder payerINN(String payerINN) {
            this.payerINN = payerINN;
            return this;
        }

        /** КПП лица, за которого поступит платёж. Обязательное поле при платеже в бюджет или за
        нерезидента РФ. Значением может быть "0" или девятизначное число. - Если платите за себя в
        бюджет на единый налоговый счёт (ЕНС), укажите "0" - Если это платёж за другое лицо, которое не
        является налоговым резидентом РФ, передайте "0" или девятизначное число. Например: "500101001" */
        public Builder payerKPP(String payerKPP) {
            this.payerKPP = payerKPP;
            return this;
        }

        /** БИК получателя. Например: "044525104" */
        public Builder counterpartyBankBic(String counterpartyBankBic) {
            this.counterpartyBankBic = counterpartyBankBic;
            return this;
        }

        /** Счёт получателя. Например: "40702810840020002504" */
        public Builder counterpartyAccountNumber(String counterpartyAccountNumber) {
            this.counterpartyAccountNumber = counterpartyAccountNumber;
            return this;
        }

        /** ИНН получателя. Например: "5001038736" */
        public Builder counterpartyINN(String counterpartyINN) {
            this.counterpartyINN = counterpartyINN;
            return this;
        }

        /** КПП получателя. Допустимые значения "0" или 9 значное число. Например: "500101001" */
        public Builder counterpartyKPP(String counterpartyKPP) {
            this.counterpartyKPP = counterpartyKPP;
            return this;
        }

        /** Получатель платежа. Например: "ООО \"БАЙКАЛ-СЕРВИС ТК\"" */
        public Builder counterpartyName(String counterpartyName) {
            this.counterpartyName = counterpartyName;
            return this;
        }

        /** Кор. счёт банка получателя. Например: "30101810745374525104" */
        public Builder counterpartyBankCorrAccount(String counterpartyBankCorrAccount) {
            this.counterpartyBankCorrAccount = counterpartyBankCorrAccount;
            return this;
        }

        /** Сумма платежа. Например: 700.33 */
        public Builder paymentAmount(BigDecimal paymentAmount) {
            this.paymentAmount = paymentAmount;
            return this;
        }

        /** Дата платежа. Используется стандарт ISO8601. Дата платежа, приведенная к часовому поясу
        Москвы. Например: "2018-03-29" */
        public Builder paymentDate(LocalDate paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        /** Номер платежа. Например: 9195 */
        public Builder paymentNumber(Integer paymentNumber) {
            this.paymentNumber = paymentNumber;
            return this;
        }

        /** Приоритет платежа. Например: "5" */
        public Builder paymentPriority(String paymentPriority) {
            this.paymentPriority = paymentPriority;
            return this;
        }

        /** Назначение платежа. Например: "Оплата по счету № 1 от 01.01.2021. Без НДС" */
        public Builder paymentPurpose(String paymentPurpose) {
            this.paymentPurpose = paymentPurpose;
            return this;
        }

        /** Поле 20. Заполняется только при платеже физ лицам на счета:('40810', '40817', '40823',
        '40824', '40826', '423', '30232', '40803', '40813', '40820', '426'). Допустимые значения
        1,2,3,4,5 и пусто. Например: "1" */
        public Builder codePurpose(String codePurpose) {
            this.codePurpose = codePurpose;
            return this;
        }

        /** Код УИН (поле 22). Например: "1" */
        public Builder supplierBillId(String supplierBillId) {
            this.supplierBillId = supplierBillId;
            return this;
        }

        /** Дата документа (поле 109). Используется стандарт ISO8601. Допустимо значение "0". Например:
        "2018-03-29" */
        public Builder taxInfoDocumentDate(String taxInfoDocumentDate) {
            this.taxInfoDocumentDate = taxInfoDocumentDate;
            return this;
        }

        /** Номера документа (поле 108). Например: "12" */
        public Builder taxInfoDocumentNumber(String taxInfoDocumentNumber) {
            this.taxInfoDocumentNumber = taxInfoDocumentNumber;
            return this;
        }

        /** КБК (поле 104). Например: "18210202020061000160" */
        public Builder taxInfoKBK(String taxInfoKBK) {
            this.taxInfoKBK = taxInfoKBK;
            return this;
        }

        /** ОКАТО (поле 105). Например: "65401364000" */
        public Builder taxInfoOKATO(String taxInfoOKATO) {
            this.taxInfoOKATO = taxInfoOKATO;
            return this;
        }

        /** Налоговый период (поле 107). Допустимо значение "0". Например: "МС.08.2009" */
        public Builder taxInfoPeriod(String taxInfoPeriod) {
            this.taxInfoPeriod = taxInfoPeriod;
            return this;
        }

        /** Основание (поле 106). Например: "ТП" */
        public Builder taxInfoReasonCode(String taxInfoReasonCode) {
            this.taxInfoReasonCode = taxInfoReasonCode;
            return this;
        }

        /** Статус (поле 101). Например: "08" */
        public Builder taxInfoStatus(String taxInfoStatus) {
            this.taxInfoStatus = taxInfoStatus;
            return this;
        }

        /** Код выплат из бюджета на ФЛ (поле 110). Например: "1" */
        public Builder budgetPaymentCode(String budgetPaymentCode) {
            this.budgetPaymentCode = budgetPaymentCode;
            return this;
        }

        /** Email для отправки платежного поручения. Например: "ivanov&#64;mail.com" */
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        /** Номер телефона для ГИС ГМП. Например: "+79999999999" */
        public Builder gisPhoneNumber(String gisPhoneNumber) {
            this.gisPhoneNumber = gisPhoneNumber;
            return this;
        }

        /** Адрес электронной почты для ГИС ГМП. Например: "ivanov&#64;mail.com" */
        public Builder gisEmail(String gisEmail) {
            this.gisEmail = gisEmail;
            return this;
        }

        public PaymentForSignRequestModel build() {
            return new PaymentForSignRequestModel(
                    this.accountCode,
                    this.bankCode,
                    this.payerINN,
                    this.payerKPP,
                    this.counterpartyBankBic,
                    this.counterpartyAccountNumber,
                    this.counterpartyINN,
                    this.counterpartyKPP,
                    this.counterpartyName,
                    this.counterpartyBankCorrAccount,
                    this.paymentAmount,
                    this.paymentDate,
                    this.paymentNumber,
                    this.paymentPriority,
                    this.paymentPurpose,
                    this.codePurpose,
                    this.supplierBillId,
                    this.taxInfoDocumentDate,
                    this.taxInfoDocumentNumber,
                    this.taxInfoKBK,
                    this.taxInfoOKATO,
                    this.taxInfoPeriod,
                    this.taxInfoReasonCode,
                    this.taxInfoStatus,
                    this.budgetPaymentCode,
                    this.email,
                    this.gisPhoneNumber,
                    this.gisEmail);
        }
    }
}
