package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CustomerCodeAndBankCodeRequest
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerCodeAndBankCodeRequest(
        @JsonProperty("Data") CustomerCodeAndBankCode data) {

    /** Строитель {@link CustomerCodeAndBankCodeRequest}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link CustomerCodeAndBankCodeRequest}. */
    public static final class Builder {

        private CustomerCodeAndBankCode data;

        public Builder data(CustomerCodeAndBankCode data) {
            this.data = data;
            return this;
        }

        public CustomerCodeAndBankCodeRequest build() {
            return new CustomerCodeAndBankCodeRequest(this.data);
        }
    }
}
