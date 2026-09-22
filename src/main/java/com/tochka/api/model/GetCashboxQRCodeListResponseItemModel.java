package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GetCashboxQRCodeListResponseItemModel
 *
 * @param payload Payload зарегистрированного QR-кода в СБП. Example:
 *        "https://qr.nspk.ru/AS1000670LSS7DN18SJQDNP4B05KLJL2"
 * @param accountId Уникальный и неизменный идентификатор счёта. Example: "40817810802000000008/044525104"
 * @param merchantId Идентификатор ТСП. Example: "MF0000000001"
 * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов). Example: "LF0000000001"
 * @param createdAt Время регистрации. Example: "2019-01-01T06:06:06.364+00:00"
 * @param qrcId Идентификатор QR-кода в СБП. Example: "AS000000000000000000000000000001"
 * @param amount Сумма в копейках. Example: 0 (optional)
 * @param currency Валюта операции. Example: "RUB" (optional)
 * @param paymentPurpose Назначение платежа. Example: "Оплата по счету № 1 от 01.01.2021. Без НДС" (optional)
 * @param paramsId Идентификатор активных значений параметров QR-кода. Example:
 *        "AS331309594501970709180285778247" (optional)
 * @param ttl Период использования в минутах. Example: 20 (optional)
 * @param commission Комиссия (optional)
 * @param redirectUrl Ссылка для автоматического возврата плательщика из приложения банка в приложение или на сайт
 *        ТСП. Example: "https://example.com/success" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetCashboxQRCodeListResponseItemModel(
        @JsonProperty("payload") String payload,
        @JsonProperty("accountId") String accountId,
        @JsonProperty("merchantId") String merchantId,
        @JsonProperty("legalId") String legalId,
        @JsonProperty("createdAt") String createdAt,
        @JsonProperty("qrcId") String qrcId,
        @JsonProperty("amount") Long amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("paymentPurpose") String paymentPurpose,
        @JsonProperty("paramsId") String paramsId,
        @JsonProperty("ttl") Integer ttl,
        @JsonProperty("commission") CashboxQrCodeOutputCommission commission,
        @JsonProperty("redirectUrl") String redirectUrl) {

    /** Builder for {@link GetCashboxQRCodeListResponseItemModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .payload(this.payload)
                .accountId(this.accountId)
                .merchantId(this.merchantId)
                .legalId(this.legalId)
                .createdAt(this.createdAt)
                .qrcId(this.qrcId)
                .amount(this.amount)
                .currency(this.currency)
                .paymentPurpose(this.paymentPurpose)
                .paramsId(this.paramsId)
                .ttl(this.ttl)
                .commission(this.commission)
                .redirectUrl(this.redirectUrl);
    }

    /** Builder for {@link GetCashboxQRCodeListResponseItemModel}. */
    public static final class Builder {

        private String payload;
        private String accountId;
        private String merchantId;
        private String legalId;
        private String createdAt;
        private String qrcId;
        private Long amount;
        private String currency;
        private String paymentPurpose;
        private String paramsId;
        private Integer ttl;
        private CashboxQrCodeOutputCommission commission;
        private String redirectUrl;

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

        /** Время регистрации. Example: "2019-01-01T06:06:06.364+00:00" */
        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
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

        /** Валюта операции. Example: "RUB" */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        /** Назначение платежа. Example: "Оплата по счету № 1 от 01.01.2021. Без НДС" */
        public Builder paymentPurpose(String paymentPurpose) {
            this.paymentPurpose = paymentPurpose;
            return this;
        }

        /** Идентификатор активных значений параметров QR-кода. Example:
        "AS331309594501970709180285778247" */
        public Builder paramsId(String paramsId) {
            this.paramsId = paramsId;
            return this;
        }

        /** Период использования в минутах. Example: 20 */
        public Builder ttl(Integer ttl) {
            this.ttl = ttl;
            return this;
        }

        /** Комиссия */
        public Builder commission(CashboxQrCodeOutputCommission commission) {
            this.commission = commission;
            return this;
        }

        /** Ссылка для автоматического возврата плательщика из приложения банка в приложение или на сайт
        ТСП. Example: "https://example.com/success" */
        public Builder redirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
            return this;
        }

        public GetCashboxQRCodeListResponseItemModel build() {
            return new GetCashboxQRCodeListResponseItemModel(
                    this.payload,
                    this.accountId,
                    this.merchantId,
                    this.legalId,
                    this.createdAt,
                    this.qrcId,
                    this.amount,
                    this.currency,
                    this.paymentPurpose,
                    this.paramsId,
                    this.ttl,
                    this.commission,
                    this.redirectUrl);
        }
    }
}
