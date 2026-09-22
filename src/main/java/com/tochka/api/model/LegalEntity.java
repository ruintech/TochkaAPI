package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * LegalEntity
 *
 * @param status Статус объекта. Example: "Active"
 * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов). Example: "LF0000000001"
 * @param createdAt Время регистрации. Example: "2019-01-01T06:06:06.364+00:00"
 * @param address Юридический адрес. Example: "УЛИЦА ТАТАРСКАЯ Б. ДОМ 11" (optional)
 * @param city Город. Example: "Москва" (optional)
 * @param countryCode код страны-регистрации юридического лица, в формате "ISO 3166, Alpha-2". Example: "RU"
 * @param countrySubDivisionCode Код региона-регистрации юридического лица, первые две цифры кода ОКТМО. Example: "45"
 *        (optional)
 * @param zipCode Индекс. Example: "115184" (optional)
 * @param customerCode Уникальный код клиента. Example: "300000092"
 * @param entityType Организационно-правовая форма юридического лица. Example: "АО" (optional)
 * @param inn ИНН. Example: "7706812159"
 * @param kpp КПП. Example: "770501001" (optional)
 * @param name Полное наименование юридического лица. Example: "АКЦИОНЕРНОЕ ОБЩЕСТВО \"НАЦИОНАЛЬНАЯ СИСТЕМА
 *        ПЛАТЕЖНЫХ КАРТ\""
 * @param ogrn ОГРН. Example: "1147746831352"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LegalEntity(
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
        @JsonProperty("ogrn") String ogrn) {

    /** Builder for {@link LegalEntity}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
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
                .ogrn(this.ogrn);
    }

    /** Builder for {@link LegalEntity}. */
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

        /** Статус объекта. Example: "Active" */
        public Builder status(StatusEnum status) {
            this.status = status;
            return this;
        }

        /** Идентификатор зарегистрированного юрлица в СБП (12 символов). Example: "LF0000000001" */
        public Builder legalId(String legalId) {
            this.legalId = legalId;
            return this;
        }

        /** Время регистрации. Example: "2019-01-01T06:06:06.364+00:00" */
        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /** Юридический адрес. Example: "УЛИЦА ТАТАРСКАЯ Б. ДОМ 11" */
        public Builder address(String address) {
            this.address = address;
            return this;
        }

        /** Город. Example: "Москва" */
        public Builder city(String city) {
            this.city = city;
            return this;
        }

        /** код страны-регистрации юридического лица, в формате "ISO 3166, Alpha-2". Example: "RU" */
        public Builder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        /** Код региона-регистрации юридического лица, первые две цифры кода ОКТМО. Example: "45" */
        public Builder countrySubDivisionCode(String countrySubDivisionCode) {
            this.countrySubDivisionCode = countrySubDivisionCode;
            return this;
        }

        /** Индекс. Example: "115184" */
        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        /** Уникальный код клиента. Example: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /** Организационно-правовая форма юридического лица. Example: "АО" */
        public Builder entityType(String entityType) {
            this.entityType = entityType;
            return this;
        }

        /** ИНН. Example: "7706812159" */
        public Builder inn(String inn) {
            this.inn = inn;
            return this;
        }

        /** КПП. Example: "770501001" */
        public Builder kpp(String kpp) {
            this.kpp = kpp;
            return this;
        }

        /** Полное наименование юридического лица. Example: "АКЦИОНЕРНОЕ ОБЩЕСТВО \"НАЦИОНАЛЬНАЯ СИСТЕМА
        ПЛАТЕЖНЫХ КАРТ\"" */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /** ОГРН. Example: "1147746831352" */
        public Builder ogrn(String ogrn) {
            this.ogrn = ogrn;
            return this;
        }

        public LegalEntity build() {
            return new LegalEntity(
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
                    this.ogrn);
        }
    }
}
