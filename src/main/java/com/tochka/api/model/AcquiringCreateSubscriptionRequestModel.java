package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * AcquiringCreateSubscriptionRequestModel
 *
 * @param customerCode Уникальный код клиента. Например: "300000092"
 * @param amount Сумма платежа, которая будет списываться в указанный клиентом период. Например: "1234.00"
 * @param purpose Назначение платежа. Например: "Перевод за оказанные услуги"
 * @param redirectUrl URL адрес, куда будет переправлен клиент после оплаты услуги. Например:
 *        "https://example.com" (необязательное)
 * @param failRedirectUrl URL адрес, куда будет переправлен клиент в случае неуспешной оплаты. Например:
 *        "https://example.com/fail" (необязательное)
 * @param saveCard Предложить покупателю сохранить карту. Например: true (необязательное)
 * @param consumerId Идентификатор покупателя. Например: "fedac807-078d-45ac-a43b-5c01c57edbf8" (необязательное)
 * @param merchantId Идентификатор торговой точки в интернет-эквайринге. Например: "200000000001056"
 *        (необязательное)
 * @param recurring Создание рекуррентной оплаты (необязательное)
 * @param options Опции подписки (необязательное)
 * @param paymentLinkId Уникальный номер заказа (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringCreateSubscriptionRequestModel(
        @JsonProperty("customerCode") String customerCode,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("purpose") String purpose,
        @JsonProperty("redirectUrl") String redirectUrl,
        @JsonProperty("failRedirectUrl") String failRedirectUrl,
        @JsonProperty("saveCard") Boolean saveCard,
        @JsonProperty("consumerId") String consumerId,
        @JsonProperty("merchantId") String merchantId,
        @JsonProperty("recurring") Boolean recurring,
        @JsonProperty("Options") AcquiringSubscriptionInputOptions options,
        @JsonProperty("paymentLinkId") String paymentLinkId) {

    /** Строитель {@link AcquiringCreateSubscriptionRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .customerCode(this.customerCode)
                .amount(this.amount)
                .purpose(this.purpose)
                .redirectUrl(this.redirectUrl)
                .failRedirectUrl(this.failRedirectUrl)
                .saveCard(this.saveCard)
                .consumerId(this.consumerId)
                .merchantId(this.merchantId)
                .recurring(this.recurring)
                .options(this.options)
                .paymentLinkId(this.paymentLinkId);
    }

    /** Строитель {@link AcquiringCreateSubscriptionRequestModel}. */
    public static final class Builder {

        private String customerCode;
        private BigDecimal amount;
        private String purpose;
        private String redirectUrl;
        private String failRedirectUrl;
        private Boolean saveCard;
        private String consumerId;
        private String merchantId;
        private Boolean recurring;
        private AcquiringSubscriptionInputOptions options;
        private String paymentLinkId;

        /** Уникальный код клиента. Например: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /** Сумма платежа, которая будет списываться в указанный клиентом период. Например: "1234.00" */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Назначение платежа. Например: "Перевод за оказанные услуги" */
        public Builder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        /** URL адрес, куда будет переправлен клиент после оплаты услуги. Например:
        "https://example.com" */
        public Builder redirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
            return this;
        }

        /** URL адрес, куда будет переправлен клиент в случае неуспешной оплаты. Например:
        "https://example.com/fail" */
        public Builder failRedirectUrl(String failRedirectUrl) {
            this.failRedirectUrl = failRedirectUrl;
            return this;
        }

        /** Предложить покупателю сохранить карту. Например: true */
        public Builder saveCard(Boolean saveCard) {
            this.saveCard = saveCard;
            return this;
        }

        /** Идентификатор покупателя. Например: "fedac807-078d-45ac-a43b-5c01c57edbf8" */
        public Builder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        /** Идентификатор торговой точки в интернет-эквайринге. Например: "200000000001056" */
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        /** Создание рекуррентной оплаты */
        public Builder recurring(Boolean recurring) {
            this.recurring = recurring;
            return this;
        }

        /** Опции подписки */
        public Builder options(AcquiringSubscriptionInputOptions options) {
            this.options = options;
            return this;
        }

        /** Уникальный номер заказа */
        public Builder paymentLinkId(String paymentLinkId) {
            this.paymentLinkId = paymentLinkId;
            return this;
        }

        public AcquiringCreateSubscriptionRequestModel build() {
            return new AcquiringCreateSubscriptionRequestModel(
                    this.customerCode,
                    this.amount,
                    this.purpose,
                    this.redirectUrl,
                    this.failRedirectUrl,
                    this.saveCard,
                    this.consumerId,
                    this.merchantId,
                    this.recurring,
                    this.options,
                    this.paymentLinkId);
        }
    }
}
