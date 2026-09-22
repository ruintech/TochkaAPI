package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * **Описание типов балансов** - {@code OpeningAvailable} - Начальный остаток - {@code
 * ClosingAvailable} - Доступный баланс - {@code Expected} - Сумма заблокированных средств - {@code
 * OverdraftAvailable} - Доступный лимит по овердрафту
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum ExternalBalanceStaticTypeEnum {

    OPENING_AVAILABLE("OpeningAvailable"),
    CLOSING_AVAILABLE("ClosingAvailable"),
    EXPECTED("Expected"),
    OVERDRAFT_AVAILABLE("OverdraftAvailable");

    private final String value;

    ExternalBalanceStaticTypeEnum(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static ExternalBalanceStaticTypeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ExternalBalanceStaticTypeEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static ExternalBalanceStaticTypeEnum parse(String value) {
        ExternalBalanceStaticTypeEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение ExternalBalanceStaticTypeEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
