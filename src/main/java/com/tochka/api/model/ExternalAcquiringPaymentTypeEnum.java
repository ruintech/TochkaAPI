package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * ExternalAcquiringPaymentTypeEnum
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum ExternalAcquiringPaymentTypeEnum {

    SBP("sbp"),
    CARD("card"),
    TINKOFF("tinkoff"),
    DOLYAME("dolyame"),
    DIGITAL_RUBLE("digitalRuble");

    private final String value;

    ExternalAcquiringPaymentTypeEnum(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static ExternalAcquiringPaymentTypeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ExternalAcquiringPaymentTypeEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static ExternalAcquiringPaymentTypeEnum parse(String value) {
        ExternalAcquiringPaymentTypeEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение ExternalAcquiringPaymentTypeEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
