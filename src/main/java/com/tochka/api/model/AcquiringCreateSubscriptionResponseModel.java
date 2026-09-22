package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * AcquiringCreateSubscriptionResponseModel
 *
 * @param purpose Назначение платежа. Example: "Перевод за оказанные услуги"
 * @param amount Сумма платежа, которая будет списываться в указанный клиентом период. Example: "1234.00"
 * @param status Статус платежа. Example: "CREATED" (optional)
 * @param operationId Идентификатор платежа. Example: "48232c9a-ce82-1593-3cb6-5c85a1ffef8f"
 * @param paymentLink Ссылка на оплату. Example:
 *        "https://merch.example.com/order/?uuid=16ea4c54-bf1d-4e6a-a1ef-53ad55666e43"
 * @param consumerId Идентификатор покупателя. Example: "fedac807-078d-45ac-a43b-5c01c57edbf8" (optional)
 * @param recurring Рекуррентная подписка (optional)
 * @param options Опции подписки (optional)
 * @param paymentLinkId Уникальный номер заказа (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringCreateSubscriptionResponseModel(
        @JsonProperty("purpose") String purpose,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("status") AcquiringCreatedStatus status,
        @JsonProperty("operationId") String operationId,
        @JsonProperty("paymentLink") String paymentLink,
        @JsonProperty("consumerId") String consumerId,
        @JsonProperty("recurring") Boolean recurring,
        @JsonProperty("Options") AcquiringSubscriptionOutputOptions options,
        @JsonProperty("paymentLinkId") String paymentLinkId) {

    /** Builder for {@link AcquiringCreateSubscriptionResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
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
                .paymentLinkId(this.paymentLinkId);
    }

    /** Builder for {@link AcquiringCreateSubscriptionResponseModel}. */
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

        /** Назначение платежа. Example: "Перевод за оказанные услуги" */
        public Builder purpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        /** Сумма платежа, которая будет списываться в указанный клиентом период. Example: "1234.00" */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Статус платежа. Example: "CREATED" */
        public Builder status(AcquiringCreatedStatus status) {
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

        /** Идентификатор покупателя. Example: "fedac807-078d-45ac-a43b-5c01c57edbf8" */
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

        public AcquiringCreateSubscriptionResponseModel build() {
            return new AcquiringCreateSubscriptionResponseModel(
                    this.purpose,
                    this.amount,
                    this.status,
                    this.operationId,
                    this.paymentLink,
                    this.consumerId,
                    this.recurring,
                    this.options,
                    this.paymentLinkId);
        }
    }
}
