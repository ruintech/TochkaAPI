package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ActivateCashboxQrCodeResponseModel
 *
 * @param qrcId Идентификатор QR-кода в СБП. Example: "AS000000000000000000000000000001"
 * @param amount Сумма в копейках. Example: 0
 * @param currency Валюта операции. Example: "RUB" (optional)
 * @param paramsId Идентификатор активных значений параметров QR-кода
 * @param paymentPurpose Назначение платежа. Example: "Оплата по счету № 1 от 01.01.2021. Без НДС" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ActivateCashboxQrCodeResponseModel(
        @JsonProperty("qrcId") String qrcId,
        @JsonProperty("amount") Long amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("paramsId") String paramsId,
        @JsonProperty("paymentPurpose") String paymentPurpose) {

    /** Builder for {@link ActivateCashboxQrCodeResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .qrcId(this.qrcId)
                .amount(this.amount)
                .currency(this.currency)
                .paramsId(this.paramsId)
                .paymentPurpose(this.paymentPurpose);
    }

    /** Builder for {@link ActivateCashboxQrCodeResponseModel}. */
    public static final class Builder {

        private String qrcId;
        private Long amount;
        private String currency;
        private String paramsId;
        private String paymentPurpose;

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

        /** Идентификатор активных значений параметров QR-кода */
        public Builder paramsId(String paramsId) {
            this.paramsId = paramsId;
            return this;
        }

        /** Назначение платежа. Example: "Оплата по счету № 1 от 01.01.2021. Без НДС" */
        public Builder paymentPurpose(String paymentPurpose) {
            this.paymentPurpose = paymentPurpose;
            return this;
        }

        public ActivateCashboxQrCodeResponseModel build() {
            return new ActivateCashboxQrCodeResponseModel(this.qrcId, this.amount, this.currency, this.paramsId, this.paymentPurpose);
        }
    }
}
