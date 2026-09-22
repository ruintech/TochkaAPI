package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * CustomerInfoResponseV3
 *
 * @param status Статус объекта. Например: "Active"
 * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов). Например: "LF0000000001"
 * @param createdAt Время регистрации. Например: "2019-01-01T06:06:06.364+00:00"
 * @param address Юридический адрес. Например: "УЛИЦА ТАТАРСКАЯ Б. ДОМ 11" (необязательное)
 * @param city Город. Например: "Москва" (необязательное)
 * @param countryCode код страны-регистрации юридического лица, в формате "ISO 3166, Alpha-2". Например: "RU"
 * @param countrySubDivisionCode Код региона-регистрации юридического лица, первые две цифры кода ОКТМО. Например: "45"
 *        (необязательное)
 * @param zipCode Индекс. Например: "115184" (необязательное)
 * @param customerCode Уникальный код клиента. Например: "300000092"
 * @param entityType Организационно-правовая форма юридического лица. Например: "АО" (необязательное)
 * @param inn ИНН. Например: "7706812159"
 * @param kpp КПП. Например: "770501001" (необязательное)
 * @param name Полное наименование юридического лица. Например: "АКЦИОНЕРНОЕ ОБЩЕСТВО \"НАЦИОНАЛЬНАЯ
 *        СИСТЕМА ПЛАТЕЖНЫХ КАРТ\""
 * @param ogrn ОГРН. Например: "1147746831352"
 * @param bankCode БИК банка клиента. Например: "041234678"
 * @param merchantList Merchantlist (необязательное)
 * @param accountList Accountlist (необязательное)
 * @param digitalRubleWallet DigitalRubleWallet (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerInfoResponseV3(
        @JsonProperty("status") StatusEnum status,
        @JsonProperty("legalId") String legalId,
        @JsonProperty("createdAt") String createdAt,
        @JsonProperty("address") String address,
        @JsonProperty("city") String city,
        @JsonProperty("countryCode") String countryCode,
        @JsonProperty("countrySubDivisionCode") String countrySubDivisionCode,
        @JsonProperty("zipCode") String zipCode,
        @JsonProperty("customerCode") String customerCode,
        @JsonProperty("entityType") String entityType,
        @JsonProperty("inn") String inn,
        @JsonProperty("kpp") String kpp,
        @JsonProperty("name") String name,
        @JsonProperty("ogrn") String ogrn,
        @JsonProperty("bankCode") String bankCode,
        @JsonProperty("MerchantList") List<Merchant> merchantList,
        @JsonProperty("AccountList") List<Account> accountList,
        @JsonProperty("DigitalRubleWallet") DigitalRubleWalletModel digitalRubleWallet) {

    /** Строитель {@link CustomerInfoResponseV3}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .status(this.status)
                .legalId(this.legalId)
                .createdAt(this.createdAt)
                .address(this.address)
                .city(this.city)
                .countryCode(this.countryCode)
                .countrySubDivisionCode(this.countrySubDivisionCode)
                .zipCode(this.zipCode)
                .customerCode(this.customerCode)
                .entityType(this.entityType)
                .inn(this.inn)
                .kpp(this.kpp)
                .name(this.name)
                .ogrn(this.ogrn)
                .bankCode(this.bankCode)
                .merchantList(this.merchantList)
                .accountList(this.accountList)
                .digitalRubleWallet(this.digitalRubleWallet);
    }

    /** Строитель {@link CustomerInfoResponseV3}. */
    public static final class Builder {

        private StatusEnum status;
        private String legalId;
        private String createdAt;
        private String address;
        private String city;
        private String countryCode;
        private String countrySubDivisionCode;
        private String zipCode;
        private String customerCode;
        private String entityType;
        private String inn;
        private String kpp;
        private String name;
        private String ogrn;
        private String bankCode;
        private List<Merchant> merchantList;
        private List<Account> accountList;
        private DigitalRubleWalletModel digitalRubleWallet;

        /** Статус объекта. Например: "Active" */
        public Builder status(StatusEnum status) {
            this.status = status;
            return this;
        }

        /** Идентификатор зарегистрированного юрлица в СБП (12 символов). Например: "LF0000000001" */
        public Builder legalId(String legalId) {
            this.legalId = legalId;
            return this;
        }

        /** Время регистрации. Например: "2019-01-01T06:06:06.364+00:00" */
        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /** Юридический адрес. Например: "УЛИЦА ТАТАРСКАЯ Б. ДОМ 11" */
        public Builder address(String address) {
            this.address = address;
            return this;
        }

        /** Город. Например: "Москва" */
        public Builder city(String city) {
            this.city = city;
            return this;
        }

        /** код страны-регистрации юридического лица, в формате "ISO 3166, Alpha-2". Например: "RU" */
        public Builder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /** Код региона-регистрации юридического лица, первые две цифры кода ОКТМО. Например: "45" */
        public Builder countrySubDivisionCode(String countrySubDivisionCode) {
            this.countrySubDivisionCode = countrySubDivisionCode;
            return this;
        }

        /** Индекс. Например: "115184" */
        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        /** Уникальный код клиента. Например: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /** Организационно-правовая форма юридического лица. Например: "АО" */
        public Builder entityType(String entityType) {
            this.entityType = entityType;
            return this;
        }

        /** ИНН. Например: "7706812159" */
        public Builder inn(String inn) {
            this.inn = inn;
            return this;
        }

        /** КПП. Например: "770501001" */
        public Builder kpp(String kpp) {
            this.kpp = kpp;
            return this;
        }

        /** Полное наименование юридического лица. Например: "АКЦИОНЕРНОЕ ОБЩЕСТВО \"НАЦИОНАЛЬНАЯ
        СИСТЕМА ПЛАТЕЖНЫХ КАРТ\"" */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /** ОГРН. Например: "1147746831352" */
        public Builder ogrn(String ogrn) {
            this.ogrn = ogrn;
            return this;
        }

        /** БИК банка клиента. Например: "041234678" */
        public Builder bankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /** Merchantlist */
        public Builder merchantList(List<Merchant> merchantList) {
            this.merchantList = merchantList;
            return this;
        }

        /** Accountlist */
        public Builder accountList(List<Account> accountList) {
            this.accountList = accountList;
            return this;
        }

        public Builder digitalRubleWallet(DigitalRubleWalletModel digitalRubleWallet) {
            this.digitalRubleWallet = digitalRubleWallet;
            return this;
        }

        public CustomerInfoResponseV3 build() {
            return new CustomerInfoResponseV3(
                    this.status,
                    this.legalId,
                    this.createdAt,
                    this.address,
                    this.city,
                    this.countryCode,
                    this.countrySubDivisionCode,
                    this.zipCode,
                    this.customerCode,
                    this.entityType,
                    this.inn,
                    this.kpp,
                    this.name,
                    this.ogrn,
                    this.bankCode,
                    this.merchantList,
                    this.accountList,
                    this.digitalRubleWallet);
        }
    }
}
