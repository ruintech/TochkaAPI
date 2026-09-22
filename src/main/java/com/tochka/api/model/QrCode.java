package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * QrCode
 *
 * @param status Статус объекта. Например: "Active"
 * @param payload Payload зарегистрированного QR-кода в СБП. Например:
 *        "https://qr.nspk.ru/AS1000670LSS7DN18SJQDNP4B05KLJL2"
 * @param accountId Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
 * @param createdAt Время регистрации. Например: "2019-01-01T06:06:06.364+00:00"
 * @param merchantId Идентификатор ТСП. Например: "MF0000000001"
 * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов). Например: "LF0000000001"
 * @param qrcId Идентификатор QR-кода в СБП. Например: "AS000000000000000000000000000001"
 * @param amount Сумма в копейках. Например: 0 (необязательное)
 * @param ttl Период использования в минутах. Например: "60" (необязательное)
 * @param paymentPurpose Назначение платежа. Например: "Оплата по счету № 1 от 01.01.2021. Без НДС" (необязательное)
 * @param image image (необязательное)
 * @param commissionPercent Размер комиссии в процентах. Например: 0
 * @param currency Валюта операции. Например: "RUB" (необязательное)
 * @param qrcType Тип QR-кода. Например: "01"
 * @param templateVersion Версия payload QR-кода. Например: "01"
 * @param sourceName название источника (системы создавшей QR-код). Например: "tochka.com" (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record QrCode(
        @JsonProperty("status") StatusEnum status,
        @JsonProperty("payload") String payload,
        @JsonProperty("accountId") String accountId,
        @JsonProperty("createdAt") String createdAt,
        @JsonProperty("merchantId") String merchantId,
        @JsonProperty("legalId") String legalId,
        @JsonProperty("qrcId") String qrcId,
        @JsonProperty("amount") Long amount,
        @JsonProperty("ttl") String ttl,
        @JsonProperty("paymentPurpose") String paymentPurpose,
        @JsonProperty("image") QrCodeContent image,
        @JsonProperty("commissionPercent") BigDecimal commissionPercent,
        @JsonProperty("currency") String currency,
        @JsonProperty("qrcType") QrTypeEnum qrcType,
        @JsonProperty("templateVersion") String templateVersion,
        @JsonProperty("sourceName") String sourceName) {

    /** Строитель {@link QrCode}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .status(this.status)
                .payload(this.payload)
                .accountId(this.accountId)
                .createdAt(this.createdAt)
                .merchantId(this.merchantId)
                .legalId(this.legalId)
                .qrcId(this.qrcId)
                .amount(this.amount)
                .ttl(this.ttl)
                .paymentPurpose(this.paymentPurpose)
                .image(this.image)
                .commissionPercent(this.commissionPercent)
                .currency(this.currency)
                .qrcType(this.qrcType)
                .templateVersion(this.templateVersion)
                .sourceName(this.sourceName);
    }

    /** Строитель {@link QrCode}. */
    public static final class Builder {

        private StatusEnum status;
        private String payload;
        private String accountId;
        private String createdAt;
        private String merchantId;
        private String legalId;
        private String qrcId;
        private Long amount;
        private String ttl;
        private String paymentPurpose;
        private QrCodeContent image;
        private BigDecimal commissionPercent;
        private String currency;
        private QrTypeEnum qrcType;
        private String templateVersion;
        private String sourceName;

        /** Статус объекта. Например: "Active" */
        public Builder status(StatusEnum status) {
            this.status = status;
            return this;
        }

        /** Payload зарегистрированного QR-кода в СБП. Например:
        "https://qr.nspk.ru/AS1000670LSS7DN18SJQDNP4B05KLJL2" */
        public Builder payload(String payload) {
            this.payload = payload;
            return this;
        }

        /** Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Время регистрации. Например: "2019-01-01T06:06:06.364+00:00" */
        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /** Идентификатор ТСП. Например: "MF0000000001" */
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        /** Идентификатор зарегистрированного юрлица в СБП (12 символов). Например: "LF0000000001" */
        public Builder legalId(String legalId) {
            this.legalId = legalId;
            return this;
        }

        /** Идентификатор QR-кода в СБП. Например: "AS000000000000000000000000000001" */
        public Builder qrcId(String qrcId) {
            this.qrcId = qrcId;
            return this;
        }

        /** Сумма в копейках. Например: 0 */
        public Builder amount(Long amount) {
            this.amount = amount;
            return this;
        }

        /** Период использования в минутах. Например: "60" */
        public Builder ttl(String ttl) {
            this.ttl = ttl;
            return this;
        }

        /** Назначение платежа. Например: "Оплата по счету № 1 от 01.01.2021. Без НДС" */
        public Builder paymentPurpose(String paymentPurpose) {
            this.paymentPurpose = paymentPurpose;
            return this;
        }

        public Builder image(QrCodeContent image) {
            this.image = image;
            return this;
        }

        /** Размер комиссии в процентах. Например: 0 */
        public Builder commissionPercent(BigDecimal commissionPercent) {
            this.commissionPercent = commissionPercent;
            return this;
        }

        /** Валюта операции. Например: "RUB" */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        /** Тип QR-кода. Например: "01" */
        public Builder qrcType(QrTypeEnum qrcType) {
            this.qrcType = qrcType;
            return this;
        }

        /** Версия payload QR-кода. Например: "01" */
        public Builder templateVersion(String templateVersion) {
            this.templateVersion = templateVersion;
            return this;
        }

        /** название источника (системы создавшей QR-код). Например: "tochka.com" */
        public Builder sourceName(String sourceName) {
            this.sourceName = sourceName;
            return this;
        }

        public QrCode build() {
            return new QrCode(
                    this.status,
                    this.payload,
                    this.accountId,
                    this.createdAt,
                    this.merchantId,
                    this.legalId,
                    this.qrcId,
                    this.amount,
                    this.ttl,
                    this.paymentPurpose,
                    this.image,
                    this.commissionPercent,
                    this.currency,
                    this.qrcType,
                    this.templateVersion,
                    this.sourceName);
        }
    }
}
