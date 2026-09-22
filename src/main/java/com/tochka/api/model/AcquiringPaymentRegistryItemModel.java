package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 * AcquiringPaymentRegistryItemModel
 *
 * @param paymentType Тип оплаты. Например: "card"
 * @param totalAmount Сумма всех позиций из этого блока. Например: 18548.39
 * @param paymentId Уникальный идентификатор платежа. Например: "A22031016256670100000533E625FCB3"
 *        (необязательное)
 * @param payments Список товаров в заказе
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringPaymentRegistryItemModel(
        @JsonProperty("paymentType") ExternalAcquiringPaymentTypeEnum paymentType,
        @JsonProperty("totalAmount") BigDecimal totalAmount,
        @JsonProperty("paymentId") String paymentId,
        @JsonProperty("payments") List<PaymentItemModel> payments) {

    /** Строитель {@link AcquiringPaymentRegistryItemModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .paymentType(this.paymentType)
                .totalAmount(this.totalAmount)
                .paymentId(this.paymentId)
                .payments(this.payments);
    }

    /** Строитель {@link AcquiringPaymentRegistryItemModel}. */
    public static final class Builder {

        private ExternalAcquiringPaymentTypeEnum paymentType;
        private BigDecimal totalAmount;
        private String paymentId;
        private List<PaymentItemModel> payments;

        /** Тип оплаты. Например: "card" */
        public Builder paymentType(ExternalAcquiringPaymentTypeEnum paymentType) {
            this.paymentType = paymentType;
            return this;
        }

        /** Сумма всех позиций из этого блока. Например: 18548.39 */
        public Builder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        /** Уникальный идентификатор платежа. Например: "A22031016256670100000533E625FCB3" */
        public Builder paymentId(String paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        /** Список товаров в заказе */
        public Builder payments(List<PaymentItemModel> payments) {
            this.payments = payments;
            return this;
        }

        public AcquiringPaymentRegistryItemModel build() {
            return new AcquiringPaymentRegistryItemModel(this.paymentType, this.totalAmount, this.paymentId, this.payments);
        }
    }
}
