package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * ExternalBalanceTypeEnum
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum ExternalBalanceTypeEnum {

    CREDIT("Credit"),
    DEBIT("Debit");

    private final String value;

    ExternalBalanceTypeEnum(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static ExternalBalanceTypeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ExternalBalanceTypeEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static ExternalBalanceTypeEnum parse(String value) {
        ExternalBalanceTypeEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение ExternalBalanceTypeEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
