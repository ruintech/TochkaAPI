package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RegisteredB2BQrCode
 *
 * @param payload Payload зарегистрированного QR-кода в СБП. Example:
 *        "https://qr.nspk.ru/AS1000670LSS7DN18SJQDNP4B05KLJL2"
 * @param qrcId Идентификатор QR-кода в СБП. Example: "AS000000000000000000000000000001"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisteredB2BQrCode(
        @JsonProperty("payload") String payload,
        @JsonProperty("qrcId") String qrcId) {

    /** Builder for {@link RegisteredB2BQrCode}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .payload(this.payload)
                .qrcId(this.qrcId);
    }

    /** Builder for {@link RegisteredB2BQrCode}. */
    public static final class Builder {

        private String payload;
        private String qrcId;

        /** Payload зарегистрированного QR-кода в СБП. Example:
        "https://qr.nspk.ru/AS1000670LSS7DN18SJQDNP4B05KLJL2" */
        public Builder payload(String payload) {
            this.payload = payload;
            return this;
        }

        /** Идентификатор QR-кода в СБП. Example: "AS000000000000000000000000000001" */
        public Builder qrcId(String qrcId) {
            this.qrcId = qrcId;
            return this;
        }

        public RegisteredB2BQrCode build() {
            return new RegisteredB2BQrCode(this.payload, this.qrcId);
        }
    }
}
