package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * SBPCashboxQrCodeStatus
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum SBPCashboxQrCodeStatus {

    INACTIVATED("INACTIVATED"),
    WAITING_PAYMENT("WAITING_PAYMENT"),
    IN_PROGRESS("IN_PROGRESS");

    private final String value;

    SBPCashboxQrCodeStatus(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static SBPCashboxQrCodeStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SBPCashboxQrCodeStatus candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static SBPCashboxQrCodeStatus parse(String value) {
        SBPCashboxQrCodeStatus parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение SBPCashboxQrCodeStatus: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
