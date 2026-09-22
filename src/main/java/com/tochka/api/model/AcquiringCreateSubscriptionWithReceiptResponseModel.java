package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 * AcquiringCreateSubscriptionWithReceiptResponseModel
 *
 * @param purpose Назначение платежа. Например: "Перевод за оказанные услуги"
 * @param amount Сумма платежа, которая будет списываться в указанный клиентом период. Например: "1234.00"
 * @param status Статус платежа. Например: "CREATED" (необязательное)
 * @param operationId Идентификатор платежа. Например: "48232c9a-ce82-1593-3cb6-5c85a1ffef8f"
 * @param paymentLink Ссылка на оплату. Например:
 *        "https://merch.example.com/order/?uuid=16ea4c54-bf1d-4e6a-a1ef-53ad55666e43"
 * @param consumerId Идентификатор покупателя. Например: "fedac807-078d-45ac-a43b-5c01c57edbf8" (необязательное)
 * @param recurring Рекуррентная подписка (необязательное)
 * @param options Опции подписки (необязательное)
 * @param paymentLinkId Уникальный номер заказа (необязательное)
 * @param customerCode Уникальный код клиента. Например: "300000092"
 * @param redirectUrl URL адрес, куда будет переправлен клиент после оплаты услуги. Например:
 *        "https://example.com" (необязательное)
 * @param failRedirectUrl URL адрес, куда будет переправлен клиент в случае неуспешной оплаты. Например:
 *        "https://example.com/fail" (необязательное)
 * @param merchantId Идентификатор торговой точки в интернет-эквайринге. Например: "200000000001056"
 *        (необязательное)
 * @param taxSystemCode Система налогообложения. Например: "osn" (необязательное)
 * @param client Данные покупателя
 * @param items Список товаров в заказе
 * @param supplier Данные поставщика (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringCreateSubscriptionWithReceiptResponseModel(
        @JsonProperty("purpose") String purpose,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("status") AcquiringCreatedStatus status,
        @JsonProperty("operationId") String operationId,
        @JsonProperty("paymentLink") String paymentLink,
        @JsonProperty("consumerId") String consumerId,
        @JsonProperty("recurring") Boolean recurring,
        @JsonProperty("Options") AcquiringSubscriptionOutputOptions options,
        @JsonProperty("paymentLinkId") String paymentLinkId,
        @JsonProperty("customerCode") String customerCode,
        @JsonProperty("redirectUrl") String redirectUrl,
        @JsonProperty("failRedirectUrl") String failRedirectUrl,
        @JsonProperty("merchantId") String merchantId,
        @JsonProperty("taxSystemCode") TaxSystemCodeInput taxSystemCode,
        @JsonProperty("Client") ReceiptClientModel client,
        @JsonProperty("Items") List<ReceiptItemOutputModel> items,
        @JsonProperty("Supplier") SupplierModel supplier) {

    /** Строитель {@link AcquiringCreateSubscriptionWithReceiptResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .purpose(this.purpose)
                .amount(this.amount)
                .status(this.status)
                .operationId(this.operationId)
                .paymentLink(this.paymentLink)
                .consumerId(this.consumerId)
                .recurring(this.recurring)
                .options(this.options)
                .paymentLinkId(this.paymentLinkId)
                .customerCode(this.customerCode)
                .redirectUrl(this.redirectUrl)
                .failRedirectUrl(this.failRedirectUrl)
                .merchantId(this.merchantId)
                .taxSystemCode(this.taxSystemCode)
                .client(this.client)
                .items(this.items)
                .supplier(this.supplier);
    }

    /** Строитель {@link AcquiringCreateSubscriptionWithReceiptResponseModel}. */
    public static final class Builder {

        private String purpose;
        private BigDecimal amount;
        private AcquiringCreatedStatus status;
        private String operationId;
        private String paymentLink;
        private String consumerId;
        private Boolean recurring;
        private AcquiringSubscriptionOutputOptions options;
        private String paymentLinkId;
        private String customerCode;
        private String redirectUrl;
        private String failRedirectUrl;
        private String merchantId;
        private TaxSystemCodeInput taxSystemCode;
        private ReceiptClientModel client;
        private List<ReceiptItemOutputModel> items;
        private SupplierModel supplier;

        /** Назначение платежа. Например: "Перевод за оказанные услуги" */
        public Builder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        /** Сумма платежа, которая будет списываться в указанный клиентом период. Например: "1234.00" */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Статус платежа. Например: "CREATED" */
        public Builder status(AcquiringCreatedStatus status) {
            this.status = status;
            return this;
        }

        /** Идентификатор платежа. Например: "48232c9a-ce82-1593-3cb6-5c85a1ffef8f" */
        public Builder operationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        /** Ссылка на оплату. Например:
        "https://merch.example.com/order/?uuid=16ea4c54-bf1d-4e6a-a1ef-53ad55666e43" */
        public Builder paymentLink(String paymentLink) {
            this.paymentLink = paymentLink;
            return this;
        }

        /** Идентификатор покупателя. Например: "fedac807-078d-45ac-a43b-5c01c57edbf8" */
        public Builder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        /** Рекуррентная подписка */
        public Builder recurring(Boolean recurring) {
            this.recurring = recurring;
            return this;
        }

        /** Опции подписки */
        public Builder options(AcquiringSubscriptionOutputOptions options) {
            this.options = options;
            return this;
        }

        /** Уникальный номер заказа */
        public Builder paymentLinkId(String paymentLinkId) {
            this.paymentLinkId = paymentLinkId;
            return this;
        }

        /** Уникальный код клиента. Например: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
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

        /** Идентификатор торговой точки в интернет-эквайринге. Например: "200000000001056" */
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        /** Система налогообложения. Например: "osn" */
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
        public Builder items(List<ReceiptItemOutputModel> items) {
            this.items = items;
            return this;
        }

        /** Данные поставщика */
        public Builder supplier(SupplierModel supplier) {
            this.supplier = supplier;
            return this;
        }

        public AcquiringCreateSubscriptionWithReceiptResponseModel build() {
            return new AcquiringCreateSubscriptionWithReceiptResponseModel(
                    this.purpose,
                    this.amount,
                    this.status,
                    this.operationId,
                    this.paymentLink,
                    this.consumerId,
                    this.recurring,
                    this.options,
                    this.paymentLinkId,
                    this.customerCode,
                    this.redirectUrl,
                    this.failRedirectUrl,
                    this.merchantId,
                    this.taxSystemCode,
                    this.client,
                    this.items,
                    this.supplier);
        }
    }
}
