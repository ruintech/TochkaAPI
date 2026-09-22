package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * AcquiringPaymentStatus
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum AcquiringPaymentStatus {

    CREATED("CREATED"),
    APPROVED("APPROVED"),
    ON_REFUND("ON-REFUND"),
    REFUNDED("REFUNDED"),
    EXPIRED("EXPIRED"),
    REFUNDED_PARTIALLY("REFUNDED_PARTIALLY"),
    AUTHORIZED("AUTHORIZED"),
    WAIT_FULL_PAYMENT("WAIT_FULL_PAYMENT");

    private final String value;

    AcquiringPaymentStatus(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static AcquiringPaymentStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (AcquiringPaymentStatus candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static AcquiringPaymentStatus parse(String value) {
        AcquiringPaymentStatus parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение AcquiringPaymentStatus: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
