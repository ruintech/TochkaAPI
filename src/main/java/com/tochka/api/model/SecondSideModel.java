package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SecondSideModel
 *
 * @param accountId Уникальный и неизменный идентификатор счёта. Example: "40817810802000000008/044525104"
 *        (optional)
 * @param legalAddress Юридический адрес. Example: "624205, РОССИЯ, СВЕРДЛОВСКАЯ обл, ЛЕСНОЙ г, ЛЕНИНА ул, ДОМ 96,
 *        офис КВ. 19" (optional)
 * @param kpp КПП. Example: "668101001" (optional)
 * @param bankName Название банка. Example: "ООО БАНК ТОЧКА" (optional)
 * @param bankCorrAccount Корреспондентский счет банка. Example: "30101810745374525104" (optional)
 * @param taxCode ИНН покупателя или заказчика. Example: "660000000000"
 * @param type Тип покупателя или заказчика. Example: "company"
 * @param secondSideName Наименование покупателя или заказчика. Example: "ООО Студия дизайна М-АРТ" (optional)
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

    /** Builder for {@link SecondSideModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
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

    /** Builder for {@link SecondSideModel}. */
    public static final class Builder {

        private String accountId;
        private String legalAddress;
        private String kpp;
        private String bankName;
        private String bankCorrAccount;
        private String taxCode;
        private CounterpartTypeEnum type;
        private String secondSideName;

        /** Уникальный и неизменный идентификатор счёта. Example: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Юридический адрес. Example: "624205, РОССИЯ, СВЕРДЛОВСКАЯ обл, ЛЕСНОЙ г, ЛЕНИНА ул, ДОМ 96,
        офис КВ. 19" */
        public Builder legalAddress(String legalAddress) {
            this.legalAddress = legalAddress;
            return this;
        }

        /** КПП. Example: "668101001" */
        public Builder kpp(String kpp) {
            this.kpp = kpp;
            return this;
        }

        /** Название банка. Example: "ООО БАНК ТОЧКА" */
        public Builder bankName(String bankName) {
            this.bankName = bankName;
            return this;
        }

        /** Корреспондентский счет банка. Example: "30101810745374525104" */
        public Builder bankCorrAccount(String bankCorrAccount) {
            this.bankCorrAccount = bankCorrAccount;
            return this;
        }

        /** ИНН покупателя или заказчика. Example: "660000000000" */
        public Builder taxCode(String taxCode) {
            this.taxCode = taxCode;
            return this;
        }

        /** Тип покупателя или заказчика. Example: "company" */
        public Builder type(CounterpartTypeEnum type) {
            this.type = type;
            return this;
        }

        /** Наименование покупателя или заказчика. Example: "ООО Студия дизайна М-АРТ" */
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
