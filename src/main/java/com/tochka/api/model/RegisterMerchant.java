package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RegisterMerchant
 *
 * @param address Юридический адрес. Example: "УЛИЦА ТАТАРСКАЯ Б. ДОМ 11"
 * @param city Город. Example: "Москва"
 * @param countryCode код страны-регистрации юридического лица, в формате "ISO 3166, Alpha-2". Example: "RU"
 * @param countrySubDivisionCode Код региона-регистрации юридического лица, первые две цифры кода ОКТМО. Example: "45"
 * @param zipCode Индекс. Example: "115184"
 * @param brandName Название ТСП (имя по вывеске). Example: "Кофейня у Артема"
 * @param capabilities Возможности ТСП по взаимодействию с покупателем. Example: "001"
 * @param contactPhoneNumber "Контактный номер телефона ТСП. Example: "79991234567" (optional)
 * @param mcc MCC код. Example: "4121"
 * @param scenario Сценарий использования ТСП. Example: "C2B" (optional)
 * @param salesMode Способ торговли ТСП. Example: "REMOTE" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterMerchant(
        @JsonProperty("address") String address,
        @JsonProperty("city") String city,
        @JsonProperty("countryCode") String countryCode,
        @JsonProperty("countrySubDivisionCode") String countrySubDivisionCode,
        @JsonProperty("zipCode") String zipCode,
        @JsonProperty("brandName") String brandName,
        @JsonProperty("capabilities") CapabilitiesEnum capabilities,
        @JsonProperty("contactPhoneNumber") String contactPhoneNumber,
        @JsonProperty("mcc") String mcc,
        @JsonProperty("scenario") ScenarioEnum scenario,
        @JsonProperty("salesMode") SalesModeEnum salesMode) {

    /** Builder for {@link RegisterMerchant}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .address(this.address)
                .city(this.city)
                .countryCode(this.countryCode)
                .countrySubDivisionCode(this.countrySubDivisionCode)
                .zipCode(this.zipCode)
                .brandName(this.brandName)
                .capabilities(this.capabilities)
                .contactPhoneNumber(this.contactPhoneNumber)
                .mcc(this.mcc)
                .scenario(this.scenario)
                .salesMode(this.salesMode);
    }

    /** Builder for {@link RegisterMerchant}. */
    public static final class Builder {

        private String address;
        private String city;
        private String countryCode;
        private String countrySubDivisionCode;
        private String zipCode;
        private String brandName;
        private CapabilitiesEnum capabilities;
        private String contactPhoneNumber;
        private String mcc;
        private ScenarioEnum scenario;
        private SalesModeEnum salesMode;

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

        /** Сценарий использования ТСП. Example: "C2B" */
        public Builder scenario(ScenarioEnum scenario) {
            this.scenario = scenario;
            return this;
        }

        /** Способ торговли ТСП. Example: "REMOTE" */
        public Builder salesMode(SalesModeEnum salesMode) {
            this.salesMode = salesMode;
            return this;
        }

        public RegisterMerchant build() {
            return new RegisterMerchant(
                    this.address,
                    this.city,
                    this.countryCode,
                    this.countrySubDivisionCode,
                    this.zipCode,
                    this.brandName,
                    this.capabilities,
                    this.contactPhoneNumber,
                    this.mcc,
                    this.scenario,
                    this.salesMode);
        }
    }
}
