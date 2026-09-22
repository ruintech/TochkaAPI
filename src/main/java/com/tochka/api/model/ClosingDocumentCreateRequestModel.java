package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ClosingDocumentCreateRequestModel
 *
 * @param accountId Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
 * @param customerCode Уникальный код клиента. Например: "300000092"
 * @param secondSide Сторона заказчика/покупателя в сделке в документе
 * @param documentId ID родительского документа. Например: "1cf95c4f-e794-4407-bac4-0829f19bd2be"
 *        (необязательное)
 * @param content Содержимое закрывающего документа
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClosingDocumentCreateRequestModel(
        @JsonProperty("accountId") String accountId,
        @JsonProperty("customerCode") String customerCode,
        @JsonProperty("SecondSide") SecondSideModel secondSide,
        @JsonProperty("documentId") String documentId,
        @JsonProperty("Content") com.fasterxml.jackson.databind.JsonNode content) {

    /** Строитель {@link ClosingDocumentCreateRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .accountId(this.accountId)
                .customerCode(this.customerCode)
                .secondSide(this.secondSide)
                .documentId(this.documentId)
                .content(this.content);
    }

    /** Строитель {@link ClosingDocumentCreateRequestModel}. */
    public static final class Builder {

        private String accountId;
        private String customerCode;
        private SecondSideModel secondSide;
        private String documentId;
        private com.fasterxml.jackson.databind.JsonNode content;

        /** Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Уникальный код клиента. Например: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /** Сторона заказчика/покупателя в сделке в документе */
        public Builder secondSide(SecondSideModel secondSide) {
            this.secondSide = secondSide;
            return this;
        }

        /** ID родительского документа. Например: "1cf95c4f-e794-4407-bac4-0829f19bd2be" */
        public Builder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        /** Содержимое закрывающего документа */
        public Builder content(com.fasterxml.jackson.databind.JsonNode content) {
            this.content = content;
            return this;
        }

        public ClosingDocumentCreateRequestModel build() {
            return new ClosingDocumentCreateRequestModel(this.accountId, this.customerCode, this.secondSide, this.documentId, this.content);
        }
    }
}
