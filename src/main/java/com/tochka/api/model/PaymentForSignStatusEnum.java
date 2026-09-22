package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * **Описание статусов платежа на подпись** - {@code WaitingForCreate} - Платёж создан, ждёт
 * подписания в интернет-банке - {@code Created} - Платёж создан - {@code Paid} - Платёж оплачен -
 * {@code Canceled} - Платёж отменен - {@code Rejected} - Платёж отменён
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum PaymentForSignStatusEnum {

    WAITING_FOR_CREATE("WaitingForCreate"),
    CREATED("Created"),
    PAID("Paid"),
    CANCELED("Canceled"),
    REJECTED("Rejected");

    private final String value;

    PaymentForSignStatusEnum(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static PaymentForSignStatusEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (PaymentForSignStatusEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static PaymentForSignStatusEnum parse(String value) {
        PaymentForSignStatusEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение PaymentForSignStatusEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
