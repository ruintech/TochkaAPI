package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * BalanceAmountModel
 *
 * @param amount Сумма. Example: 1234.56
 * @param currency Валюта ведения счета. Используется стандарт ISO 4217. Example: "RUB"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BalanceAmountModel(
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("currency") String currency) {

    /** Builder for {@link BalanceAmountModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .amount(this.amount)
                .currency(this.currency);
    }

    /** Builder for {@link BalanceAmountModel}. */
    public static final class Builder {

        private BigDecimal amount;
        private String currency;

        /** Сумма. Example: 1234.56 */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Валюта ведения счета. Используется стандарт ISO 4217. Example: "RUB" */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public BalanceAmountModel build() {
            return new BalanceAmountModel(this.amount, this.currency);
        }
    }
}
