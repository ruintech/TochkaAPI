package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 * AcquiringCreateSubscriptionWithReceiptRequestModel
 *
 * @param customerCode Уникальный код клиента. Example: "300000092"
 * @param amount Сумма платежа, которая будет списываться в указанный клиентом период. Example: "1234.00"
 * @param purpose Назначение платежа. Example: "Перевод за оказанные услуги"
 * @param redirectUrl URL адрес, куда будет переправлен клиент после оплаты услуги. Example: "https://example.com"
 *        (optional)
 * @param failRedirectUrl URL адрес, куда будет переправлен клиент в случае неуспешной оплаты. Example:
 *        "https://example.com/fail" (optional)
 * @param saveCard Предложить покупателю сохранить карту. Example: true (optional)
 * @param consumerId Идентификатор покупателя. Example: "fedac807-078d-45ac-a43b-5c01c57edbf8" (optional)
 * @param merchantId Идентификатор торговой точки в интернет-эквайринге. Example: "200000000001056" (optional)
 * @param recurring Создание рекуррентной оплаты (optional)
 * @param options Опции подписки (optional)
 * @param paymentLinkId Уникальный номер заказа (optional)
 * @param taxSystemCode Система налогообложения. Example: "osn" (optional)
 * @param client Данные покупателя
 * @param items Список товаров в заказе
 * @param supplier Данные поставщика (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringCreateSubscriptionWithReceiptRequestModel(
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
        @JsonProperty("paymentLinkId") String paymentLinkId,
        @JsonProperty("taxSystemCode") TaxSystemCodeInput taxSystemCode,
        @JsonProperty("Client") ReceiptClientModel client,
        @JsonProperty("Items") List<ReceiptItemModel> items,
        @JsonProperty("Supplier") SupplierModel supplier) {

    /** Builder for {@link AcquiringCreateSubscriptionWithReceiptRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
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
                .paymentLinkId(this.paymentLinkId)
                .taxSystemCode(this.taxSystemCode)
                .client(this.client)
                .items(this.items)
                .supplier(this.supplier);
    }

    /** Builder for {@link AcquiringCreateSubscriptionWithReceiptRequestModel}. */
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
        private TaxSystemCodeInput taxSystemCode;
        private ReceiptClientModel client;
        private List<ReceiptItemModel> items;
        private SupplierModel supplier;

        /** Уникальный код клиента. Example: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /** Сумма платежа, которая будет списываться в указанный клиентом период. Example: "1234.00" */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Назначение платежа. Example: "Перевод за оказанные услуги" */
        public Builder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        /** URL адрес, куда будет переправлен клиент после оплаты услуги. Example: "https://example.com" */
        public Builder redirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
            return this;
        }

        /** URL адрес, куда будет переправлен клиент в случае неуспешной оплаты. Example:
        "https://example.com/fail" */
        public Builder failRedirectUrl(String failRedirectUrl) {
            this.failRedirectUrl = failRedirectUrl;
            return this;
        }

        /** Предложить покупателю сохранить карту. Example: true */
        public Builder saveCard(Boolean saveCard) {
            this.saveCard = saveCard;
            return this;
        }

        /** Идентификатор покупателя. Example: "fedac807-078d-45ac-a43b-5c01c57edbf8" */
        public Builder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        /** Идентификатор торговой точки в интернет-эквайринге. Example: "200000000001056" */
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

        /** Система налогообложения. Example: "osn" */
        public Builder taxSystemCode(TaxSystemCodeInput taxSystemCode) {
            this.taxSystemCode = taxSystemCode;
            return this;
        }

        /** Данные покупателя */
        public Builder client(ReceiptClientModel client) {
            this.client = client;
            return this;
        }

        /** Список товаров в заказе */
        public Builder items(List<ReceiptItemModel> items) {
            this.items = items;
            return this;
        }

        /** Данные поставщика */
        public Builder supplier(SupplierModel supplier) {
            this.supplier = supplier;
            return this;
        }

        public AcquiringCreateSubscriptionWithReceiptRequestModel build() {
            return new AcquiringCreateSubscriptionWithReceiptRequestModel(
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
                    this.paymentLinkId,
                    this.taxSystemCode,
                    this.client,
                    this.items,
                    this.supplier);
        }
    }
}
