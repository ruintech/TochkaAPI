package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * AcquiringRetailerStatus
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum AcquiringRetailerStatus {

    NEW("NEW"),
    ADDRESS_DADATA("ADDRESS_DADATA"),
    OPEN_ACCOUNT("OPEN_ACCOUNT"),
    TWPG_SENDED("TWPG_SENDED"),
    RETAILER_CREATED("RETAILER_CREATED"),
    TERMINAL_CREATED("TERMINAL_CREATED"),
    FILE_SENT("FILE_SENT"),
    REG("REG"),
    CLOSE("CLOSE");

    private final String value;

    AcquiringRetailerStatus(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static AcquiringRetailerStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (AcquiringRetailerStatus candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static AcquiringRetailerStatus parse(String value) {
        AcquiringRetailerStatus parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение AcquiringRetailerStatus: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
