package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Событие, пришедшее в вебхуке. Конкретный тип определяется полем {@code webhookType}.
 *
 * <p>Разбирать событие удобно проверкой типа:
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
 * <p>Интерфейс запечатан, поэтому на Java 21+ то же самое записывается через {@code switch}
 * с сопоставлением по образцу и проверкой полноты ветвей.
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

    /** Тип события, например {@code incomingPayment}. */
    String webhookType();

    /** Уникальный код клиента, к которому относится событие. */
    String customerCode();
}
