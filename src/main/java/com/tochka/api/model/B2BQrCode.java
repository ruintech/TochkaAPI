package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * B2BQrCode
 *
 * @param payload Payload зарегистрированного QR-кода в СБП. Example:
 *        "https://qr.nspk.ru/AS1000670LSS7DN18SJQDNP4B05KLJL2"
 * @param accountId Уникальный и неизменный идентификатор счёта. Example: "40817810802000000008/044525104"
 * @param createdAt Время регистрации. Example: "2019-01-01T06:06:06.364+00:00"
 * @param merchantId Идентификатор ТСП. Example: "MF0000000001"
 * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов). Example: "LF0000000001"
 * @param qrcId Идентификатор QR-кода в СБП. Example: "AS000000000000000000000000000001"
 * @param amount Сумма в копейках. Example: 0 (optional)
 * @param ttl Период использования в минутах. Example: "60" (optional)
 * @param paymentPurpose Назначение платежа. Example: "Оплата по счету № 1 от 01.01.2021. Без НДС" (optional)
 * @param image image (optional)
 * @param redirectUrl Ссылка для автоматического возврата плательщика из приложения банка в приложение или на сайт
 *        ТСП. Example: "https://tsp.ru/path" (optional)
 * @param takeTax Taketax. Наличие НДС
 * @param totalTaxAmount Totaltaxamount. Сумма НДС в копейках (optional)
 * @param uip Уникальный идентификатор платежа, назначаемый получателем (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record B2BQrCode(
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
        @JsonProperty("redirectUrl") String redirectUrl,
        @JsonProperty("takeTax") Boolean takeTax,
        @JsonProperty("totalTaxAmount") Long totalTaxAmount,
        @JsonProperty("uip") String uip) {

    /** Builder for {@link B2BQrCode}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
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
                .redirectUrl(this.redirectUrl)
                .takeTax(this.takeTax)
                .totalTaxAmount(this.totalTaxAmount)
                .uip(this.uip);
    }

    /** Builder for {@link B2BQrCode}. */
    public static final class Builder {

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
        private String redirectUrl;
        private Boolean takeTax;
        private Long totalTaxAmount;
        private String uip;

        /** Payload зарегистрированного QR-кода в СБП. Example:
        "https://qr.nspk.ru/AS1000670LSS7DN18SJQDNP4B05KLJL2" */
        public Builder payload(String payload) {
            this.payload = payload;
            return this;
        }

        /** Уникальный и неизменный идентификатор счёта. Example: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Время регистрации. Example: "2019-01-01T06:06:06.364+00:00" */
        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /** Идентификатор ТСП. Example: "MF0000000001" */
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        /** Идентификатор зарегистрированного юрлица в СБП (12 символов). Example: "LF0000000001" */
        public Builder legalId(String legalId) {
            this.legalId = legalId;
            return this;
        }

        /** Идентификатор QR-кода в СБП. Example: "AS000000000000000000000000000001" */
        public Builder qrcId(String qrcId) {
            this.qrcId = qrcId;
            return this;
        }

        /** Сумма в копейках. Example: 0 */
        public Builder amount(Long amount) {
            this.amount = amount;
            return this;
        }

        /** Период использования в минутах. Example: "60" */
        public Builder ttl(String ttl) {
            this.ttl = ttl;
            return this;
        }

        /** Назначение платежа. Example: "Оплата по счету № 1 от 01.01.2021. Без НДС" */
        public Builder paymentPurpose(String paymentPurpose) {
            this.paymentPurpose = paymentPurpose;
            return this;
        }

        public Builder image(QrCodeContent image) {
            this.image = image;
            return this;
        }

        /** Ссылка для автоматического возврата плательщика из приложения банка в приложение или на сайт
        ТСП. Example: "https://tsp.ru/path" */
        public Builder redirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
            return this;
        }

        /** Taketax. Наличие НДС */
        public Builder takeTax(Boolean takeTax) {
            this.takeTax = takeTax;
            return this;
        }

        /** Totaltaxamount. Сумма НДС в копейках */
        public Builder totalTaxAmount(Long totalTaxAmount) {
            this.totalTaxAmount = totalTaxAmount;
            return this;
        }

        /** Уникальный идентификатор платежа, назначаемый получателем */
        public Builder uip(String uip) {
            this.uip = uip;
            return this;
        }

        public B2BQrCode build() {
            return new B2BQrCode(
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
                    this.redirectUrl,
                    this.takeTax,
                    this.totalTaxAmount,
                    this.uip);
        }
    }
}
