package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * BalanceAmountModel
 *
 * @param amount Сумма. Например: 1234.56
 * @param currency Валюта ведения счета. Используется стандарт ISO 4217. Например: "RUB"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BalanceAmountModel(
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("currency") String currency) {

    /** Строитель {@link BalanceAmountModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .amount(this.amount)
                .currency(this.currency);
    }

    /** Строитель {@link BalanceAmountModel}. */
    public static final class Builder {

        private BigDecimal amount;
        private String currency;

        /** Сумма. Например: 1234.56 */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Валюта ведения счета. Используется стандарт ISO 4217. Например: "RUB" */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public BalanceAmountModel build() {
            return new BalanceAmountModel(this.amount, this.currency);
        }
    }
}
