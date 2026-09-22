package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

/**
 * {@code incomingSbpPayment} — a QR code payment, made either through the Faster Payments System
 * (SBP) or with digital rubles. Delivered about 5 seconds after the money arrives.
 *
 * <p>The payment method is told apart by {@link #paymentType()}: {@code sbpPayment} for SBP,
 * {@code drPayment} for digital rubles. Which fields are filled in depends on the method.
 *
 * @param operationId       operation id (for SBP it doubles as {@code trxId} when refunding)
 * @param qrcId             QR code id
 * @param amount            operation amount
 * @param paymentType       payment method: {@code sbpPayment} or {@code drPayment}
 * @param payerMobileNumber buyer phone number (SBP only)
 * @param payerName         buyer first name, patronymic and the initial of the last name
 * @param brandName         merchant name
 * @param merchantId        merchant id
 * @param purpose           payment purpose
 * @param refTransactionId  transaction id — required to issue a refund
 * @param drClientId        client id on the digital ruble platform
 * @param webhookType       event type, always {@code incomingSbpPayment}
 * @param customerCode      customer code
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IncomingSbpPaymentEvent(
        String operationId,
        String qrcId,
        BigDecimal amount,
        String paymentType,
        String payerMobileNumber,
        String payerName,
        String brandName,
        String merchantId,
        String purpose,
        String refTransactionId,
        String drClientId,
        String webhookType,
        String customerCode) implements WebhookEvent {

    /** The payment went through the Faster Payments System. */
    public boolean isSbp() {
        return "sbpPayment".equals(paymentType);
    }

    /** The payment was made with digital rubles — such a payment can only be refunded in the internet bank. */
    public boolean isDigitalRuble() {
        return "drPayment".equals(paymentType);
    }
}
