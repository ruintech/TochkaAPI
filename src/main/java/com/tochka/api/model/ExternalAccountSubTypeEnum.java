package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * ExternalAccountSubTypeEnum
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum ExternalAccountSubTypeEnum {

    CREDIT_CARD("CreditCard"),
    CURRENT_ACCOUNT("CurrentAccount"),
    LOAN("Loan"),
    MORTGAGE("Mortgage"),
    PRE_PAID_CARD("PrePaidCard"),
    SAVINGS("Savings"),
    SPECIAL("Special");

    private final String value;

    ExternalAccountSubTypeEnum(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static ExternalAccountSubTypeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ExternalAccountSubTypeEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static ExternalAccountSubTypeEnum parse(String value) {
        ExternalAccountSubTypeEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение ExternalAccountSubTypeEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
