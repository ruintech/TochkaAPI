package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * AccountListModel
 *
 * @param account Account
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AccountListModel(
        @JsonProperty("Account") List<AccountModel> account) {

    /** Builder for {@link AccountListModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .account(this.account);
    }

    /** Builder for {@link AccountListModel}. */
    public static final class Builder {

        private List<AccountModel> account;

        /** Account */
        public Builder account(List<AccountModel> account) {
            this.account = account;
            return this;
        }

        public AccountListModel build() {
            return new AccountListModel(this.account);
        }
    }
}
