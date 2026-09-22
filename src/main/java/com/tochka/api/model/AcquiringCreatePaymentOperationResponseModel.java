package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 * AcquiringCreatePaymentOperationResponseModel
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
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringCreatePaymentOperationResponseModel(
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
        @JsonProperty("paymentMode") List<AcquiringPaymentMode> paymentMode) {

    /** Строитель {@link AcquiringCreatePaymentOperationResponseModel}. */
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
                .paymentMode(this.paymentMode);
    }

    /** Строитель {@link AcquiringCreatePaymentOperationResponseModel}. */
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

        public AcquiringCreatePaymentOperationResponseModel build() {
            return new AcquiringCreatePaymentOperationResponseModel(
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
                    this.paymentMode);
        }
    }
}
