package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * AcquiringSubscriptionStatus
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum AcquiringSubscriptionStatus {

    ACTIVE("Active"),
    CANCELLED("Cancelled"),
    COMPLETED("Completed"),
    EXPIRED("Expired"),
    FAILED("Failed"),
    PAST_DUE("PastDue"),
    PREPARING("Preparing"),
    REFUSED("Refused"),
    REJECTED("Rejected"),
    SUSPENDED("Suspended"),
    TRIAL("Trial");

    private final String value;

    AcquiringSubscriptionStatus(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static AcquiringSubscriptionStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (AcquiringSubscriptionStatus candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static AcquiringSubscriptionStatus parse(String value) {
        AcquiringSubscriptionStatus parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение AcquiringSubscriptionStatus: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
