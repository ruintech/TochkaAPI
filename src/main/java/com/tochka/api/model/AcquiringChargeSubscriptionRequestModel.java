package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * AcquiringChargeSubscriptionRequestModel
 *
 * @param amount Сумма платежа. Example: "1234.00"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringChargeSubscriptionRequestModel(
        @JsonProperty("amount") BigDecimal amount) {

    /** Builder for {@link AcquiringChargeSubscriptionRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .amount(this.amount);
    }

    /** Builder for {@link AcquiringChargeSubscriptionRequestModel}. */
    public static final class Builder {

        private BigDecimal amount;

        /** Сумма платежа. Example: "1234.00" */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public AcquiringChargeSubscriptionRequestModel build() {
            return new AcquiringChargeSubscriptionRequestModel(this.amount);
        }
    }
}
