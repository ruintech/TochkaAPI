package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * BalanceListModel
 *
 * @param balance Balance
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BalanceListModel(
        @JsonProperty("Balance") List<BalanceModel> balance) {

    /** Строитель {@link BalanceListModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .balance(this.balance);
    }

    /** Строитель {@link BalanceListModel}. */
    public static final class Builder {

        private List<BalanceModel> balance;

        /** Balance */
        public Builder balance(List<BalanceModel> balance) {
            this.balance = balance;
            return this;
        }

        public BalanceListModel build() {
            return new BalanceListModel(this.balance);
        }
    }
}
