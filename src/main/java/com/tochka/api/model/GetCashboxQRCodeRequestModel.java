package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GetCashboxQRCodeRequestModel
 *
 * @param imageParams imageParams (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetCashboxQRCodeRequestModel(
        @JsonProperty("imageParams") QrCodeImageParams imageParams) {

    /** Строитель {@link GetCashboxQRCodeRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .imageParams(this.imageParams);
    }

    /** Строитель {@link GetCashboxQRCodeRequestModel}. */
    public static final class Builder {

        private QrCodeImageParams imageParams;

        public Builder imageParams(QrCodeImageParams imageParams) {
            this.imageParams = imageParams;
            return this;
        }

        public GetCashboxQRCodeRequestModel build() {
            return new GetCashboxQRCodeRequestModel(this.imageParams);
        }
    }
}
