package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * VatType
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum VatType {

    NONE("none"),
    VAT0("vat0"),
    VAT5("vat5"),
    VAT7("vat7"),
    VAT10("vat10"),
    VAT22("vat22"),
    VAT105("vat105"),
    VAT107("vat107"),
    VAT110("vat110"),
    VAT122("vat122");

    private final String value;

    VatType(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static VatType fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (VatType candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static VatType parse(String value) {
        VatType parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение VatType: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
