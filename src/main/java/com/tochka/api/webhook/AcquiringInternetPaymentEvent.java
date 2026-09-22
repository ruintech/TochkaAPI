package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

/**
 * {@code acquiringInternetPayment} — a payment made through a payment link: by card, through SBP,
 * with digital rubles or via «Долями». Delivered 5–10 seconds after the payment.
 *
 * <p>The same event serves two-stage payments: {@code AUTHORIZED} means the money is held on the
 * buyer card and still has to be captured with {@code acquiring().capturePayment(...)}, while
 * {@code APPROVED} means the payment is complete.
 *
 * @param operationId   payment id
 * @param amount        payment amount
 * @param paymentType   payment method: {@code card}, {@code sbp}, {@code digitalRuble}, {@code dolyame}
 * @param status        payment status: {@code AUTHORIZED} or {@code APPROVED}
 * @param paymentLinkId order number passed when the link or the subscription was created
 * @param purpose       payment purpose
 * @param merchantId    retailer id
 * @param consumerId    buyer id, when the card was saved
 * @param transactionId payment id within SBP
 * @param qrcId         QR code id for SBP payments
 * @param payerName     buyer details for SBP payments
 * @param maskedPan     masked card number, for example {@code 220445******0792}
 * @param cardType      card payment system, for example {@code MIR}
 * @param tokenCardId   token of the buyer card
 * @param webhookType   event type, always {@code acquiringInternetPayment}
 * @param customerCode  customer code
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AcquiringInternetPaymentEvent(
        String operationId,
        BigDecimal amount,
        String paymentType,
        String status,
        String paymentLinkId,
        String purpose,
        String merchantId,
        String consumerId,
        String transactionId,
        String qrcId,
        String payerName,
        String maskedPan,
        String cardType,
        String tokenCardId,
        String webhookType,
        String customerCode) implements WebhookEvent {

    /** The money is captured, the payment is complete. */
    public boolean isApproved() {
        return "APPROVED".equals(status);
    }

    /** The money is held on the buyer card and awaits capture — a two-stage payment. */
    public boolean isAuthorized() {
        return "AUTHORIZED".equals(status);
    }
}
