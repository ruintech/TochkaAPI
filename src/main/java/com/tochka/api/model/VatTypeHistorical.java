package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * VatTypeHistorical
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum VatTypeHistorical {

    NONE("none"),
    VAT0("vat0"),
    VAT5("vat5"),
    VAT7("vat7"),
    VAT10("vat10"),
    VAT20("vat20"),
    VAT22("vat22"),
    VAT105("vat105"),
    VAT107("vat107"),
    VAT110("vat110"),
    VAT120("vat120"),
    VAT122("vat122");

    private final String value;

    VatTypeHistorical(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static VatTypeHistorical fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (VatTypeHistorical candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static VatTypeHistorical parse(String value) {
        VatTypeHistorical parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение VatTypeHistorical: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
