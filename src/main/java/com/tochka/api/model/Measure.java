package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Measure
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum Measure {

    G("г."),
    KG("кг."),
    T("т."),
    SM("см."),
    DM("дм."),
    M("м."),
    SM2("см2."),
    DM2("дм2."),
    M2("м2."),
    ML("мл."),
    L("л."),
    M3("м3"),
    K_VT_CH("кВт.ч."),
    GKAL("Гкал."),
    DN("дн."),
    CH("ч."),
    MIN("мин."),
    SEK("сек."),
    KB("Кб."),
    MB("Мб."),
    GB("Гб."),
    TB("Тб."),
    SHT("шт.");

    private final String value;

    Measure(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static Measure fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (Measure candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static Measure parse(String value) {
        Measure parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение Measure: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
