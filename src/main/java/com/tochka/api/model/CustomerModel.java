package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CustomerModel
 *
 * @param customerCode Уникальный код клиента. Example: "300000092"
 * @param customerType Тип клиент (физическое или юридическое лицо). Example: "Personal"
 * @param isResident Признак резидента. Example: true
 * @param taxCode ИНН. Example: "660000000000" (optional)
 * @param fullName Полное наименование. Example: "Индивидуальный Предприниматель Тест"
 * @param shortName Краткое наименование. Example: "ИП Тест" (optional)
 * @param kpp КПП. Example: "668501001" (optional)
 * @param customerOgrn ОГРН или ОГРНИП. Example: "319665800211661" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerModel(
        @JsonProperty("customerCode") String customerCode,
        @JsonProperty("customerType") ExternalTypeEnum customerType,
        @JsonProperty("isResident") Boolean isResident,
        @JsonProperty("taxCode") String taxCode,
        @JsonProperty("fullName") String fullName,
        @JsonProperty("shortName") String shortName,
        @JsonProperty("kpp") String kpp,
        @JsonProperty("customerOgrn") String customerOgrn) {

    /** Builder for {@link CustomerModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .customerCode(this.customerCode)
                .customerType(this.customerType)
                .isResident(this.isResident)
                .taxCode(this.taxCode)
                .fullName(this.fullName)
                .shortName(this.shortName)
                .kpp(this.kpp)
                .customerOgrn(this.customerOgrn);
    }

    /** Builder for {@link CustomerModel}. */
    public static final class Builder {

        private String customerCode;
        private ExternalTypeEnum customerType;
        private Boolean isResident;
        private String taxCode;
        private String fullName;
        private String shortName;
        private String kpp;
        private String customerOgrn;

        /** Уникальный код клиента. Example: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /** Тип клиент (физическое или юридическое лицо). Example: "Personal" */
        public Builder customerType(ExternalTypeEnum customerType) {
            this.customerType = customerType;
            return this;
        }

        /** Признак резидента. Example: true */
        public Builder isResident(Boolean isResident) {
            this.isResident = isResident;
            return this;
        }

        /** ИНН. Example: "660000000000" */
        public Builder taxCode(String taxCode) {
            this.taxCode = taxCode;
            return this;
        }

        /** Полное наименование. Example: "Индивидуальный Предприниматель Тест" */
        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        /** Краткое наименование. Example: "ИП Тест" */
        public Builder shortName(String shortName) {
            this.shortName = shortName;
            return this;
        }

        /** КПП. Example: "668501001" */
        public Builder kpp(String kpp) {
            this.kpp = kpp;
            return this;
        }

        /** ОГРН или ОГРНИП. Example: "319665800211661" */
        public Builder customerOgrn(String customerOgrn) {
            this.customerOgrn = customerOgrn;
            return this;
        }

        public CustomerModel build() {
            return new CustomerModel(
                    this.customerCode,
                    this.customerType,
                    this.isResident,
                    this.taxCode,
                    this.fullName,
                    this.shortName,
                    this.kpp,
                    this.customerOgrn);
        }
    }
}
