package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * Merchant
 *
 * @param status Статус объекта. Например: "Active"
 * @param createdAt Время регистрации. Например: "2019-01-01T06:06:06.364+00:00"
 * @param address Юридический адрес. Например: "УЛИЦА ТАТАРСКАЯ Б. ДОМ 11" (необязательное)
 * @param city Город. Например: "Москва" (необязательное)
 * @param countryCode код страны-регистрации юридического лица, в формате "ISO 3166, Alpha-2". Например: "RU"
 * @param countrySubDivisionCode Код региона-регистрации юридического лица, первые две цифры кода ОКТМО. Например: "45"
 *        (необязательное)
 * @param zipCode Индекс. Например: "115184" (необязательное)
 * @param merchantId Идентификатор ТСП. Например: "MF0000000001"
 * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов). Например: "LF0000000001"
 * @param brandName Название ТСП (имя по вывеске). Например: "Кофейня у Артема"
 * @param capabilities Возможности ТСП по взаимодействию с покупателем. Например: "001"
 * @param contactPhoneNumber "Контактный номер телефона ТСП. Например: "79991234567" (необязательное)
 * @param mcc MCC код. Например: "4121"
 * @param additionalContacts Дополнительные контакты (необязательное)
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

    /** Строитель {@link Merchant}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
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

    /** Строитель {@link Merchant}. */
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

        /** Статус объекта. Например: "Active" */
        public Builder status(StatusEnum status) {
            this.status = status;
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

        /** Идентификатор ТСП. Например: "MF0000000001" */
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        /** Идентификатор зарегистрированного юрлица в СБП (12 символов). Например: "LF0000000001" */
        public Builder legalId(String legalId) {
            this.legalId = legalId;
            return this;
        }

        /** Название ТСП (имя по вывеске). Например: "Кофейня у Артема" */
        public Builder brandName(String brandName) {
            this.brandName = brandName;
            return this;
        }

        /** Возможности ТСП по взаимодействию с покупателем. Например: "001" */
        public Builder capabilities(CapabilitiesEnum capabilities) {
            this.capabilities = capabilities;
            return this;
        }

        /** "Контактный номер телефона ТСП. Например: "79991234567" */
        public Builder contactPhoneNumber(String contactPhoneNumber) {
            this.contactPhoneNumber = contactPhoneNumber;
            return this;
        }

        /** MCC код. Например: "4121" */
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
