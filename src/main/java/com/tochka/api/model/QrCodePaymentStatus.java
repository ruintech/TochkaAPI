package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * QrCodePaymentStatus
 *
 * @param qrcId Идентификатор QR-кода в СБП. Например: "AS000000000000000000000000000001"
 * @param code Код операции. Например: "RQ00000"
 * @param status Статус операции, инициированной Dynamic QR-кодом. Например: "InProgress" (необязательное)
 * @param message Текстовое представление статуса. Например: "Запрос обработан успешно"
 * @param trxId Идентификатор операции, инициированной Dynamic QR-кодом. Например:
 *        "X1A2S3D5F6G7H8J9K0C4S5C6D7V5D1K2" (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record QrCodePaymentStatus(
        @JsonProperty("qrcId") String qrcId,
        @JsonProperty("code") String code,
        @JsonProperty("status") QRCodePaymentStatusExternal status,
        @JsonProperty("message") String message,
        @JsonProperty("trxId") String trxId) {

    /** Строитель {@link QrCodePaymentStatus}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .qrcId(this.qrcId)
                .code(this.code)
                .status(this.status)
                .message(this.message)
                .trxId(this.trxId);
    }

    /** Строитель {@link QrCodePaymentStatus}. */
    public static final class Builder {

        private String qrcId;
        private String code;
        private QRCodePaymentStatusExternal status;
        private String message;
        private String trxId;

        /** Идентификатор QR-кода в СБП. Например: "AS000000000000000000000000000001" */
        public Builder qrcId(String qrcId) {
            this.qrcId = qrcId;
            return this;
        }

        /** Код операции. Например: "RQ00000" */
        public Builder code(String code) {
            this.code = code;
            return this;
        }

        /** Статус операции, инициированной Dynamic QR-кодом. Например: "InProgress" */
        public Builder status(QRCodePaymentStatusExternal status) {
            this.status = status;
            return this;
        }

        /** Текстовое представление статуса. Например: "Запрос обработан успешно" */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /** Идентификатор операции, инициированной Dynamic QR-кодом. Например:
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
