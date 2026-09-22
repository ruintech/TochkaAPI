package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * An event delivered by a webhook. The concrete type is decided by the {@code webhookType} field.
 *
 * <p>The convenient way to handle it is a type check:
 *
 * <pre>{@code
 * WebhookEvent event = verifier.verify(requestBody);
 * if (event instanceof IncomingPaymentEvent e) {
 *     onIncoming(e.paymentId(), e.amount());
 * } else if (event instanceof AcquiringInternetPaymentEvent e && e.isApproved()) {
 *     onPaid(e.paymentLinkId());
 * }
 * }</pre>
 *
 * <p>The interface is sealed, so on Java 21+ the same thing can be written as a {@code switch}
 * with pattern matching and exhaustiveness checking.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "webhookType",
        visible = true,
        defaultImpl = UnknownWebhookEvent.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = IncomingPaymentEvent.class, name = "incomingPayment"),
        @JsonSubTypes.Type(value = OutgoingPaymentEvent.class, name = "outgoingPayment"),
        @JsonSubTypes.Type(value = IncomingSbpPaymentEvent.class, name = "incomingSbpPayment"),
        @JsonSubTypes.Type(value = IncomingSbpB2bPaymentEvent.class, name = "incomingSbpB2BPayment"),
        @JsonSubTypes.Type(value = AcquiringInternetPaymentEvent.class, name = "acquiringInternetPayment")
})
public sealed interface WebhookEvent
        permits IncomingPaymentEvent, OutgoingPaymentEvent, IncomingSbpPaymentEvent,
        IncomingSbpB2bPaymentEvent, AcquiringInternetPaymentEvent, UnknownWebhookEvent {

    /** Event type, for example {@code incomingPayment}. */
    String webhookType();

    /** Customer code the event belongs to. */
    String customerCode();
}
