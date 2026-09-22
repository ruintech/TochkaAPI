package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * SBPPaymentStatus
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum SBPPaymentStatus {

    WAITING_FOR_CLIENT_CONFIRM("WaitingForClientConfirm"),
    INITIATED("Initiated"),
    WAITING_FOR_CONFIRM("WaitingForConfirm"),
    CONFIRMED("Confirmed"),
    WAITING_FOR_ACCEPT("WaitingForAccept"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected");

    private final String value;

    SBPPaymentStatus(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static SBPPaymentStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SBPPaymentStatus candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static SBPPaymentStatus parse(String value) {
        SBPPaymentStatus parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение SBPPaymentStatus: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
