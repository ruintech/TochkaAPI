package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * QrCodePaymentStatus
 *
 * @param qrcId Идентификатор QR-кода в СБП. Example: "AS000000000000000000000000000001"
 * @param code Код операции. Example: "RQ00000"
 * @param status Статус операции, инициированной Dynamic QR-кодом. Example: "InProgress" (optional)
 * @param message Текстовое представление статуса. Example: "Запрос обработан успешно"
 * @param trxId Идентификатор операции, инициированной Dynamic QR-кодом. Example:
 *        "X1A2S3D5F6G7H8J9K0C4S5C6D7V5D1K2" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record QrCodePaymentStatus(
        @JsonProperty("qrcId") String qrcId,
        @JsonProperty("code") String code,
        @JsonProperty("status") QRCodePaymentStatusExternal status,
        @JsonProperty("message") String message,
        @JsonProperty("trxId") String trxId) {

    /** Builder for {@link QrCodePaymentStatus}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .qrcId(this.qrcId)
                .code(this.code)
                .status(this.status)
                .message(this.message)
                .trxId(this.trxId);
    }

    /** Builder for {@link QrCodePaymentStatus}. */
    public static final class Builder {

        private String qrcId;
        private String code;
        private QRCodePaymentStatusExternal status;
        private String message;
        private String trxId;

        /** Идентификатор QR-кода в СБП. Example: "AS000000000000000000000000000001" */
        public Builder qrcId(String qrcId) {
            this.qrcId = qrcId;
            return this;
        }

        /** Код операции. Example: "RQ00000" */
        public Builder code(String code) {
            this.code = code;
            return this;
        }

        /** Статус операции, инициированной Dynamic QR-кодом. Example: "InProgress" */
        public Builder status(QRCodePaymentStatusExternal status) {
            this.status = status;
            return this;
        }

        /** Текстовое представление статуса. Example: "Запрос обработан успешно" */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /** Идентификатор операции, инициированной Dynamic QR-кодом. Example:
        "X1A2S3D5F6G7H8J9K0C4S5C6D7V5D1K2" */
        public Builder trxId(String trxId) {
            this.trxId = trxId;
            return this;
        }

        public QrCodePaymentStatus build() {
            return new QrCodePaymentStatus(this.qrcId, this.code, this.status, this.message, this.trxId);
        }
    }
}
