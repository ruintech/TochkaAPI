package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CofTokenModel
 *
 * @param tokenCardId Токен карты покупателя. Например: "208452" (необязательное)
 * @param cardType Тип платёжной системы. Например: "Mir" (необязательное)
 * @param maskedPan Маскированный номер карты. Например: "220445******0792" (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CofTokenModel(
        @JsonProperty("tokenCardId") String tokenCardId,
        @JsonProperty("cardType") String cardType,
        @JsonProperty("maskedPan") String maskedPan) {

    /** Строитель {@link CofTokenModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .tokenCardId(this.tokenCardId)
                .cardType(this.cardType)
                .maskedPan(this.maskedPan);
    }

    /** Строитель {@link CofTokenModel}. */
    public static final class Builder {

        private String tokenCardId;
        private String cardType;
        private String maskedPan;

        /** Токен карты покупателя. Например: "208452" */
        public Builder tokenCardId(String tokenCardId) {
            this.tokenCardId = tokenCardId;
            return this;
        }

        /** Тип платёжной системы. Например: "Mir" */
        public Builder cardType(String cardType) {
            this.cardType = cardType;
            return this;
        }

        /** Маскированный номер карты. Например: "220445******0792" */
        public Builder maskedPan(String maskedPan) {
            this.maskedPan = maskedPan;
            return this;
        }

        public CofTokenModel build() {
            return new CofTokenModel(this.tokenCardId, this.cardType, this.maskedPan);
        }
    }
}
