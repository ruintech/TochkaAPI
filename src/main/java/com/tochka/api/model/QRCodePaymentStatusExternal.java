package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * **Описание возможных статусов платежа** - {@code NotStarted} - операции по QR-коду не существует
 * - {@code Received} - операция в обработке - {@code InProgress} - операция в обработке - {@code
 * Accepted} - операция завершена успешно - {@code Rejected} - операция отклонена
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum QRCodePaymentStatusExternal {

    NOT_STARTED("NotStarted"),
    RECEIVED("Received"),
    IN_PROGRESS("InProgress"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected");

    private final String value;

    QRCodePaymentStatusExternal(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static QRCodePaymentStatusExternal fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (QRCodePaymentStatusExternal candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static QRCodePaymentStatusExternal parse(String value) {
        QRCodePaymentStatusExternal parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение QRCodePaymentStatusExternal: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
