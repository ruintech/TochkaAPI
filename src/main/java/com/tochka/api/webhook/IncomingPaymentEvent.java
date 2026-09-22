package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * {@code incomingPayment} — an incoming bank transfer. Delivered within 20 seconds of the
 * money arriving.
 *
 * @param payer          payer details
 * @param recipient      recipient details
 * @param purpose        payment purpose
 * @param documentNumber document number
 * @param paymentId      unique payment id, the same one that appears in the statement
 * @param date           payment date
 * @param webhookType    event type, always {@code incomingPayment}
 * @param customerCode   customer code
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IncomingPaymentEvent(
        @JsonProperty("SidePayer") PaymentSide payer,
        @JsonProperty("SideRecipient") PaymentSide recipient,
        String purpose,
        String documentNumber,
        String paymentId,
        LocalDate date,
        String webhookType,
        String customerCode) implements WebhookEvent {

    /** Payment amount — taken from the recipient details. */
    public BigDecimal amount() {
        return recipient == null ? null : recipient.amount();
    }
}
