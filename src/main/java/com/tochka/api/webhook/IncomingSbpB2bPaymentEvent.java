package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

/**
 * {@code incomingSbpB2BPayment} — a payment made through an SBP B2B QR code, that is, by a
 * company or a sole proprietor. Delivered about 10 seconds after the money arrives.
 *
 * @param qrcId        QR code id
 * @param amount       operation amount
 * @param purpose      payment purpose
 * @param webhookType  event type, always {@code incomingSbpB2BPayment}
 * @param customerCode customer code
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IncomingSbpB2bPaymentEvent(
        String qrcId,
        BigDecimal amount,
        String purpose,
        String webhookType,
        String customerCode) implements WebhookEvent {
}
