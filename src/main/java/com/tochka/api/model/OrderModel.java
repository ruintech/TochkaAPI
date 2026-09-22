package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * OrderModel
 *
 * @param orderId Идентификатор платежа
 * @param type Тип операции
 * @param amount Сумма операции
 * @param time Время операции
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderModel(
        @JsonProperty("orderId") String orderId,
        @JsonProperty("type") OrderType type,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("time") String time) {

    /** Строитель {@link OrderModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .orderId(this.orderId)
                .type(this.type)
                .amount(this.amount)
                .time(this.time);
    }

    /** Строитель {@link OrderModel}. */
    public static final class Builder {

        private String orderId;
        private OrderType type;
        private BigDecimal amount;
        private String time;

        /** Идентификатор платежа */
        public Builder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        /** Тип операции */
        public Builder type(OrderType type) {
            this.type = type;
            return this;
        }

        /** Сумма операции */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Время операции */
        public Builder time(String time) {
            this.time = time;
            return this;
        }

        public OrderModel build() {
            return new OrderModel(this.orderId, this.type, this.amount, this.time);
        }
    }
}
