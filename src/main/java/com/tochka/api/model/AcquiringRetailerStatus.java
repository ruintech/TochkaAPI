package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * AcquiringRetailerStatus
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum AcquiringRetailerStatus {

    NEW("NEW"),
    ADDRESS_DADATA("ADDRESS_DADATA"),
    OPEN_ACCOUNT("OPEN_ACCOUNT"),
    TWPG_SENDED("TWPG_SENDED"),
    RETAILER_CREATED("RETAILER_CREATED"),
    TERMINAL_CREATED("TERMINAL_CREATED"),
    FILE_SENT("FILE_SENT"),
    REG("REG"),
    CLOSE("CLOSE");

    private final String value;

    AcquiringRetailerStatus(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static AcquiringRetailerStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (AcquiringRetailerStatus candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static AcquiringRetailerStatus parse(String value) {
        AcquiringRetailerStatus parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение AcquiringRetailerStatus: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
