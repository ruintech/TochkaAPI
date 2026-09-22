package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RegisterCashboxQrCodeRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterCashboxQrCodeRequestDataModel(
        @JsonProperty("Data") RegisterCashboxQrCodeRequestModel data) {

    /** Builder for {@link RegisterCashboxQrCodeRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Builder for {@link RegisterCashboxQrCodeRequestDataModel}. */
    public static final class Builder {

        private RegisterCashboxQrCodeRequestModel data;

        public Builder data(RegisterCashboxQrCodeRequestModel data) {
            this.data = data;
            return this;
        }

        public RegisterCashboxQrCodeRequestDataModel build() {
            return new RegisterCashboxQrCodeRequestDataModel(this.data);
        }
    }
}
