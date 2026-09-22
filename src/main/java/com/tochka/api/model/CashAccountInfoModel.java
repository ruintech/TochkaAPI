package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CashAccountInfoModel
 *
 * @param schemeName Название схемы. Example: "RU.CBR.PAN"
 * @param identification Идентификатор счета(может отсутствовать в валютном платеже). Example: "60000000000000000001"
 *        (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CashAccountInfoModel(
        @JsonProperty("schemeName") AccountIdentificationEnum schemeName,
        @JsonProperty("identification") String identification) {

    /** Builder for {@link CashAccountInfoModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .schemeName(this.schemeName)
                .identification(this.identification);
    }

    /** Builder for {@link CashAccountInfoModel}. */
    public static final class Builder {

        private AccountIdentificationEnum schemeName;
        private String identification;

        /** Название схемы. Example: "RU.CBR.PAN" */
        public Builder schemeName(AccountIdentificationEnum schemeName) {
            this.schemeName = schemeName;
            return this;
        }

        /** Идентификатор счета(может отсутствовать в валютном платеже). Example: "60000000000000000001" */
        public Builder identification(String identification) {
            this.identification = identification;
            return this;
        }

        public CashAccountInfoModel build() {
            return new CashAccountInfoModel(this.schemeName, this.identification);
        }
    }
}
