package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * **Описание типов балансов** - {@code OpeningAvailable} - Начальный остаток - {@code
 * ClosingAvailable} - Доступный баланс - {@code Expected} - Сумма заблокированных средств - {@code
 * OverdraftAvailable} - Доступный лимит по овердрафту
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
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

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
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

    /** Parses a wire value, throwing on an unknown one. */
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
