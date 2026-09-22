package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * SBPPaymentList
 *
 * @param payments Payments
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SBPPaymentList(
        @JsonProperty("Payments") List<SBPPayment> payments) {

    /** Builder for {@link SBPPaymentList}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .payments(this.payments);
    }

    /** Builder for {@link SBPPaymentList}. */
    public static final class Builder {

        private List<SBPPayment> payments;

        /** Payments */
        public Builder payments(List<SBPPayment> payments) {
            this.payments = payments;
            return this;
        }

        public SBPPaymentList build() {
            return new SBPPaymentList(this.payments);
        }
    }
}
