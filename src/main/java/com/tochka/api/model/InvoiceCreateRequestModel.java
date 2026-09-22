package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * InvoiceCreateRequestModel
 *
 * @param accountId Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
 * @param customerCode Уникальный код клиента. Например: "300000092"
 * @param secondSide Сторона заказчика/покупателя в сделке в документе
 * @param content Содержимое счета на оплату
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InvoiceCreateRequestModel(
        @JsonProperty("accountId") String accountId,
        @JsonProperty("customerCode") String customerCode,
        @JsonProperty("SecondSide") SecondSideModel secondSide,
        @JsonProperty("Content") ContentInvoice content) {

    /** Строитель {@link InvoiceCreateRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .accountId(this.accountId)
                .customerCode(this.customerCode)
                .secondSide(this.secondSide)
                .content(this.content);
    }

    /** Строитель {@link InvoiceCreateRequestModel}. */
    public static final class Builder {

        private String accountId;
        private String customerCode;
        private SecondSideModel secondSide;
        private ContentInvoice content;

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

        /** Содержимое счета на оплату */
        public Builder content(ContentInvoice content) {
            this.content = content;
            return this;
        }

        public InvoiceCreateRequestModel build() {
            return new InvoiceCreateRequestModel(this.accountId, this.customerCode, this.secondSide, this.content);
        }
    }
}
