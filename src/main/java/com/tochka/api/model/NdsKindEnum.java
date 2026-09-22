package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * NdsKindEnum
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum NdsKindEnum {

    NDS_0("nds_0"),
    NDS_5("nds_5"),
    NDS_7("nds_7"),
    NDS_10("nds_10"),
    NDS_22("nds_22"),
    WITHOUT_NDS("without_nds");

    private final String value;

    NdsKindEnum(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static NdsKindEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (NdsKindEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static NdsKindEnum parse(String value) {
        NdsKindEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение NdsKindEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
