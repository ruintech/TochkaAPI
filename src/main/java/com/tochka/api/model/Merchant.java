package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * Merchant
 *
 * @param status Статус объекта. Example: "Active"
 * @param createdAt Время регистрации. Example: "2019-01-01T06:06:06.364+00:00"
 * @param address Юридический адрес. Example: "УЛИЦА ТАТАРСКАЯ Б. ДОМ 11" (optional)
 * @param city Город. Example: "Москва" (optional)
 * @param countryCode код страны-регистрации юридического лица, в формате "ISO 3166, Alpha-2". Example: "RU"
 * @param countrySubDivisionCode Код региона-регистрации юридического лица, первые две цифры кода ОКТМО. Example: "45"
 *        (optional)
 * @param zipCode Индекс. Example: "115184" (optional)
 * @param merchantId Идентификатор ТСП. Example: "MF0000000001"
 * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов). Example: "LF0000000001"
 * @param brandName Название ТСП (имя по вывеске). Example: "Кофейня у Артема"
 * @param capabilities Возможности ТСП по взаимодействию с покупателем. Example: "001"
 * @param contactPhoneNumber "Контактный номер телефона ТСП. Example: "79991234567" (optional)
 * @param mcc MCC код. Example: "4121"
 * @param additionalContacts Дополнительные контакты (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Merchant(
        @JsonProperty("status") StatusEnum status,
        @JsonProperty("createdAt") String createdAt,
        @JsonProperty("address") String address,
        @JsonProperty("city") String city,
        @JsonProperty("countryCode") String countryCode,
        @JsonProperty("countrySubDivisionCode") String countrySubDivisionCode,
        @JsonProperty("zipCode") String zipCode,
        @JsonProperty("merchantId") String merchantId,
        @JsonProperty("legalId") String legalId,
        @JsonProperty("brandName") String brandName,
        @JsonProperty("capabilities") CapabilitiesEnum capabilities,
        @JsonProperty("contactPhoneNumber") String contactPhoneNumber,
        @JsonProperty("mcc") String mcc,
        @JsonProperty("additionalContacts") List<Map<String, Object>> additionalContacts) {

    /** Builder for {@link Merchant}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .status(this.status)
                .createdAt(this.createdAt)
                .address(this.address)
                .city(this.city)
                .countryCode(this.countryCode)
                .countrySubDivisionCode(this.countrySubDivisionCode)
                .zipCode(this.zipCode)
                .merchantId(this.merchantId)
                .legalId(this.legalId)
                .brandName(this.brandName)
                .capabilities(this.capabilities)
                .contactPhoneNumber(this.contactPhoneNumber)
                .mcc(this.mcc)
                .additionalContacts(this.additionalContacts);
    }

    /** Builder for {@link Merchant}. */
    public static final class Builder {

        private StatusEnum status;
        private String createdAt;
        private String address;
        private String city;
        private String countryCode;
        private String countrySubDivisionCode;
        private String zipCode;
        private String merchantId;
        private String legalId;
        private String brandName;
        private CapabilitiesEnum capabilities;
        private String contactPhoneNumber;
        private String mcc;
        private List<Map<String, Object>> additionalContacts;

        /** Статус объекта. Example: "Active" */
        public Builder status(StatusEnum status) {
            this.status = status;
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

        /** Идентификатор ТСП. Example: "MF0000000001" */
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        /** Идентификатор зарегистрированного юрлица в СБП (12 символов). Example: "LF0000000001" */
        public Builder legalId(String legalId) {
            this.legalId = legalId;
            return this;
        }

        /** Название ТСП (имя по вывеске). Example: "Кофейня у Артема" */
        public Builder brandName(String brandName) {
            this.brandName = brandName;
            return this;
        }

        /** Возможности ТСП по взаимодействию с покупателем. Example: "001" */
        public Builder capabilities(CapabilitiesEnum capabilities) {
            this.capabilities = capabilities;
            return this;
        }

        /** "Контактный номер телефона ТСП. Example: "79991234567" */
        public Builder contactPhoneNumber(String contactPhoneNumber) {
            this.contactPhoneNumber = contactPhoneNumber;
            return this;
        }

        /** MCC код. Example: "4121" */
        public Builder mcc(String mcc) {
            this.mcc = mcc;
            return this;
        }

        /** Дополнительные контакты */
        public Builder additionalContacts(List<Map<String, Object>> additionalContacts) {
            this.additionalContacts = additionalContacts;
            return this;
        }

        public Merchant build() {
            return new Merchant(
                    this.status,
                    this.createdAt,
                    this.address,
                    this.city,
                    this.countryCode,
                    this.countrySubDivisionCode,
                    this.zipCode,
                    this.merchantId,
                    this.legalId,
                    this.brandName,
                    this.capabilities,
                    this.contactPhoneNumber,
                    this.mcc,
                    this.additionalContacts);
        }
    }
}
