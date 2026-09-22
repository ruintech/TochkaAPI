package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * TransactionAmountModel
 *
 * @param amount Сумма транзакции запроса в валюте счета. Например: 1234.56
 * @param amountNat Сумма транзакции по счету запроса в рублях по курсу ЦБ на дату транзакции. Например: 400.0
 *        (необязательное)
 * @param currency Валюта ведения счета. Используется стандарт ISO 4217. Например: "RUB"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransactionAmountModel(
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("amountNat") BigDecimal amountNat,
        @JsonProperty("currency") String currency) {

    /** Строитель {@link TransactionAmountModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .amount(this.amount)
                .amountNat(this.amountNat)
                .currency(this.currency);
    }

    /** Строитель {@link TransactionAmountModel}. */
    public static final class Builder {

        private BigDecimal amount;
        private BigDecimal amountNat;
        private String currency;

        /** Сумма транзакции запроса в валюте счета. Например: 1234.56 */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Сумма транзакции по счету запроса в рублях по курсу ЦБ на дату транзакции. Например: 400.0 */
        public Builder amountNat(BigDecimal amountNat) {
            this.amountNat = amountNat;
            return this;
        }

        /** Валюта ведения счета. Используется стандарт ISO 4217. Например: "RUB" */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public TransactionAmountModel build() {
            return new TransactionAmountModel(this.amount, this.amountNat, this.currency);
        }
    }
}
