package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * ReceiptItemModel
 *
 * @param vatType Ставка НДС (необязательное)
 * @param name Название товара
 * @param amount Цена за единицу товара. Например: "1234.00"
 * @param quantity Количество товара. Например: 1
 * @param paymentMethod Тип оплаты. Например: "full_payment" (необязательное)
 * @param paymentObject Признак предмета расчёта. Например: "service" (необязательное)
 * @param measure Единица измерения. По умолчанию - штуки. Например: "шт." (необязательное)
 * @param supplier Данные поставщика (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReceiptItemModel(
        @JsonProperty("vatType") VatType vatType,
        @JsonProperty("name") String name,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("quantity") BigDecimal quantity,
        @JsonProperty("paymentMethod") PaymentMethod paymentMethod,
        @JsonProperty("paymentObject") PaymentObject paymentObject,
        @JsonProperty("measure") Measure measure,
        @JsonProperty("Supplier") SupplierModel supplier) {

    /** Строитель {@link ReceiptItemModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .vatType(this.vatType)
                .name(this.name)
                .amount(this.amount)
                .quantity(this.quantity)
                .paymentMethod(this.paymentMethod)
                .paymentObject(this.paymentObject)
                .measure(this.measure)
                .supplier(this.supplier);
    }

    /** Строитель {@link ReceiptItemModel}. */
    public static final class Builder {

        private VatType vatType;
        private String name;
        private BigDecimal amount;
        private BigDecimal quantity;
        private PaymentMethod paymentMethod;
        private PaymentObject paymentObject;
        private Measure measure;
        private SupplierModel supplier;

        /** Ставка НДС */
        public Builder vatType(VatType vatType) {
            this.vatType = vatType;
            return this;
        }

        /** Название товара */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /** Цена за единицу товара. Например: "1234.00" */
        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /** Количество товара. Например: 1 */
        public Builder quantity(BigDecimal quantity) {
            this.quantity = quantity;
            return this;
        }

        /** Тип оплаты. Например: "full_payment" */
        public Builder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        /** Признак предмета расчёта. Например: "service" */
        public Builder paymentObject(PaymentObject paymentObject) {
            this.paymentObject = paymentObject;
            return this;
        }

        /** Единица измерения. По умолчанию - штуки. Например: "шт." */
        public Builder measure(Measure measure) {
            this.measure = measure;
            return this;
        }

        /** Данные поставщика */
        public Builder supplier(SupplierModel supplier) {
            this.supplier = supplier;
            return this;
        }

        public ReceiptItemModel build() {
            return new ReceiptItemModel(
                    this.vatType,
                    this.name,
                    this.amount,
                    this.quantity,
                    this.paymentMethod,
                    this.paymentObject,
                    this.measure,
                    this.supplier);
        }
    }
}
