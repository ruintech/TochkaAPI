package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * {@code outgoingPayment} — an outgoing transfer. Delivered within 20 seconds of the payment.
 *
 * @param payer          payer details
 * @param recipient      recipient details
 * @param purpose        payment purpose
 * @param documentNumber document number
 * @param paymentId      unique payment id, the same one that appears in the statement
 * @param date           payment date
 * @param webhookType    event type, always {@code outgoingPayment}
 * @param customerCode   customer code
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OutgoingPaymentEvent(
        @JsonProperty("SidePayer") PaymentSide payer,
        @JsonProperty("SideRecipient") PaymentSide recipient,
        String purpose,
        String documentNumber,
        String paymentId,
        LocalDate date,
        String webhookType,
        String customerCode) implements WebhookEvent {

    /** Payment amount — taken from the payer details. */
    public BigDecimal amount() {
        return payer == null ? null : payer.amount();
    }
}
