package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * GetCashboxQRCodeListResponseModel
 *
 * @param qrCodes Список QR-кодов
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetCashboxQRCodeListResponseModel(
        @JsonProperty("qrCodes") List<GetCashboxQRCodeListResponseItemModel> qrCodes) {

    /** Builder for {@link GetCashboxQRCodeListResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .qrCodes(this.qrCodes);
    }

    /** Builder for {@link GetCashboxQRCodeListResponseModel}. */
    public static final class Builder {

        private List<GetCashboxQRCodeListResponseItemModel> qrCodes;

        /** Список QR-кодов */
        public Builder qrCodes(List<GetCashboxQRCodeListResponseItemModel> qrCodes) {
            this.qrCodes = qrCodes;
            return this;
        }

        public GetCashboxQRCodeListResponseModel build() {
            return new GetCashboxQRCodeListResponseModel(this.qrCodes);
        }
    }
}
