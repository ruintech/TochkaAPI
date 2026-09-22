package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * ExternalAccountStatusEnum
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum ExternalAccountStatusEnum {

    ENABLED("Enabled"),
    DISABLED("Disabled"),
    DELETED("Deleted"),
    PRO_FORMA("ProForma"),
    PENDING("Pending");

    private final String value;

    ExternalAccountStatusEnum(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static ExternalAccountStatusEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ExternalAccountStatusEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static ExternalAccountStatusEnum parse(String value) {
        ExternalAccountStatusEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение ExternalAccountStatusEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
