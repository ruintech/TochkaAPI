package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * **Описание статусов платежа на подпись** - {@code WaitingForCreate} - Платёж создан, ждёт
 * подписания в интернет-банке - {@code Created} - Платёж создан - {@code Paid} - Платёж оплачен -
 * {@code Canceled} - Платёж отменен - {@code Rejected} - Платёж отменён
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
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

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
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

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
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
