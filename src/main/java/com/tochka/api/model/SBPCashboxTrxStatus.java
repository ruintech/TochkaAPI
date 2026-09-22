package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * * ACWP - Операция завершена успешно * RJCT - Операция отклонена * RCVD - Операция в обработке *
 * NTST - Операции по QR-коду не существует
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum SBPCashboxTrxStatus {

    ACWP("ACWP"),
    RJCT("RJCT"),
    RCVD("RCVD"),
    NTST("NTST");

    private final String value;

    SBPCashboxTrxStatus(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static SBPCashboxTrxStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SBPCashboxTrxStatus candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static SBPCashboxTrxStatus parse(String value) {
        SBPCashboxTrxStatus parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение SBPCashboxTrxStatus: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
