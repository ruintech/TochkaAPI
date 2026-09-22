package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * AcquiringPaymentOrderRefundRequestModel
 *
 * @param amount Сумма платежа. Не больше суммы оплаты. Example: "1234.00"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringPaymentOrderRefundRequestModel(
        @JsonProperty("amount") BigDecimal amount) {

    /** Builder for {@link AcquiringPaymentOrderRefundRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .amount(this.amount);
    }

    /** Builder for {@link AcquiringPaymentOrderRefundRequestModel}. */
    public static final class Builder {

        private BigDecimal amount;

        /** Сумма платежа. Не больше суммы оплаты. Example: "1234.00" */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public AcquiringPaymentOrderRefundRequestModel build() {
            return new AcquiringPaymentOrderRefundRequestModel(this.amount);
        }
    }
}
