package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * PositionModel
 *
 * @param positionName Название товара или услуги. Например: "Название товара"
 * @param unitCode Код единицы измерения. Например: "шт."
 * @param ndsKind Ставка НДС. Например: "nds_0"
 * @param price Цена единицы с НДС. Например: "1234.56"
 * @param quantity Количество. Например: "1234.567"
 * @param totalAmount Сумма позиции с НДС. Например: "1234.56"
 * @param totalNds Сумма НДС. Например: "1234.56" (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PositionModel(
        @JsonProperty("positionName") String positionName,
        @JsonProperty("unitCode") UnitCodeEnum unitCode,
        @JsonProperty("ndsKind") NdsKindEnum ndsKind,
        @JsonProperty("price") BigDecimal price,
        @JsonProperty("quantity") BigDecimal quantity,
        @JsonProperty("totalAmount") BigDecimal totalAmount,
        @JsonProperty("totalNds") BigDecimal totalNds) {

    /** Строитель {@link PositionModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .positionName(this.positionName)
                .unitCode(this.unitCode)
                .ndsKind(this.ndsKind)
                .price(this.price)
                .quantity(this.quantity)
                .totalAmount(this.totalAmount)
                .totalNds(this.totalNds);
    }

    /** Строитель {@link PositionModel}. */
    public static final class Builder {

        private String positionName;
        private UnitCodeEnum unitCode;
        private NdsKindEnum ndsKind;
        private BigDecimal price;
        private BigDecimal quantity;
        private BigDecimal totalAmount;
        private BigDecimal totalNds;

        /** Название товара или услуги. Например: "Название товара" */
        public Builder positionName(String positionName) {
            this.positionName = positionName;
            return this;
        }

        /** Код единицы измерения. Например: "шт." */
        public Builder unitCode(UnitCodeEnum unitCode) {
            this.unitCode = unitCode;
            return this;
        }

        /** Ставка НДС. Например: "nds_0" */
        public Builder ndsKind(NdsKindEnum ndsKind) {
            this.ndsKind = ndsKind;
            return this;
        }

        /** Цена единицы с НДС. Например: "1234.56" */
        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        /** Количество. Например: "1234.567" */
        public Builder quantity(BigDecimal quantity) {
            this.quantity = quantity;
            return this;
        }

        /** Сумма позиции с НДС. Например: "1234.56" */
        public Builder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        /** Сумма НДС. Например: "1234.56" */
        public Builder totalNds(BigDecimal totalNds) {
            this.totalNds = totalNds;
            return this;
        }

        public PositionModel build() {
            return new PositionModel(
                    this.positionName,
                    this.unitCode,
                    this.ndsKind,
                    this.price,
                    this.quantity,
                    this.totalAmount,
                    this.totalNds);
        }
    }
}
