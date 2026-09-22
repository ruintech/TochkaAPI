package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CofTokenModel
 *
 * @param tokenCardId Токен карты покупателя. Example: "208452" (optional)
 * @param cardType Тип платёжной системы. Example: "Mir" (optional)
 * @param maskedPan Маскированный номер карты. Example: "220445******0792" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CofTokenModel(
        @JsonProperty("tokenCardId") String tokenCardId,
        @JsonProperty("cardType") String cardType,
        @JsonProperty("maskedPan") String maskedPan) {

    /** Builder for {@link CofTokenModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .tokenCardId(this.tokenCardId)
                .cardType(this.cardType)
                .maskedPan(this.maskedPan);
    }

    /** Builder for {@link CofTokenModel}. */
    public static final class Builder {

        private String tokenCardId;
        private String cardType;
        private String maskedPan;

        /** Токен карты покупателя. Example: "208452" */
        public Builder tokenCardId(String tokenCardId) {
            this.tokenCardId = tokenCardId;
            return this;
        }

        /** Тип платёжной системы. Example: "Mir" */
        public Builder cardType(String cardType) {
            this.cardType = cardType;
            return this;
        }

        /** Маскированный номер карты. Example: "220445******0792" */
        public Builder maskedPan(String maskedPan) {
            this.maskedPan = maskedPan;
            return this;
        }

        public CofTokenModel build() {
            return new CofTokenModel(this.tokenCardId, this.cardType, this.maskedPan);
        }
    }
}
