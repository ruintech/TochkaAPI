package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ChangeCashboxQRCodeAccountRequestModel
 *
 * @param accountId Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChangeCashboxQRCodeAccountRequestModel(
        @JsonProperty("accountId") String accountId) {

    /** Строитель {@link ChangeCashboxQRCodeAccountRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .accountId(this.accountId);
    }

    /** Строитель {@link ChangeCashboxQRCodeAccountRequestModel}. */
    public static final class Builder {

        private String accountId;

        /** Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public ChangeCashboxQRCodeAccountRequestModel build() {
            return new ChangeCashboxQRCodeAccountRequestModel(this.accountId);
        }
    }
}
