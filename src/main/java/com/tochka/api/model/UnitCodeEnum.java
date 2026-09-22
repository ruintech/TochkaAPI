package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * UnitCodeEnum
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
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

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
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

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
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
