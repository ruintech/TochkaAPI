package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SecondSideModel
 *
 * @param accountId Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
 *        (необязательное)
 * @param legalAddress Юридический адрес. Например: "624205, РОССИЯ, СВЕРДЛОВСКАЯ обл, ЛЕСНОЙ г, ЛЕНИНА ул, ДОМ 96,
 *        офис КВ. 19" (необязательное)
 * @param kpp КПП. Например: "668101001" (необязательное)
 * @param bankName Название банка. Например: "ООО БАНК ТОЧКА" (необязательное)
 * @param bankCorrAccount Корреспондентский счет банка. Например: "30101810745374525104" (необязательное)
 * @param taxCode ИНН покупателя или заказчика. Например: "660000000000"
 * @param type Тип покупателя или заказчика. Например: "company"
 * @param secondSideName Наименование покупателя или заказчика. Например: "ООО Студия дизайна М-АРТ" (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SecondSideModel(
        @JsonProperty("accountId") String accountId,
        @JsonProperty("legalAddress") String legalAddress,
        @JsonProperty("kpp") String kpp,
        @JsonProperty("bankName") String bankName,
        @JsonProperty("bankCorrAccount") String bankCorrAccount,
        @JsonProperty("taxCode") String taxCode,
        @JsonProperty("type") CounterpartTypeEnum type,
        @JsonProperty("secondSideName") String secondSideName) {

    /** Строитель {@link SecondSideModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .accountId(this.accountId)
                .legalAddress(this.legalAddress)
                .kpp(this.kpp)
                .bankName(this.bankName)
                .bankCorrAccount(this.bankCorrAccount)
                .taxCode(this.taxCode)
                .type(this.type)
                .secondSideName(this.secondSideName);
    }

    /** Строитель {@link SecondSideModel}. */
    public static final class Builder {

        private String accountId;
        private String legalAddress;
        private String kpp;
        private String bankName;
        private String bankCorrAccount;
        private String taxCode;
        private CounterpartTypeEnum type;
        private String secondSideName;

        /** Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Юридический адрес. Например: "624205, РОССИЯ, СВЕРДЛОВСКАЯ обл, ЛЕСНОЙ г, ЛЕНИНА ул, ДОМ 96,
        офис КВ. 19" */
        public Builder legalAddress(String legalAddress) {
            this.legalAddress = legalAddress;
            return this;
        }

        /** КПП. Например: "668101001" */
        public Builder kpp(String kpp) {
            this.kpp = kpp;
            return this;
        }

        /** Название банка. Например: "ООО БАНК ТОЧКА" */
        public Builder bankName(String bankName) {
            this.bankName = bankName;
            return this;
        }

        /** Корреспондентский счет банка. Например: "30101810745374525104" */
        public Builder bankCorrAccount(String bankCorrAccount) {
            this.bankCorrAccount = bankCorrAccount;
            return this;
        }

        /** ИНН покупателя или заказчика. Например: "660000000000" */
        public Builder taxCode(String taxCode) {
            this.taxCode = taxCode;
            return this;
        }

        /** Тип покупателя или заказчика. Например: "company" */
        public Builder type(CounterpartTypeEnum type) {
            this.type = type;
            return this;
        }

        /** Наименование покупателя или заказчика. Например: "ООО Студия дизайна М-АРТ" */
        public Builder secondSideName(String secondSideName) {
            this.secondSideName = secondSideName;
            return this;
        }

        public SecondSideModel build() {
            return new SecondSideModel(
                    this.accountId,
                    this.legalAddress,
                    this.kpp,
                    this.bankName,
                    this.bankCorrAccount,
                    this.taxCode,
                    this.type,
                    this.secondSideName);
        }
    }
}
