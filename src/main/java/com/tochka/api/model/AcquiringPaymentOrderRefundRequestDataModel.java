package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringPaymentOrderRefundRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringPaymentOrderRefundRequestDataModel(
        @JsonProperty("Data") AcquiringPaymentOrderRefundRequestModel data) {

    /** Строитель {@link AcquiringPaymentOrderRefundRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link AcquiringPaymentOrderRefundRequestDataModel}. */
    public static final class Builder {

        private AcquiringPaymentOrderRefundRequestModel data;

        public Builder data(AcquiringPaymentOrderRefundRequestModel data) {
            this.data = data;
            return this;
        }

        public AcquiringPaymentOrderRefundRequestDataModel build() {
            return new AcquiringPaymentOrderRefundRequestDataModel(this.data);
        }
    }
}
