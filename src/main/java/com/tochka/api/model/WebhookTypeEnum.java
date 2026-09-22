package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * WebhookTypeEnum
 *
 * <p>Неизвестное значение, которого ещё нет в этой версии библиотеки,
 * разбирается в {@code null}, а не приводит к ошибке — используйте
 * {@link #parse(String)}, если незнакомое значение должно быть ошибкой.
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

    /** Значение, как оно передаётся в JSON. */
    @JsonValue
    public String value() {
        return this.value;
    }

    /** Разбирает значение из JSON; неизвестное значение даёт {@code null}. */
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

    /** Разбирает значение, выбрасывая исключение на неизвестном. */
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
