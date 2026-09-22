package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 * AcquiringCreatePaymentOperationWithReceiptResponseModel
 *
 * @param purpose Назначение платежа. Например: "Футболка женская молочная"
 * @param status Статус операции. Например: "CREATED" (необязательное)
 * @param amount Сумма платежа. Например: "1234.00"
 * @param operationId Идентификатор платежа. Например: "48232c9a-ce82-1593-3cb6-5c85a1ffef8f"
 * @param paymentLink Ссылка на оплату. Например:
 *        "https://merch.example.com/order/?uuid=16ea4c54-bf1d-4e6a-a1ef-53ad55666e43"
 * @param consumerId Идентификатор покупателя. Например: "fedac807-078d-45ac-a43b-5c01c57edbf8" (необязательное)
 * @param merchantId Идентификатор торговой точки в интернет-эквайринге. Например: "200000000001056"
 *        (необязательное)
 * @param preAuthorization Создать платёж с двухэтапной оплатой (необязательное)
 * @param ttl Время жизни платёжной ссылки в минутах (необязательное)
 * @param paymentLinkId Уникальный номер заказа (необязательное)
 * @param paymentMode Способ оплаты. Например: ["sbp", "card", "tinkoff", "dolyame"]
 * @param customerCode Уникальный код клиента. Например: "300000092"
 * @param redirectUrl URL адрес, куда будет переправлен клиент после оплаты услуги. Например:
 *        "https://example.com" (необязательное)
 * @param failRedirectUrl URL адрес, куда будет переправлен клиент в случае неуспешной оплаты. Например:
 *        "https://example.com/fail" (необязательное)
 * @param taxSystemCode Система налогообложения. Например: "osn" (необязательное)
 * @param client Данные покупателя
 * @param items Список товаров в заказе
 * @param supplier Данные поставщика (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringCreatePaymentOperationWithReceiptResponseModel(
        @JsonProperty("purpose") String purpose,
        @JsonProperty("status") AcquiringPaymentStatus status,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("operationId") String operationId,
        @JsonProperty("paymentLink") String paymentLink,
        @JsonProperty("consumerId") String consumerId,
        @JsonProperty("merchantId") String merchantId,
        @JsonProperty("preAuthorization") Boolean preAuthorization,
        @JsonProperty("ttl") Integer ttl,
        @JsonProperty("paymentLinkId") String paymentLinkId,
        @JsonProperty("paymentMode") List<AcquiringPaymentMode> paymentMode,
        @JsonProperty("customerCode") String customerCode,
        @JsonProperty("redirectUrl") String redirectUrl,
        @JsonProperty("failRedirectUrl") String failRedirectUrl,
        @JsonProperty("taxSystemCode") TaxSystemCodeInput taxSystemCode,
        @JsonProperty("Client") ReceiptClientModel client,
        @JsonProperty("Items") List<ReceiptItemOutputModel> items,
        @JsonProperty("Supplier") SupplierModel supplier) {

    /** Строитель {@link AcquiringCreatePaymentOperationWithReceiptResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .purpose(this.purpose)
                .status(this.status)
                .amount(this.amount)
                .operationId(this.operationId)
                .paymentLink(this.paymentLink)
                .consumerId(this.consumerId)
                .merchantId(this.merchantId)
                .preAuthorization(this.preAuthorization)
                .ttl(this.ttl)
                .paymentLinkId(this.paymentLinkId)
                .paymentMode(this.paymentMode)
                .customerCode(this.customerCode)
                .redirectUrl(this.redirectUrl)
                .failRedirectUrl(this.failRedirectUrl)
                .taxSystemCode(this.taxSystemCode)
                .client(this.client)
                .items(this.items)
                .supplier(this.supplier);
    }

    /** Строитель {@link AcquiringCreatePaymentOperationWithReceiptResponseModel}. */
    public static final class Builder {

        private String purpose;
        private AcquiringPaymentStatus status;
        private BigDecimal amount;
        private String operationId;
        private String paymentLink;
        private String consumerId;
        private String merchantId;
        private Boolean preAuthorization;
        private Integer ttl;
        private String paymentLinkId;
        private List<AcquiringPaymentMode> paymentMode;
        private String customerCode;
        private String redirectUrl;
        private String failRedirectUrl;
        private TaxSystemCodeInput taxSystemCode;
        private ReceiptClientModel client;
        private List<ReceiptItemOutputModel> items;
        private SupplierModel supplier;

        /** Назначение платежа. Например: "Футболка женская молочная" */
        public Builder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        /** Статус операции. Например: "CREATED" */
        public Builder status(AcquiringPaymentStatus status) {
            this.status = status;
            return this;
        }

        /** Сумма платежа. Например: "1234.00" */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
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

        /** Идентификатор торговой точки в интернет-эквайринге. Например: "200000000001056" */
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        /** Создать платёж с двухэтапной оплатой */
        public Builder preAuthorization(Boolean preAuthorization) {
            this.preAuthorization = preAuthorization;
            return this;
        }

        /** Время жизни платёжной ссылки в минутах */
        public Builder ttl(Integer ttl) {
            this.ttl = ttl;
            return this;
        }

        /** Уникальный номер заказа */
        public Builder paymentLinkId(String paymentLinkId) {
            this.paymentLinkId = paymentLinkId;
            return this;
        }

        /** Способ оплаты. Например: ["sbp", "card", "tinkoff", "dolyame"] */
        public Builder paymentMode(List<AcquiringPaymentMode> paymentMode) {
            this.paymentMode = paymentMode;
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

        public AcquiringCreatePaymentOperationWithReceiptResponseModel build() {
            return new AcquiringCreatePaymentOperationWithReceiptResponseModel(
                    this.purpose,
                    this.status,
                    this.amount,
                    this.operationId,
                    this.paymentLink,
                    this.consumerId,
                    this.merchantId,
                    this.preAuthorization,
                    this.ttl,
                    this.paymentLinkId,
                    this.paymentMode,
                    this.customerCode,
                    this.redirectUrl,
                    this.failRedirectUrl,
                    this.taxSystemCode,
                    this.client,
                    this.items,
                    this.supplier);
        }
    }
}
