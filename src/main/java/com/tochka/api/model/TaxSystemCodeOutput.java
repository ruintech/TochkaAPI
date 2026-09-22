package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Перечисление для выдачи результатов из openapi.
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum TaxSystemCodeOutput {

    OSN("osn"),
    USN_INCOME("usn_income"),
    USN_INCOME_OUTCOME("usn_income_outcome"),
    ESN("esn"),
    PATENT("patent"),
    ENVD("envd");

    private final String value;

    TaxSystemCodeOutput(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static TaxSystemCodeOutput fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (TaxSystemCodeOutput candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static TaxSystemCodeOutput parse(String value) {
        TaxSystemCodeOutput parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение TaxSystemCodeOutput: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
