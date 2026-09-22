package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 * AcquiringSubscriptionListItemModel
 *
 * @param customerCode Уникальный код клиента. Например: "300000092"
 * @param taxSystemCode Система налогообложения. Например: "osn" (необязательное)
 * @param paymentId Идентификатор платежа в процессинге или СБП. Например: "A22031016256670100000533E625FCB3"
 *        (необязательное)
 * @param transactionId Идентификатор транзакции в СБП. Используется для возврата при оплате по СБП. Например:
 *        "48232c9a-ce82-1593-3cb6-5c85a1ffef8f" (необязательное)
 * @param createdAt Дата и время создания операции. Используется стандарт ISO8601. Например:
 *        "2022-10-18T08:28:59+00:00"
 * @param redirectUrl URL адрес, куда будет переправлен клиент после оплаты услуги. Например:
 *        "https://example.com" (необязательное)
 * @param failRedirectUrl URL адрес, куда будет переправлен клиент в случае неуспешной оплаты. Например:
 *        "https://example.com/fail" (необязательное)
 * @param client Данные покупателя (необязательное)
 * @param items Список товаров в заказе
 * @param purpose Назначение платежа. Отсутствует, если при создании платежа назначение не было указано.
 *        Например: "Перевод за оказанные услуги" (необязательное)
 * @param amount Сумма платежа. Например: "1234.00"
 * @param status Статус платежа. Например: "CREATED"
 * @param operationId Идентификатор платежа. Например: "48232c9a-ce82-1593-3cb6-5c85a1ffef8f"
 * @param paymentLink Ссылка на оплату. Например:
 *        "https://merch.example.com/order/?uuid=16ea4c54-bf1d-4e6a-a1ef-53ad55666e43"
 * @param merchantId Идентификатор торговой точки в интернет-эквайринге. Например: "200000000001056"
 *        (необязательное)
 * @param consumerId Идентификатор покупателя. Например: "fedac807-078d-45ac-a43b-5c01c57edbf8" (необязательное)
 * @param options Опции подписки (необязательное)
 * @param supplier Данные поставщика (необязательное)
 * @param recurring Создание рекуррентной оплаты (необязательное)
 * @param paymentLinkId Уникальный номер заказа (необязательное)
 * @param cofToken Информация о карте плательщика (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringSubscriptionListItemModel(
        @JsonProperty("customerCode") String customerCode,
        @JsonProperty("taxSystemCode") TaxSystemCodeOutput taxSystemCode,
        @JsonProperty("paymentId") String paymentId,
        @JsonProperty("transactionId") String transactionId,
        @JsonProperty("createdAt") String createdAt,
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
        @JsonProperty("Options") AcquiringSubscriptionOutputOptions options,
        @JsonProperty("Supplier") SupplierModel supplier,
        @JsonProperty("recurring") Boolean recurring,
        @JsonProperty("paymentLinkId") String paymentLinkId,
        @JsonProperty("CofToken") CofTokenModel cofToken) {

    /** Строитель {@link AcquiringSubscriptionListItemModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .customerCode(this.customerCode)
                .taxSystemCode(this.taxSystemCode)
                .paymentId(this.paymentId)
                .transactionId(this.transactionId)
                .createdAt(this.createdAt)
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
                .options(this.options)
                .supplier(this.supplier)
                .recurring(this.recurring)
                .paymentLinkId(this.paymentLinkId)
                .cofToken(this.cofToken);
    }

    /** Строитель {@link AcquiringSubscriptionListItemModel}. */
    public static final class Builder {

        private String customerCode;
        private TaxSystemCodeOutput taxSystemCode;
        private String paymentId;
        private String transactionId;
        private String createdAt;
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
        private AcquiringSubscriptionOutputOptions options;
        private SupplierModel supplier;
        private Boolean recurring;
        private String paymentLinkId;
        private CofTokenModel cofToken;

        /** Уникальный код клиента. Например: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /** Система налогообложения. Например: "osn" */
        public Builder taxSystemCode(TaxSystemCodeOutput taxSystemCode) {
            this.taxSystemCode = taxSystemCode;
            return this;
        }

        /** Идентификатор платежа в процессинге или СБП. Например: "A22031016256670100000533E625FCB3" */
        public Builder paymentId(String paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        /** Идентификатор транзакции в СБП. Используется для возврата при оплате по СБП. Например:
        "48232c9a-ce82-1593-3cb6-5c85a1ffef8f" */
        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        /** Дата и время создания операции. Используется стандарт ISO8601. Например:
        "2022-10-18T08:28:59+00:00" */
        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
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
        Например: "Перевод за оказанные услуги" */
        public Builder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        /** Сумма платежа. Например: "1234.00" */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Статус платежа. Например: "CREATED" */
        public Builder status(AcquiringPaymentStatus status) {
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

        /** Идентификатор торговой точки в интернет-эквайринге. Например: "200000000001056" */
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        /** Идентификатор покупателя. Например: "fedac807-078d-45ac-a43b-5c01c57edbf8" */
        public Builder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        /** Опции подписки */
        public Builder options(AcquiringSubscriptionOutputOptions options) {
            this.options = options;
            return this;
        }

        /** Данные поставщика */
        public Builder supplier(SupplierModel supplier) {
            this.supplier = supplier;
            return this;
        }

        /** Создание рекуррентной оплаты */
        public Builder recurring(Boolean recurring) {
            this.recurring = recurring;
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

        public AcquiringSubscriptionListItemModel build() {
            return new AcquiringSubscriptionListItemModel(
                    this.customerCode,
                    this.taxSystemCode,
                    this.paymentId,
                    this.transactionId,
                    this.createdAt,
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
                    this.options,
                    this.supplier,
                    this.recurring,
                    this.paymentLinkId,
                    this.cofToken);
        }
    }
}
