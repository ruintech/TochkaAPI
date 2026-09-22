package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PaymentForSignRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentForSignRequestDataModel(
        @JsonProperty("Data") PaymentForSignRequestModel data) {

    /** Строитель {@link PaymentForSignRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link PaymentForSignRequestDataModel}. */
    public static final class Builder {

        private PaymentForSignRequestModel data;

        public Builder data(PaymentForSignRequestModel data) {
            this.data = data;
            return this;
        }

        public PaymentForSignRequestDataModel build() {
            return new PaymentForSignRequestDataModel(this.data);
        }
    }
}
