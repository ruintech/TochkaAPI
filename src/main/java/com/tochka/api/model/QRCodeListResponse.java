package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * QRCodeListResponse
 *
 * @param qrCodeList Qrcodelist
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record QRCodeListResponse(
        @JsonProperty("qrCodeList") List<QrCode> qrCodeList) {

    /** Строитель {@link QRCodeListResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .qrCodeList(this.qrCodeList);
    }

    /** Строитель {@link QRCodeListResponse}. */
    public static final class Builder {

        private List<QrCode> qrCodeList;

        /** Qrcodelist */
        public Builder qrCodeList(List<QrCode> qrCodeList) {
            this.qrCodeList = qrCodeList;
            return this;
        }

        public QRCodeListResponse build() {
            return new QRCodeListResponse(this.qrCodeList);
        }
    }
}
