package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 * AcquiringRetailerModel
 *
 * @param status Статус регистрации. Example: "REG"
 * @param isActive Статус готовности к работе. Example: true
 * @param mcc Код МСС. Example: "5111"
 * @param rate Комиссия. Example: 2, 2.6
 * @param name Наименование. Example: "ООО Альтер"
 * @param url Сайт регистрации. Example: "https://alter.ru" (optional)
 * @param merchantId ID мерчанта. Может отсутствовать при значениях поля status: NEW, ADDRESS_DADATA и
 *        OPEN_ACCOUNT. Example: "200000000001056" (optional)
 * @param terminalId ID терминала. Будет заполнен при значениях поля status: TERMINAL_CREATED, FILE_SENT, REG.
 *        Example: "20000032" (optional)
 * @param paymentModes Способ оплаты. Example: ["sbp", "card", "tinkoff", "dolyame"]
 * @param cashbox Название подключённой кассы. Example: "businessRu" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringRetailerModel(
        @JsonProperty("status") AcquiringRetailerStatus status,
        @JsonProperty("isActive") Boolean isActive,
        @JsonProperty("mcc") String mcc,
        @JsonProperty("rate") BigDecimal rate,
        @JsonProperty("name") String name,
        @JsonProperty("url") String url,
        @JsonProperty("merchantId") String merchantId,
        @JsonProperty("terminalId") String terminalId,
        @JsonProperty("paymentModes") List<AcquiringPaymentMode> paymentModes,
        @JsonProperty("cashbox") String cashbox) {

    /** Builder for {@link AcquiringRetailerModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .status(this.status)
                .isActive(this.isActive)
                .mcc(this.mcc)
                .rate(this.rate)
                .name(this.name)
                .url(this.url)
                .merchantId(this.merchantId)
                .terminalId(this.terminalId)
                .paymentModes(this.paymentModes)
                .cashbox(this.cashbox);
    }

    /** Builder for {@link AcquiringRetailerModel}. */
    public static final class Builder {

        private AcquiringRetailerStatus status;
        private Boolean isActive;
        private String mcc;
        private BigDecimal rate;
        private String name;
        private String url;
        private String merchantId;
        private String terminalId;
        private List<AcquiringPaymentMode> paymentModes;
        private String cashbox;

        /** Статус регистрации. Example: "REG" */
        public Builder status(AcquiringRetailerStatus status) {
            this.status = status;
            return this;
        }

        /** Статус готовности к работе. Example: true */
        public Builder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        /** Код МСС. Example: "5111" */
        public Builder mcc(String mcc) {
            this.mcc = mcc;
            return this;
        }

        /** Комиссия. Example: 2, 2.6 */
        public Builder rate(BigDecimal rate) {
            this.rate = rate;
            return this;
        }

        /** Наименование. Example: "ООО Альтер" */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /** Сайт регистрации. Example: "https://alter.ru" */
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        /** ID мерчанта. Может отсутствовать при значениях поля status: NEW, ADDRESS_DADATA и
        OPEN_ACCOUNT. Example: "200000000001056" */
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        /** ID терминала. Будет заполнен при значениях поля status: TERMINAL_CREATED, FILE_SENT, REG.
        Example: "20000032" */
        public Builder terminalId(String terminalId) {
            this.terminalId = terminalId;
            return this;
        }

        /** Способ оплаты. Example: ["sbp", "card", "tinkoff", "dolyame"] */
        public Builder paymentModes(List<AcquiringPaymentMode> paymentModes) {
            this.paymentModes = paymentModes;
            return this;
        }

        /** Название подключённой кассы. Example: "businessRu" */
        public Builder cashbox(String cashbox) {
            this.cashbox = cashbox;
            return this;
        }

        public AcquiringRetailerModel build() {
            return new AcquiringRetailerModel(
                    this.status,
                    this.isActive,
                    this.mcc,
                    this.rate,
                    this.name,
                    this.url,
                    this.merchantId,
                    this.terminalId,
                    this.paymentModes,
                    this.cashbox);
        }
    }
}
