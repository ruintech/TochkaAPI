package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * AccountListResponse
 *
 * @param accountList Accountlist
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AccountListResponse(
        @JsonProperty("AccountList") List<Account> accountList) {

    /** Строитель {@link AccountListResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .accountList(this.accountList);
    }

    /** Строитель {@link AccountListResponse}. */
    public static final class Builder {

        private List<Account> accountList;

        /** Accountlist */
        public Builder accountList(List<Account> accountList) {
            this.accountList = accountList;
            return this;
        }

        public AccountListResponse build() {
            return new AccountListResponse(this.accountList);
        }
    }
}
