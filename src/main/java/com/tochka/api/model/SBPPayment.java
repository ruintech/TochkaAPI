package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SBPPayment
 *
 * @param qrcId Идентификатор QR-кода в СБП. Например: "AS000000000000000000000000000001"
 * @param status Статус операции, инициированной Dynamic QR-кодом. Например: "InProgress"
 * @param message Текстовое представление статуса. Например: "Запрос обработан успешно"
 * @param refTransactionId Идентификатор операции, инициированной Dynamic QR-кодом. Например:
 *        "56746525-2768-5023-97aa-21a09c49d4d0"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SBPPayment(
        @JsonProperty("qrcId") String qrcId,
        @JsonProperty("status") SbpQrCodePaymentStatusEnum status,
        @JsonProperty("message") String message,
        @JsonProperty("refTransactionId") String refTransactionId) {

    /** Строитель {@link SBPPayment}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .qrcId(this.qrcId)
                .status(this.status)
                .message(this.message)
                .refTransactionId(this.refTransactionId);
    }

    /** Строитель {@link SBPPayment}. */
    public static final class Builder {

        private String qrcId;
        private SbpQrCodePaymentStatusEnum status;
        private String message;
        private String refTransactionId;

        /** Идентификатор QR-кода в СБП. Например: "AS000000000000000000000000000001" */
        public Builder qrcId(String qrcId) {
            this.qrcId = qrcId;
            return this;
        }

        /** Статус операции, инициированной Dynamic QR-кодом. Например: "InProgress" */
        public Builder status(SbpQrCodePaymentStatusEnum status) {
            this.status = status;
            return this;
        }

        /** Текстовое представление статуса. Например: "Запрос обработан успешно" */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /** Идентификатор операции, инициированной Dynamic QR-кодом. Например:
        "56746525-2768-5023-97aa-21a09c49d4d0" */
        public Builder refTransactionId(String refTransactionId) {
            this.refTransactionId = refTransactionId;
            return this;
        }

        public SBPPayment build() {
            return new SBPPayment(this.qrcId, this.status, this.message, this.refTransactionId);
        }
    }
}
