package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * AcquiringSubscriptionStatus
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum AcquiringSubscriptionStatus {

    ACTIVE("Active"),
    CANCELLED("Cancelled"),
    COMPLETED("Completed"),
    EXPIRED("Expired"),
    FAILED("Failed"),
    PAST_DUE("PastDue"),
    PREPARING("Preparing"),
    REFUSED("Refused"),
    REJECTED("Rejected"),
    SUSPENDED("Suspended"),
    TRIAL("Trial");

    private final String value;

    AcquiringSubscriptionStatus(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static AcquiringSubscriptionStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (AcquiringSubscriptionStatus candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static AcquiringSubscriptionStatus parse(String value) {
        AcquiringSubscriptionStatus parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение AcquiringSubscriptionStatus: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
