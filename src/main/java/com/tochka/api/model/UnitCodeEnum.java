package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * UnitCodeEnum
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum UnitCodeEnum {

    SHT("шт."),
    TYS_SHT("тыс.шт."),
    KOMPL("компл."),
    PAR("пар."),
    USL_ED("усл.ед."),
    UPAK("упак."),
    USLUGA("услуга."),
    PACH("пач."),
    MIN("мин."),
    CH("ч."),
    SUT("сут."),
    G("г."),
    KG("кг."),
    L("л."),
    M("м."),
    M2("м2."),
    M3("м3."),
    KM("км."),
    GA("га."),
    K_VT("кВт."),
    K_VT_CH("кВт.ч.");

    private final String value;

    UnitCodeEnum(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static UnitCodeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (UnitCodeEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static UnitCodeEnum parse(String value) {
        UnitCodeEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение UnitCodeEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
