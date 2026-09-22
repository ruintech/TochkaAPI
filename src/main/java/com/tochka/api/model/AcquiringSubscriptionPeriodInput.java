package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * AcquiringSubscriptionPeriodInput
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum AcquiringSubscriptionPeriodInput {

    DAY("Day"),
    MONTH("Month"),
    YEAR("Year");

    private final String value;

    AcquiringSubscriptionPeriodInput(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static AcquiringSubscriptionPeriodInput fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (AcquiringSubscriptionPeriodInput candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static AcquiringSubscriptionPeriodInput parse(String value) {
        AcquiringSubscriptionPeriodInput parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение AcquiringSubscriptionPeriodInput: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
