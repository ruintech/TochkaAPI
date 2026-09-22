package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * AcquiringPaymentOperationRefundModel
 *
 * @param isRefund Оформлен ли возврат. Например: true
 * @param operationId Идентификатор платежа. Например: "48232c9a-ce82-1593-3cb6-5c85a1ffef8f"
 * @param amount Сумма платежа. Например: "1234.00"
 * @param date Дата запроса на возврат. Например: "2025-04-11"
 * @param orderId Идентификатор операции возрата. Например: 1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringPaymentOperationRefundModel(
        @JsonProperty("isRefund") Boolean isRefund,
        @JsonProperty("operationId") String operationId,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("orderId") String orderId) {

    /** Строитель {@link AcquiringPaymentOperationRefundModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .isRefund(this.isRefund)
                .operationId(this.operationId)
                .amount(this.amount)
                .date(this.date)
                .orderId(this.orderId);
    }

    /** Строитель {@link AcquiringPaymentOperationRefundModel}. */
    public static final class Builder {

        private Boolean isRefund;
        private String operationId;
        private BigDecimal amount;
        private LocalDate date;
        private String orderId;

        /** Оформлен ли возврат. Например: true */
        public Builder isRefund(Boolean isRefund) {
            this.isRefund = isRefund;
            return this;
        }

        /** Идентификатор платежа. Например: "48232c9a-ce82-1593-3cb6-5c85a1ffef8f" */
        public Builder operationId(String operationId) {
            this.operationId = operationId;
            return this;
        }

        /** Сумма платежа. Например: "1234.00" */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Дата запроса на возврат. Например: "2025-04-11" */
        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        /** Идентификатор операции возрата. Например: 1 */
        public Builder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public AcquiringPaymentOperationRefundModel build() {
            return new AcquiringPaymentOperationRefundModel(this.isRefund, this.operationId, this.amount, this.date, this.orderId);
        }
    }
}
