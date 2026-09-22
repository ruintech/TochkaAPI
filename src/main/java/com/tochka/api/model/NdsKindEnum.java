package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * NdsKindEnum
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
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

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
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

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
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
