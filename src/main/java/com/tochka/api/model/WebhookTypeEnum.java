package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * WebhookTypeEnum
 *
 * <p>A value that is not yet known to this version of the library is parsed
 * as {@code null} instead of failing; use {@link #parse(String)} when an
 * unknown value must be an error.
 */
public enum WebhookTypeEnum {

    INCOMING_PAYMENT("incomingPayment"),
    OUTGOING_PAYMENT("outgoingPayment"),
    INCOMING_SBP_PAYMENT("incomingSbpPayment"),
    ACQUIRING_INTERNET_PAYMENT("acquiringInternetPayment"),
    INCOMING_SBP_B2_BPAYMENT("incomingSbpB2BPayment"),
    CUSTOM_WEBHOOK("customWebhook");

    private final String value;

    WebhookTypeEnum(String value) {
        this.value = value;
    }

    /** The value as it is sent over the wire. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Parses a wire value; an unknown one yields {@code null}. */
    @JsonCreator
    public static WebhookTypeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (WebhookTypeEnum candidate : values()) {
            if (candidate.value.equals(value)) {
                return candidate;
            }
        }
        return null;
    }

    /** Parses a wire value, throwing on an unknown one. */
    public static WebhookTypeEnum parse(String value) {
        WebhookTypeEnum parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException(
                    "Неизвестное значение WebhookTypeEnum: " + value);
        }
        return parsed;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
