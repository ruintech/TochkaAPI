package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * PaymentStatusEnum
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum InvoicePaymentStatusEnum {

    PAYMENT_WAITING("payment_waiting"),
    PAYMENT_EXPIRED("payment_expired"),
    PAYMENT_PAID("payment_paid");

    private final String value;

    InvoicePaymentStatusEnum(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static InvoicePaymentStatusEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (InvoicePaymentStatusEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static InvoicePaymentStatusEnum parse(String value) {
        InvoicePaymentStatusEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение InvoicePaymentStatusEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
