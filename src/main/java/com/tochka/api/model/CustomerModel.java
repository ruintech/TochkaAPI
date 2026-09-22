package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CustomerModel
 *
 * @param customerCode Уникальный код клиента. Например: "300000092"
 * @param customerType Тип клиент (физическое или юридическое лицо). Например: "Personal"
 * @param isResident Признак резидента. Например: true
 * @param taxCode ИНН. Например: "660000000000" (необязательное)
 * @param fullName Полное наименование. Например: "Индивидуальный Предприниматель Тест"
 * @param shortName Краткое наименование. Например: "ИП Тест" (необязательное)
 * @param kpp КПП. Например: "668501001" (необязательное)
 * @param customerOgrn ОГРН или ОГРНИП. Например: "319665800211661" (необязательное)
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

    /** Строитель {@link CustomerModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
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

    /** Строитель {@link CustomerModel}. */
    public static final class Builder {

        private String customerCode;
        private ExternalTypeEnum customerType;
        private Boolean isResident;
        private String taxCode;
        private String fullName;
        private String shortName;
        private String kpp;
        private String customerOgrn;

        /** Уникальный код клиента. Например: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /** Тип клиент (физическое или юридическое лицо). Например: "Personal" */
        public Builder customerType(ExternalTypeEnum customerType) {
            this.customerType = customerType;
            return this;
        }

        /** Признак резидента. Например: true */
        public Builder isResident(Boolean isResident) {
            this.isResident = isResident;
            return this;
        }

        /** ИНН. Например: "660000000000" */
        public Builder taxCode(String taxCode) {
            this.taxCode = taxCode;
            return this;
        }

        /** Полное наименование. Например: "Индивидуальный Предприниматель Тест" */
        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        /** Краткое наименование. Например: "ИП Тест" */
        public Builder shortName(String shortName) {
            this.shortName = shortName;
            return this;
        }

        /** КПП. Например: "668501001" */
        public Builder kpp(String kpp) {
            this.kpp = kpp;
            return this;
        }

        /** ОГРН или ОГРНИП. Например: "319665800211661" */
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
