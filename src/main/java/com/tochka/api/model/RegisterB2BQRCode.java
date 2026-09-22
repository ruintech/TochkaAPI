package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RegisterB2BQRCode
 *
 * @param amount Сумма в копейках. Example: 0
 * @param paymentPurpose Назначение платежа. Example: "Оплата по счету № 1 от 01.01.2021. Без НДС"
 * @param sourceName Название источника (системы создавшей QR-код). Система, создавшая QR-код
 * @param takeTax Taketax. Наличие НДС
 * @param totalTaxAmount Totaltaxamount. Сумма НДС в копейках (optional)
 * @param ttl Период использования в минутах. Example: 60 (optional)
 * @param redirectUrl URL адрес. Ссылка для автоматического возврата плательщика из приложения банка в приложение
 *        или на сайт ТСП (optional)
 * @param uip Уникальный идентификатор платежа, назначаемый получателем (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterB2BQRCode(
        @JsonProperty("amount") Long amount,
        @JsonProperty("paymentPurpose") String paymentPurpose,
        @JsonProperty("sourceName") String sourceName,
        @JsonProperty("takeTax") Boolean takeTax,
        @JsonProperty("totalTaxAmount") Long totalTaxAmount,
        @JsonProperty("ttl") Integer ttl,
        @JsonProperty("redirectUrl") String redirectUrl,
        @JsonProperty("uip") String uip) {

    /** Builder for {@link RegisterB2BQRCode}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .amount(this.amount)
                .paymentPurpose(this.paymentPurpose)
                .sourceName(this.sourceName)
                .takeTax(this.takeTax)
                .totalTaxAmount(this.totalTaxAmount)
                .ttl(this.ttl)
                .redirectUrl(this.redirectUrl)
                .uip(this.uip);
    }

    /** Builder for {@link RegisterB2BQRCode}. */
    public static final class Builder {

        private Long amount;
        private String paymentPurpose;
        private String sourceName;
        private Boolean takeTax;
        private Long totalTaxAmount;
        private Integer ttl;
        private String redirectUrl;
        private String uip;

        /** Сумма в копейках. Example: 0 */
        public Builder amount(Long amount) {
            this.amount = amount;
            return this;
        }

        /** Назначение платежа. Example: "Оплата по счету № 1 от 01.01.2021. Без НДС" */
        public Builder paymentPurpose(String paymentPurpose) {
            this.paymentPurpose = paymentPurpose;
            return this;
        }

        /** Название источника (системы создавшей QR-код). Система, создавшая QR-код */
        public Builder sourceName(String sourceName) {
            this.sourceName = sourceName;
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

        /** Период использования в минутах. Example: 60 */
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

        /** Уникальный идентификатор платежа, назначаемый получателем */
        public Builder uip(String uip) {
            this.uip = uip;
            return this;
        }

        public RegisterB2BQRCode build() {
            return new RegisterB2BQRCode(
                    this.amount,
                    this.paymentPurpose,
                    this.sourceName,
                    this.takeTax,
                    this.totalTaxAmount,
                    this.ttl,
                    this.redirectUrl,
                    this.uip);
        }
    }
}
