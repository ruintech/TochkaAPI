package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RegisterMerchantRequest
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterMerchantRequest(
        @JsonProperty("Data") RegisterMerchant data) {

    /** Строитель {@link RegisterMerchantRequest}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link RegisterMerchantRequest}. */
    public static final class Builder {

        private RegisterMerchant data;

        public Builder data(RegisterMerchant data) {
            this.data = data;
            return this;
        }

        public RegisterMerchantRequest build() {
            return new RegisterMerchantRequest(this.data);
        }
    }
}
