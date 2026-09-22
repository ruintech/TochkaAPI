package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RegisterCashboxQrCodeResponseModel
 *
 * @param payload Payload зарегистрированного QR-кода в СБП. Например:
 *        "https://qr.nspk.ru/AS1000670LSS7DN18SJQDNP4B05KLJL2"
 * @param qrcId Идентификатор QR-кода в СБП. Например: "AS000000000000000000000000000001"
 * @param image image (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterCashboxQrCodeResponseModel(
        @JsonProperty("payload") String payload,
        @JsonProperty("qrcId") String qrcId,
        @JsonProperty("image") QrCodeContent image) {

    /** Строитель {@link RegisterCashboxQrCodeResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .payload(this.payload)
                .qrcId(this.qrcId)
                .image(this.image);
    }

    /** Строитель {@link RegisterCashboxQrCodeResponseModel}. */
    public static final class Builder {

        private String payload;
        private String qrcId;
        private QrCodeContent image;

        /** Payload зарегистрированного QR-кода в СБП. Например:
        "https://qr.nspk.ru/AS1000670LSS7DN18SJQDNP4B05KLJL2" */
        public Builder payload(String payload) {
            this.payload = payload;
            return this;
        }

        /** Идентификатор QR-кода в СБП. Например: "AS000000000000000000000000000001" */
        public Builder qrcId(String qrcId) {
            this.qrcId = qrcId;
            return this;
        }

        public Builder image(QrCodeContent image) {
            this.image = image;
            return this;
        }

        public RegisterCashboxQrCodeResponseModel build() {
            return new RegisterCashboxQrCodeResponseModel(this.payload, this.qrcId, this.image);
        }
    }
}
