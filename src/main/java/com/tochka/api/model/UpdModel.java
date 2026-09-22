package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * UpdModel
 *
 * @param positions Список позиций
 * @param date Дата выставления счета, приведенная к часовому поясу Москвы. Если не передана, то текущая
 *        дата.. Example: "2010-10-29" (optional)
 * @param totalAmount Сумма всех позиций с НДС. Example: "1234.56"
 * @param totalNds Сумма НДС. Example: "1234.56" (optional)
 * @param function Функция документа. Example: "dop"
 * @param number Номер УПД. Example: "1"
 * @param basedOn Документ, на основании которого выставляется счёт. Example: "Основание платежа" (optional)
 * @param shipmentDocuments Реквизиты документа, подтверждающего отгрузку товаров, работ или услуг (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdModel(
        @JsonProperty("Positions") List<PositionModel> positions,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("totalAmount") BigDecimal totalAmount,
        @JsonProperty("totalNds") BigDecimal totalNds,
        @JsonProperty("function") UpdFunctionEnum function,
        @JsonProperty("number") String number,
        @JsonProperty("basedOn") String basedOn,
        @JsonProperty("shipmentDocuments") List<ShipmentDocumentModel> shipmentDocuments) {

    /** Builder for {@link UpdModel}. */
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
                .function(this.function)
                .number(this.number)
                .basedOn(this.basedOn)
                .shipmentDocuments(this.shipmentDocuments);
    }

    /** Builder for {@link UpdModel}. */
    public static final class Builder {

        private List<PositionModel> positions;
        private LocalDate date;
        private BigDecimal totalAmount;
        private BigDecimal totalNds;
        private UpdFunctionEnum function;
        private String number;
        private String basedOn;
        private List<ShipmentDocumentModel> shipmentDocuments;

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

        /** Функция документа. Example: "dop" */
        public Builder function(UpdFunctionEnum function) {
            this.function = function;
            return this;
        }

        /** Номер УПД. Example: "1" */
        public Builder number(String number) {
            this.number = number;
            return this;
        }

        /** Документ, на основании которого выставляется счёт. Example: "Основание платежа" */
        public Builder basedOn(String basedOn) {
            this.basedOn = basedOn;
            return this;
        }

        /** Реквизиты документа, подтверждающего отгрузку товаров, работ или услуг */
        public Builder shipmentDocuments(List<ShipmentDocumentModel> shipmentDocuments) {
            this.shipmentDocuments = shipmentDocuments;
            return this;
        }

        public UpdModel build() {
            return new UpdModel(
                    this.positions,
                    this.date,
                    this.totalAmount,
                    this.totalNds,
                    this.function,
                    this.number,
                    this.basedOn,
                    this.shipmentDocuments);
        }
    }
}
