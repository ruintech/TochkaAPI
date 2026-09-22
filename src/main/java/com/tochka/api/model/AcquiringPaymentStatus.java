package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * AcquiringPaymentStatus
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum AcquiringPaymentStatus {

    CREATED("CREATED"),
    APPROVED("APPROVED"),
    ON_REFUND("ON-REFUND"),
    REFUNDED("REFUNDED"),
    EXPIRED("EXPIRED"),
    REFUNDED_PARTIALLY("REFUNDED_PARTIALLY"),
    AUTHORIZED("AUTHORIZED"),
    WAIT_FULL_PAYMENT("WAIT_FULL_PAYMENT");

    private final String value;

    AcquiringPaymentStatus(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static AcquiringPaymentStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (AcquiringPaymentStatus candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static AcquiringPaymentStatus parse(String value) {
        AcquiringPaymentStatus parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение AcquiringPaymentStatus: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
