package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * SBPCashboxQrCodeStatus
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum SBPCashboxQrCodeStatus {

    INACTIVATED("INACTIVATED"),
    WAITING_PAYMENT("WAITING_PAYMENT"),
    IN_PROGRESS("IN_PROGRESS");

    private final String value;

    SBPCashboxQrCodeStatus(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static SBPCashboxQrCodeStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SBPCashboxQrCodeStatus candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static SBPCashboxQrCodeStatus parse(String value) {
        SBPCashboxQrCodeStatus parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение SBPCashboxQrCodeStatus: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
