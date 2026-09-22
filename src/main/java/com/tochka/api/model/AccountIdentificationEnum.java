package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * AccountIdentificationEnum
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum AccountIdentificationEnum {

    RU_CBR_PAN("RU.CBR.PAN"),
    RU_CBR_CELLPHONE_NUMBER("RU.CBR.CellphoneNumber"),
    RU_CBR_BBAN("RU.CBR.BBAN");

    private final String value;

    AccountIdentificationEnum(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static AccountIdentificationEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (AccountIdentificationEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static AccountIdentificationEnum parse(String value) {
        AccountIdentificationEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение AccountIdentificationEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
