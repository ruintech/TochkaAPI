package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * PaymentItemModel
 *
 * @param purpose Назначение платежа. Например: "Футболка женская молочная"
 * @param status Статус операции. Например: "CREATED" (необязательное)
 * @param amount Сумма платежа. Например: "1234.00"
 * @param operationId Идентификатор платежа. Например: "48232c9a-ce82-1593-3cb6-5c85a1ffef8f"
 * @param paymentLink Ссылка на оплату. Например:
 *        "https://merch.example.com/order/?uuid=16ea4c54-bf1d-4e6a-a1ef-53ad55666e43"
 * @param time Дата и время создания операции. Используется стандарт ISO8601. Например:
 *        "2022-10-18T08:28:59+00:00"
 * @param number Номер платежа. Например: "123456"
 * @param commission Комиссия за зачисление платежа. Например: 18548.39
 * @param enrollmentAmount Сумма за вычетом комиссии. Например: 18548.39
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentItemModel(
        @JsonProperty("purpose") String purpose,
        @JsonProperty("status") AcquiringPaymentStatus status,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("operationId") String operationId,
        @JsonProperty("paymentLink") String paymentLink,
        @JsonProperty("time") String time,
        @JsonProperty("number") String number,
        @JsonProperty("commission") BigDecimal commission,
        @JsonProperty("enrollmentAmount") BigDecimal enrollmentAmount) {

    /** Строитель {@link PaymentItemModel}. */
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
                .time(this.time)
                .number(this.number)
                .commission(this.commission)
                .enrollmentAmount(this.enrollmentAmount);
    }

    /** Строитель {@link PaymentItemModel}. */
    public static final class Builder {

        private String purpose;
        private AcquiringPaymentStatus status;
        private BigDecimal amount;
        private String operationId;
        private String paymentLink;
        private String time;
        private String number;
        private BigDecimal commission;
        private BigDecimal enrollmentAmount;

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

        /** Дата и время создания операции. Используется стандарт ISO8601. Например:
        "2022-10-18T08:28:59+00:00" */
        public Builder time(String time) {
            this.time = time;
            return this;
        }

        /** Номер платежа. Например: "123456" */
        public Builder number(String number) {
            this.number = number;
            return this;
        }

        /** Комиссия за зачисление платежа. Например: 18548.39 */
        public Builder commission(BigDecimal commission) {
            this.commission = commission;
            return this;
        }

        /** Сумма за вычетом комиссии. Например: 18548.39 */
        public Builder enrollmentAmount(BigDecimal enrollmentAmount) {
            this.enrollmentAmount = enrollmentAmount;
            return this;
        }

        public PaymentItemModel build() {
            return new PaymentItemModel(
                    this.purpose,
                    this.status,
                    this.amount,
                    this.operationId,
                    this.paymentLink,
                    this.time,
                    this.number,
                    this.commission,
                    this.enrollmentAmount);
        }
    }
}
