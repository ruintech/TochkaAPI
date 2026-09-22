package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * QRCodePaymentStatusListResponse
 *
 * @param paymentList Paymentlist
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record QRCodePaymentStatusListResponse(
        @JsonProperty("paymentList") List<QrCodePaymentStatus> paymentList) {

    /** Builder for {@link QRCodePaymentStatusListResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .paymentList(this.paymentList);
    }

    /** Builder for {@link QRCodePaymentStatusListResponse}. */
    public static final class Builder {

        private List<QrCodePaymentStatus> paymentList;

        /** Paymentlist */
        public Builder paymentList(List<QrCodePaymentStatus> paymentList) {
            this.paymentList = paymentList;
            return this;
        }

        public QRCodePaymentStatusListResponse build() {
            return new QRCodePaymentStatusListResponse(this.paymentList);
        }
    }
}
