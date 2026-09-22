package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * * ACWP - Операция завершена успешно * RJCT - Операция отклонена * RCVD - Операция в обработке *
 * NTST - Операции по QR-коду не существует
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
 */
public enum SBPCashboxTrxStatus {

    ACWP("ACWP"),
    RJCT("RJCT"),
    RCVD("RCVD"),
    NTST("NTST");

    private final String value;

    SBPCashboxTrxStatus(String value) {
        this.value = value;
    }

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
    @JsonCreator
    public static SBPCashboxTrxStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SBPCashboxTrxStatus candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
    public static SBPCashboxTrxStatus parse(String value) {
        SBPCashboxTrxStatus parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение SBPCashboxTrxStatus: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
