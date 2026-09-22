package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 * AcquiringGetPaymentOperationListItemModel
 *
 * @param customerCode Уникальный код клиента. Example: "300000092"
 * @param taxSystemCode Система налогообложения. Example: "osn" (optional)
 * @param paymentType Тип оплаты. Присутствует, если оплата произведена. Example: "card" (optional)
 * @param paymentId Идентификатор платежа в процессинге или СБП. Example: "A22031016256670100000533E625FCB3"
 *        (optional)
 * @param transactionId Идентификатор транзакции в СБП. Используется для возврата при оплате по СБП. Example:
 *        "48232c9a-ce82-1593-3cb6-5c85a1ffef8f" (optional)
 * @param createdAt Дата и время создания операции. Используется стандарт ISO8601. Example:
 *        "2022-10-18T08:28:59+00:00"
 * @param paymentMode Способ оплаты. Example: ["sbp", "card", "tinkoff", "dolyame"] (optional)
 * @param redirectUrl URL адрес, куда будет переправлен клиент после оплаты услуги. Example: "https://example.com"
 *        (optional)
 * @param failRedirectUrl URL адрес, куда будет переправлен клиент в случае неуспешной оплаты. Example:
 *        "https://example.com/fail" (optional)
 * @param client Данные покупателя (optional)
 * @param items Список товаров в заказе (optional)
 * @param purpose Назначение платежа. Отсутствует, если при создании платежа назначение не было указано.
 *        Example: "Перевод за оказанные услуги" (optional)
 * @param amount Сумма платежа. Example: "1234.00"
 * @param status Статус платежа. Example: "CREATED"
 * @param operationId Идентификатор платежа. Example: "48232c9a-ce82-1593-3cb6-5c85a1ffef8f"
 * @param paymentLink Ссылка на оплату. Example:
 *        "https://merch.example.com/order/?uuid=16ea4c54-bf1d-4e6a-a1ef-53ad55666e43"
 * @param merchantId Идентификатор торговой точки в интернет-эквайринге. Example: "200000000001056" (optional)
 * @param consumerId Идентификатор покупателя. Example: "fedac807-078d-45ac-a43b-5c01c57edbf8" (optional)
 * @param order Список операций, связанных с платежом.
 * @param supplier Данные поставщика (optional)
 * @param preAuthorization Создать платёж с двухэтапной оплатой (optional)
 * @param paidAt Дата и время оплаты (optional)
 * @param paymentLinkId Уникальный номер заказа (optional)
 * @param cofToken Информация о карте плательщика (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringGetPaymentOperationListItemModel(
        @JsonProperty("customerCode") String customerCode,
        @JsonProperty("taxSystemCode") TaxSystemCodeOutput taxSystemCode,
        @JsonProperty("paymentType") ExternalAcquiringPaymentTypeEnum paymentType,
        @JsonProperty("paymentId") String paymentId,
        @JsonProperty("transactionId") String transactionId,
        @JsonProperty("createdAt") String createdAt,
        @JsonProperty("paymentMode") List<AcquiringPaymentMode> paymentMode,
        @JsonProperty("redirectUrl") String redirectUrl,
        @JsonProperty("failRedirectUrl") String failRedirectUrl,
        @JsonProperty("Client") ReceiptClientModel client,
        @JsonProperty("Items") List<ReceiptItemResponseModel> items,
        @JsonProperty("purpose") String purpose,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("status") AcquiringPaymentStatus status,
        @JsonProperty("operationId") String operationId,
        @JsonProperty("paymentLink") String paymentLink,
        @JsonProperty("merchantId") String merchantId,
        @JsonProperty("consumerId") String consumerId,
        @JsonProperty("Order") List<OrderModel> order,
        @JsonProperty("Supplier") SupplierModel supplier,
        @JsonProperty("preAuthorization") Boolean preAuthorization,
        @JsonProperty("paidAt") String paidAt,
        @JsonProperty("paymentLinkId") String paymentLinkId,
        @JsonProperty("CofToken") CofTokenModel cofToken) {

    /** Builder for {@link AcquiringGetPaymentOperationListItemModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .customerCode(this.customerCode)
                .taxSystemCode(this.taxSystemCode)
                .paymentType(this.paymentType)
                .paymentId(this.paymentId)
                .transactionId(this.transactionId)
                .createdAt(this.createdAt)
                .paymentMode(this.paymentMode)
                .redirectUrl(this.redirectUrl)
                .failRedirectUrl(this.failRedirectUrl)
                .client(this.client)
                .items(this.items)
                .purpose(this.purpose)
                .amount(this.amount)
                .status(this.status)
                .operationId(this.operationId)
                .paymentLink(this.paymentLink)
                .merchantId(this.merchantId)
                .consumerId(this.consumerId)
                .order(this.order)
                .supplier(this.supplier)
                .preAuthorization(this.preAuthorization)
                .paidAt(this.paidAt)
                .paymentLinkId(this.paymentLinkId)
                .cofToken(this.cofToken);
    }

    /** Builder for {@link AcquiringGetPaymentOperationListItemModel}. */
    public static final class Builder {

        private String customerCode;
        private TaxSystemCodeOutput taxSystemCode;
        private ExternalAcquiringPaymentTypeEnum paymentType;
        private String paymentId;
        private String transactionId;
        private String createdAt;
        private List<AcquiringPaymentMode> paymentMode;
        private String redirectUrl;
        private String failRedirectUrl;
        private ReceiptClientModel client;
        private List<ReceiptItemResponseModel> items;
        private String purpose;
        private BigDecimal amount;
        private AcquiringPaymentStatus status;
        private String operationId;
        private String paymentLink;
        private String merchantId;
        private String consumerId;
        private List<OrderModel> order;
        private SupplierModel supplier;
        private Boolean preAuthorization;
        private String paidAt;
        private String paymentLinkId;
        private CofTokenModel cofToken;

        /** Уникальный код клиента. Example: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /** Система налогообложения. Example: "osn" */
        public Builder taxSystemCode(TaxSystemCodeOutput taxSystemCode) {
            this.taxSystemCode = taxSystemCode;
            return this;
        }

        /** Тип оплаты. Присутствует, если оплата произведена. Example: "card" */
        public Builder paymentType(ExternalAcquiringPaymentTypeEnum paymentType) {
            this.paymentType = paymentType;
            return this;
        }

        /** Идентификатор платежа в процессинге или СБП. Example: "A22031016256670100000533E625FCB3" */
        public Builder paymentId(String paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        /** Идентификатор транзакции в СБП. Используется для возврата при оплате по СБП. Example:
        "48232c9a-ce82-1593-3cb6-5c85a1ffef8f" */
        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        /** Дата и время создания операции. Используется стандарт ISO8601. Example:
        "2022-10-18T08:28:59+00:00" */
        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /** Способ оплаты. Example: ["sbp", "card", "tinkoff", "dolyame"] */
        public Builder paymentMode(List<AcquiringPaymentMode> paymentMode) {
            this.paymentMode = paymentMode;
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

        /** Данные покупателя */
        public Builder client(ReceiptClientModel client) {
            this.client = client;
            return this;
        }

        /** Список товаров в заказе */
        public Builder items(List<ReceiptItemResponseModel> items) {
            this.items = items;
            return this;
        }

        /** Назначение платежа. Отсутствует, если при создании платежа назначение не было указано.
        Example: "Перевод за оказанные услуги" */
        public Builder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        /** Сумма платежа. Example: "1234.00" */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Статус платежа. Example: "CREATED" */
        public Builder status(AcquiringPaymentStatus status) {
            this.status = status;
            return this;
        }

        /** Идентификатор платежа. Example: "48232c9a-ce82-1593-3cb6-5c85a1ffef8f" */
        public Builder operationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        /** Ссылка на оплату. Example:
        "https://merch.example.com/order/?uuid=16ea4c54-bf1d-4e6a-a1ef-53ad55666e43" */
        public Builder paymentLink(String paymentLink) {
            this.paymentLink = paymentLink;
            return this;
        }

        /** Идентификатор торговой точки в интернет-эквайринге. Example: "200000000001056" */
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        /** Идентификатор покупателя. Example: "fedac807-078d-45ac-a43b-5c01c57edbf8" */
        public Builder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        /** Список операций, связанных с платежом. */
        public Builder order(List<OrderModel> order) {
            this.order = order;
            return this;
        }

        /** Данные поставщика */
        public Builder supplier(SupplierModel supplier) {
            this.supplier = supplier;
            return this;
        }

        /** Создать платёж с двухэтапной оплатой */
        public Builder preAuthorization(Boolean preAuthorization) {
            this.preAuthorization = preAuthorization;
            return this;
        }

        /** Дата и время оплаты */
        public Builder paidAt(String paidAt) {
            this.paidAt = paidAt;
            return this;
        }

        /** Уникальный номер заказа */
        public Builder paymentLinkId(String paymentLinkId) {
            this.paymentLinkId = paymentLinkId;
            return this;
        }

        /** Информация о карте плательщика */
        public Builder cofToken(CofTokenModel cofToken) {
            this.cofToken = cofToken;
            return this;
        }

        public AcquiringGetPaymentOperationListItemModel build() {
            return new AcquiringGetPaymentOperationListItemModel(
                    this.customerCode,
                    this.taxSystemCode,
                    this.paymentType,
                    this.paymentId,
                    this.transactionId,
                    this.createdAt,
                    this.paymentMode,
                    this.redirectUrl,
                    this.failRedirectUrl,
                    this.client,
                    this.items,
                    this.purpose,
                    this.amount,
                    this.status,
                    this.operationId,
                    this.paymentLink,
                    this.merchantId,
                    this.consumerId,
                    this.order,
                    this.supplier,
                    this.preAuthorization,
                    this.paidAt,
                    this.paymentLinkId,
                    this.cofToken);
        }
    }
}
