package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * {@code 001} - только QR Static {@code 010} - только QR Dynamic {@code 011} - QR Static и QR
 * Dynamic {@code 100} - Только QR Subscription {@code 101} - QR Subscription и QR Static {@code
 * 110} - QR Subscription и QR Dynamic {@code 111} - QR Static, QR Dynamic и QR Subscription
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum CapabilitiesEnum {

    V001("001"),
    V010("010"),
    V011("011"),
    V100("100"),
    V101("101"),
    V110("110"),
    V111("111");

    private final String value;

    CapabilitiesEnum(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static CapabilitiesEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (CapabilitiesEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static CapabilitiesEnum parse(String value) {
        CapabilitiesEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение CapabilitiesEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
