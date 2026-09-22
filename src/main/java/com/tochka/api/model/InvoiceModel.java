package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * InvoiceModel
 *
 * @param positions Список позиций
 * @param date Дата выставления счета, приведенная к часовому поясу Москвы. Если не передана, то текущая
 *        дата.. Example: "2010-10-29" (optional)
 * @param totalAmount Сумма всех позиций с НДС. Example: "1234.56"
 * @param totalNds Сумма НДС. Example: "1234.56" (optional)
 * @param number Номер выставляемого счёта. Example: "1"
 * @param basedOn Документ, на основании которого выставляется счёт. Example: "Основание платежа" (optional)
 * @param comment Комментарий. Example: "Комментарий к платежу" (optional)
 * @param paymentExpiryDate Срок оплаты в виде даты, приведенной к часовому поясу Москвы. Example: "2020-01-20"
 *        (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InvoiceModel(
        @JsonProperty("Positions") List<PositionModel> positions,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("totalAmount") BigDecimal totalAmount,
        @JsonProperty("totalNds") BigDecimal totalNds,
        @JsonProperty("number") String number,
        @JsonProperty("basedOn") String basedOn,
        @JsonProperty("comment") String comment,
        @JsonProperty("paymentExpiryDate") LocalDate paymentExpiryDate) {

    /** Builder for {@link InvoiceModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .positions(this.positions)
                .date(this.date)
                .totalAmount(this.totalAmount)
                .totalNds(this.totalNds)
                .number(this.number)
                .basedOn(this.basedOn)
                .comment(this.comment)
                .paymentExpiryDate(this.paymentExpiryDate);
    }

    /** Builder for {@link InvoiceModel}. */
    public static final class Builder {

        private List<PositionModel> positions;
        private LocalDate date;
        private BigDecimal totalAmount;
        private BigDecimal totalNds;
        private String number;
        private String basedOn;
        private String comment;
        private LocalDate paymentExpiryDate;

        /** Список позиций */
        public Builder positions(List<PositionModel> positions) {
            this.positions = positions;
            return this;
        }

        /** Дата выставления счета, приведенная к часовому поясу Москвы. Если не передана, то текущая
        дата.. Example: "2010-10-29" */
        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        /** Сумма всех позиций с НДС. Example: "1234.56" */
        public Builder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        /** Сумма НДС. Example: "1234.56" */
        public Builder totalNds(BigDecimal totalNds) {
            this.totalNds = totalNds;
            return this;
        }

        /** Номер выставляемого счёта. Example: "1" */
        public Builder number(String number) {
            this.number = number;
            return this;
        }

        /** Документ, на основании которого выставляется счёт. Example: "Основание платежа" */
        public Builder basedOn(String basedOn) {
            this.basedOn = basedOn;
            return this;
        }

        /** Комментарий. Example: "Комментарий к платежу" */
        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        /** Срок оплаты в виде даты, приведенной к часовому поясу Москвы. Example: "2020-01-20" */
        public Builder paymentExpiryDate(LocalDate paymentExpiryDate) {
            this.paymentExpiryDate = paymentExpiryDate;
            return this;
        }

        public InvoiceModel build() {
            return new InvoiceModel(
                    this.positions,
                    this.date,
                    this.totalAmount,
                    this.totalNds,
                    this.number,
                    this.basedOn,
                    this.comment,
                    this.paymentExpiryDate);
        }
    }
}
