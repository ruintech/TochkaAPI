package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CustomerCodeAndBankCode
 *
 * @param customerCode Уникальный код клиента. Example: "300000092"
 * @param bankCode БИК банка. Example: "044525104"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerCodeAndBankCode(
        @JsonProperty("customerCode") String customerCode,
        @JsonProperty("bankCode") String bankCode) {

    /** Builder for {@link CustomerCodeAndBankCode}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .customerCode(this.customerCode)
                .bankCode(this.bankCode);
    }

    /** Builder for {@link CustomerCodeAndBankCode}. */
    public static final class Builder {

        private String customerCode;
        private String bankCode;

        /** Уникальный код клиента. Example: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /** БИК банка. Example: "044525104" */
        public Builder bankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        public CustomerCodeAndBankCode build() {
            return new CustomerCodeAndBankCode(this.customerCode, this.bankCode);
        }
    }
}
