package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * CardTransactionListModel
 *
 * @param transactions Transactions
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CardTransactionListModel(
        @JsonProperty("Transactions") List<CardTransactionModel> transactions) {

    /** Builder for {@link CardTransactionListModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .transactions(this.transactions);
    }

    /** Builder for {@link CardTransactionListModel}. */
    public static final class Builder {

        private List<CardTransactionModel> transactions;

        /** Transactions */
        public Builder transactions(List<CardTransactionModel> transactions) {
            this.transactions = transactions;
            return this;
        }

        public CardTransactionListModel build() {
            return new CardTransactionListModel(this.transactions);
        }
    }
}
