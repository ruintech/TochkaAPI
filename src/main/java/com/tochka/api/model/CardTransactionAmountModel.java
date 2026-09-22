package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * CardTransactionAmountModel
 *
 * @param amount Amount. Сумма транзакции. Например: 1234.56
 * @param currency Currency. Валюта транзакции, используется ISO 4217. Например: "RUB"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CardTransactionAmountModel(
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("currency") String currency) {

    /** Строитель {@link CardTransactionAmountModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .amount(this.amount)
                .currency(this.currency);
    }

    /** Строитель {@link CardTransactionAmountModel}. */
    public static final class Builder {

        private BigDecimal amount;
        private String currency;

        /** Amount. Сумма транзакции. Например: 1234.56 */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Currency. Валюта транзакции, используется ISO 4217. Например: "RUB" */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public CardTransactionAmountModel build() {
            return new CardTransactionAmountModel(this.amount, this.currency);
        }
    }
}
