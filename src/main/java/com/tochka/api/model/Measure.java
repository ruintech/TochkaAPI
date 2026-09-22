package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Measure
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
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

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
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

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
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
