package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * FinancialInstitutionIdentificationEnum
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum FinancialInstitutionIdentificationEnum {

    RU_CBR_BICFI("RU.CBR.BICFI"),
    RU_CBR_BIK("RU.CBR.BIK");

    private final String value;

    FinancialInstitutionIdentificationEnum(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static FinancialInstitutionIdentificationEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (FinancialInstitutionIdentificationEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static FinancialInstitutionIdentificationEnum parse(String value) {
        FinancialInstitutionIdentificationEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение FinancialInstitutionIdentificationEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
