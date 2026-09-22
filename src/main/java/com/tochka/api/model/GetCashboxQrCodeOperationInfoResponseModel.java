package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GetCashboxQrCodeOperationInfoResponseModel
 *
 * @param qrCodeStatus Статус кассовой ссылки. Example: "WAITING_PAYMENT"
 * @param trxStatus Статус операции по кассовой ссылке. Example: "ACWP"
 * @param trxId Идентификатор операции. Example: "A1A2S3D5F6G7H8J9K0C4S5C6D7V5D1K2" (optional)
 * @param amount Сумма Операции в копейках. Целое, положительное число. Валюта операции – рубли РФ. Example:
 *        100000 (optional)
 * @param dateTime Дата и время выполнения операции. Example: "2019-01-01T06:06:06.364+00:00" (optional)
 * @param payerId Маскированный номер телефона клиента-плательщика. Example: "*********6731" (optional)
 * @param kzo Контрольное значение операции СБП. Example:
 *        "FDOS4JUETLYT639ADAFZ4GAUY9VSM2TG2Y595LQ20EKQF3JM1CIV4ZTZYA1EYIMFMEJSRB2UR7KATMA29Q"
 *        (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetCashboxQrCodeOperationInfoResponseModel(
        @JsonProperty("qrCodeStatus") SBPCashboxOperationQrCodeStatus qrCodeStatus,
        @JsonProperty("trxStatus") SBPCashboxTrxStatus trxStatus,
        @JsonProperty("trxId") String trxId,
        @JsonProperty("amount") Long amount,
        @JsonProperty("dateTime") String dateTime,
        @JsonProperty("payerId") String payerId,
        @JsonProperty("kzo") String kzo) {

    /** Builder for {@link GetCashboxQrCodeOperationInfoResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .qrCodeStatus(this.qrCodeStatus)
                .trxStatus(this.trxStatus)
                .trxId(this.trxId)
                .amount(this.amount)
                .dateTime(this.dateTime)
                .payerId(this.payerId)
                .kzo(this.kzo);
    }

    /** Builder for {@link GetCashboxQrCodeOperationInfoResponseModel}. */
    public static final class Builder {

        private SBPCashboxOperationQrCodeStatus qrCodeStatus;
        private SBPCashboxTrxStatus trxStatus;
        private String trxId;
        private Long amount;
        private String dateTime;
        private String payerId;
        private String kzo;

        /** Статус кассовой ссылки. Example: "WAITING_PAYMENT" */
        public Builder qrCodeStatus(SBPCashboxOperationQrCodeStatus qrCodeStatus) {
            this.qrCodeStatus = qrCodeStatus;
            return this;
        }

        /** Статус операции по кассовой ссылке. Example: "ACWP" */
        public Builder trxStatus(SBPCashboxTrxStatus trxStatus) {
            this.trxStatus = trxStatus;
            return this;
        }

        /** Идентификатор операции. Example: "A1A2S3D5F6G7H8J9K0C4S5C6D7V5D1K2" */
        public Builder trxId(String trxId) {
            this.trxId = trxId;
            return this;
        }

        /** Сумма Операции в копейках. Целое, положительное число. Валюта операции – рубли РФ. Example:
        100000 */
        public Builder amount(Long amount) {
            this.amount = amount;
            return this;
        }

        /** Дата и время выполнения операции. Example: "2019-01-01T06:06:06.364+00:00" */
        public Builder dateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        /** Маскированный номер телефона клиента-плательщика. Example: "*********6731" */
        public Builder payerId(String payerId) {
            this.payerId = payerId;
            return this;
        }

        /** Контрольное значение операции СБП. Example:
        "FDOS4JUETLYT639ADAFZ4GAUY9VSM2TG2Y595LQ20EKQF3JM1CIV4ZTZYA1EYIMFMEJSRB2UR7KATMA29Q" */
        public Builder kzo(String kzo) {
            this.kzo = kzo;
            return this;
        }

        public GetCashboxQrCodeOperationInfoResponseModel build() {
            return new GetCashboxQrCodeOperationInfoResponseModel(
                    this.qrCodeStatus,
                    this.trxStatus,
                    this.trxId,
                    this.amount,
                    this.dateTime,
                    this.payerId,
                    this.kzo);
        }
    }
}
