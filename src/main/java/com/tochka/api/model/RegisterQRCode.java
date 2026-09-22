package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RegisterQRCode
 *
 * @param amount Сумма в копейках. Поле обязательно для заполнения, если тип QR = QR-Dynamic (необязательное)
 * @param currency Валюта операции. Например: "RUB" (необязательное)
 * @param paymentPurpose Назначение платежа. Например: "Оплата по счету № 1 от 01.01.2021. Без НДС"
 * @param qrcType Тип QR-кода. Например: "01"
 * @param imageParams Параметры изображения (необязательное)
 * @param sourceName Название источника. Cистема, создавшая QR-код (необязательное)
 * @param ttl Период использования QR-кода в минутах. Задается, только если тип QR = QR-Dynamic
 *        (необязательное)
 * @param redirectUrl URL адрес. Ссылка для автоматического возврата плательщика из приложения банка в приложение
 *        или на сайт ТСП (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterQRCode(
        @JsonProperty("amount") Long amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("paymentPurpose") String paymentPurpose,
        @JsonProperty("qrcType") QrTypeEnum qrcType,
        @JsonProperty("imageParams") QRCodeRequestParams imageParams,
        @JsonProperty("sourceName") String sourceName,
        @JsonProperty("ttl") Integer ttl,
        @JsonProperty("redirectUrl") String redirectUrl) {

    /** Строитель {@link RegisterQRCode}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .amount(this.amount)
                .currency(this.currency)
                .paymentPurpose(this.paymentPurpose)
                .qrcType(this.qrcType)
                .imageParams(this.imageParams)
                .sourceName(this.sourceName)
                .ttl(this.ttl)
                .redirectUrl(this.redirectUrl);
    }

    /** Строитель {@link RegisterQRCode}. */
    public static final class Builder {

        private Long amount;
        private String currency;
        private String paymentPurpose;
        private QrTypeEnum qrcType;
        private QRCodeRequestParams imageParams;
        private String sourceName;
        private Integer ttl;
        private String redirectUrl;

        /** Сумма в копейках. Поле обязательно для заполнения, если тип QR = QR-Dynamic */
        public Builder amount(Long amount) {
            this.amount = amount;
            return this;
        }

        /** Валюта операции. Например: "RUB" */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        /** Назначение платежа. Например: "Оплата по счету № 1 от 01.01.2021. Без НДС" */
        public Builder paymentPurpose(String paymentPurpose) {
            this.paymentPurpose = paymentPurpose;
            return this;
        }

        /** Тип QR-кода. Например: "01" */
        public Builder qrcType(QrTypeEnum qrcType) {
            this.qrcType = qrcType;
            return this;
        }

        /** Параметры изображения */
        public Builder imageParams(QRCodeRequestParams imageParams) {
            this.imageParams = imageParams;
            return this;
        }

        /** Название источника. Cистема, создавшая QR-код */
        public Builder sourceName(String sourceName) {
            this.sourceName = sourceName;
            return this;
        }

        /** Период использования QR-кода в минутах. Задается, только если тип QR = QR-Dynamic */
        public Builder ttl(Integer ttl) {
            this.ttl = ttl;
            return this;
        }

        /** URL адрес. Ссылка для автоматического возврата плательщика из приложения банка в приложение
        или на сайт ТСП */
        public Builder redirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
            return this;
        }

        public RegisterQRCode build() {
            return new RegisterQRCode(
                    this.amount,
                    this.currency,
                    this.paymentPurpose,
                    this.qrcType,
                    this.imageParams,
                    this.sourceName,
                    this.ttl,
                    this.redirectUrl);
        }
    }
}
