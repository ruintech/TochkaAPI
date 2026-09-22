package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ActivateCashboxQrCodeRequestModel
 *
 * @param amount Сумма в копейках.. Например: "500000"
 * @param currency Currency. Валюта операции (необязательное)
 * @param paymentPurpose Назначение платежа. Например: "Оплата по счету № 1 от 01.01.2021. Без НДС" (необязательное)
 * @param ttl Период использования QR-кода в минутах. Например: 7 (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ActivateCashboxQrCodeRequestModel(
        @JsonProperty("amount") Long amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("paymentPurpose") String paymentPurpose,
        @JsonProperty("ttl") Integer ttl) {

    /** Строитель {@link ActivateCashboxQrCodeRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .amount(this.amount)
                .currency(this.currency)
                .paymentPurpose(this.paymentPurpose)
                .ttl(this.ttl);
    }

    /** Строитель {@link ActivateCashboxQrCodeRequestModel}. */
    public static final class Builder {

        private Long amount;
        private String currency;
        private String paymentPurpose;
        private Integer ttl;

        /** Сумма в копейках.. Например: "500000" */
        public Builder amount(Long amount) {
            this.amount = amount;
            return this;
        }

        /** Currency. Валюта операции */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        /** Назначение платежа. Например: "Оплата по счету № 1 от 01.01.2021. Без НДС" */
        public Builder paymentPurpose(String paymentPurpose) {
            this.paymentPurpose = paymentPurpose;
            return this;
        }

        /** Период использования QR-кода в минутах. Например: 7 */
        public Builder ttl(Integer ttl) {
            this.ttl = ttl;
            return this;
        }

        public ActivateCashboxQrCodeRequestModel build() {
            return new ActivateCashboxQrCodeRequestModel(this.amount, this.currency, this.paymentPurpose, this.ttl);
        }
    }
}
