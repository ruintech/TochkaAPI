package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * **Описание возможных статусов платежа** - {@code Confirming} - операция в процессе подтверждения
 * ОПКЦ СБП - {@code Confirmed} - операция подтверждена - {@code Initiated} - операция отправлена
 * на обработку - {@code Accepting} - операция в обработке ОПКЦ СБП - {@code Accepted} - операция
 * успешно завершена - {@code InProgress} - операция в обработке РЦ СБП - {@code Rejected} -
 * операция отклонена - {@code Error} - ошибка выполнения операции - {@code Timeout} - тайм-аут
 * выполнения операции
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum SbpQrCodePaymentStatusEnum {

    CONFIRMING("Confirming"),
    CONFIRMED("Confirmed"),
    INITIATED("Initiated"),
    ACCEPTING("Accepting"),
    ACCEPTED("Accepted"),
    IN_PROGRESS("InProgress"),
    REJECTED("Rejected"),
    ERROR("Error"),
    TIMEOUT("Timeout");

    private final String value;

    SbpQrCodePaymentStatusEnum(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static SbpQrCodePaymentStatusEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SbpQrCodePaymentStatusEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static SbpQrCodePaymentStatusEnum parse(String value) {
        SbpQrCodePaymentStatusEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение SbpQrCodePaymentStatusEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
