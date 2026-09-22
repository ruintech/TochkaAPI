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

    /** Builder for {@link PaymentForSignRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Builder for {@link PaymentForSignRequestDataModel}. */
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
