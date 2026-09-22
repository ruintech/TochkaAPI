package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * AcquiringSubscriptionPeriodOutput
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum AcquiringSubscriptionPeriodOutput {

    DAY("Day"),
    MONTH("Month"),
    YEAR("Year"),
    QUARTER("Quarter");

    private final String value;

    AcquiringSubscriptionPeriodOutput(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static AcquiringSubscriptionPeriodOutput fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (AcquiringSubscriptionPeriodOutput candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static AcquiringSubscriptionPeriodOutput parse(String value) {
        AcquiringSubscriptionPeriodOutput parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение AcquiringSubscriptionPeriodOutput: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
