package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GetCashboxQRCodeRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetCashboxQRCodeRequestDataModel(
        @JsonProperty("Data") GetCashboxQRCodeRequestModel data) {

    /** Builder for {@link GetCashboxQRCodeRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Builder for {@link GetCashboxQRCodeRequestDataModel}. */
    public static final class Builder {

        private GetCashboxQRCodeRequestModel data;

        public Builder data(GetCashboxQRCodeRequestModel data) {
            this.data = data;
            return this;
        }

        public GetCashboxQRCodeRequestDataModel build() {
            return new GetCashboxQRCodeRequestDataModel(this.data);
        }
    }
}
