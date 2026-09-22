package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RegisterCashboxQrCodeRequestModel
 *
 * @param merchantId Идентификатор ТСП. Например: "MF0000000001"
 * @param accountId Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
 * @param redirectUrl Ссылка для автоматического возврата плательщика из приложения банка в приложение или на сайт
 *        ТСП (необязательное)
 * @param imageParams Параметры изображения (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterCashboxQrCodeRequestModel(
        @JsonProperty("merchantId") String merchantId,
        @JsonProperty("accountId") String accountId,
        @JsonProperty("redirectUrl") String redirectUrl,
        @JsonProperty("imageParams") QrCodeImageParams imageParams) {

    /** Строитель {@link RegisterCashboxQrCodeRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .merchantId(this.merchantId)
                .accountId(this.accountId)
                .redirectUrl(this.redirectUrl)
                .imageParams(this.imageParams);
    }

    /** Строитель {@link RegisterCashboxQrCodeRequestModel}. */
    public static final class Builder {

        private String merchantId;
        private String accountId;
        private String redirectUrl;
        private QrCodeImageParams imageParams;

        /** Идентификатор ТСП. Например: "MF0000000001" */
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        /** Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Ссылка для автоматического возврата плательщика из приложения банка в приложение или на сайт
        ТСП */
        public Builder redirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
            return this;
        }

        /** Параметры изображения */
        public Builder imageParams(QrCodeImageParams imageParams) {
            this.imageParams = imageParams;
            return this;
        }

        public RegisterCashboxQrCodeRequestModel build() {
            return new RegisterCashboxQrCodeRequestModel(this.merchantId, this.accountId, this.redirectUrl, this.imageParams);
        }
    }
}
