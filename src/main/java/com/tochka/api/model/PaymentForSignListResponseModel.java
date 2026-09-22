package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * PaymentForSignListResponseModel
 *
 * @param payment Payment
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentForSignListResponseModel(
        @JsonProperty("Payment") List<PaymentForSignListItemModel> payment) {

    /** Строитель {@link PaymentForSignListResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .payment(this.payment);
    }

    /** Строитель {@link PaymentForSignListResponseModel}. */
    public static final class Builder {

        private List<PaymentForSignListItemModel> payment;

        /** Payment */
        public Builder payment(List<PaymentForSignListItemModel> payment) {
            this.payment = payment;
            return this;
        }

        public PaymentForSignListResponseModel build() {
            return new PaymentForSignListResponseModel(this.payment);
        }
    }
}
