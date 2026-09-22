package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * SalesModeEnum
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum SalesModeEnum {

    REMOTE("REMOTE"),
    FACE_TO_FACE("FACE_TO_FACE");

    private final String value;

    SalesModeEnum(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static SalesModeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SalesModeEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static SalesModeEnum parse(String value) {
        SalesModeEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение SalesModeEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
