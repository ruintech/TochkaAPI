package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ContractorBankInfoModel
 *
 * @param schemeName БИК/SWIFT банка агента. Например: "RU.CBR.BIK" (необязательное)
 * @param identification БИК/SWIFT банка агента. Например: "000555777" (необязательное)
 * @param accountIdentification Номер кор. счета банка агента. Например: "000555777" (необязательное)
 * @param name Наименование банка агента. Например: "ПАО..." (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractorBankInfoModel(
        @JsonProperty("schemeName") FinancialInstitutionIdentificationEnum schemeName,
        @JsonProperty("identification") String identification,
        @JsonProperty("accountIdentification") String accountIdentification,
        @JsonProperty("name") String name) {

    /** Строитель {@link ContractorBankInfoModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .schemeName(this.schemeName)
                .identification(this.identification)
                .accountIdentification(this.accountIdentification)
                .name(this.name);
    }

    /** Строитель {@link ContractorBankInfoModel}. */
    public static final class Builder {

        private FinancialInstitutionIdentificationEnum schemeName;
        private String identification;
        private String accountIdentification;
        private String name;

        /** БИК/SWIFT банка агента. Например: "RU.CBR.BIK" */
        public Builder schemeName(FinancialInstitutionIdentificationEnum schemeName) {
            this.schemeName = schemeName;
            return this;
        }

        /** БИК/SWIFT банка агента. Например: "000555777" */
        public Builder identification(String identification) {
            this.identification = identification;
            return this;
        }

        /** Номер кор. счета банка агента. Например: "000555777" */
        public Builder accountIdentification(String accountIdentification) {
            this.accountIdentification = accountIdentification;
            return this;
        }

        /** Наименование банка агента. Например: "ПАО..." */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public ContractorBankInfoModel build() {
            return new ContractorBankInfoModel(this.schemeName, this.identification, this.accountIdentification, this.name);
        }
    }
}
