package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * **Описание возможных статусов платежа** - {@code NotStarted} - операции по QR-коду не существует
 * - {@code Received} - операция в обработке - {@code InProgress} - операция в обработке - {@code
 * Accepted} - операция завершена успешно - {@code Rejected} - операция отклонена
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
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

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
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

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
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
