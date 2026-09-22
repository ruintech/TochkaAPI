package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringCreatePaymentOperationRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringCreatePaymentOperationRequestDataModel(
        @JsonProperty("Data") AcquiringCreatePaymentOperationRequestModel data) {

    /** Строитель {@link AcquiringCreatePaymentOperationRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link AcquiringCreatePaymentOperationRequestDataModel}. */
    public static final class Builder {

        private AcquiringCreatePaymentOperationRequestModel data;

        public Builder data(AcquiringCreatePaymentOperationRequestModel data) {
            this.data = data;
            return this;
        }

        public AcquiringCreatePaymentOperationRequestDataModel build() {
            return new AcquiringCreatePaymentOperationRequestDataModel(this.data);
        }
    }
}
