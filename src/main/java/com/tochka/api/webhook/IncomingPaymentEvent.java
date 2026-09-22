package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * {@code incomingPayment} — поступление на счёт по реквизитам. Приходит в течение 20 секунд
 * после зачисления.
 *
 * @param payer          реквизиты плательщика
 * @param recipient      реквизиты получателя
 * @param purpose        назначение платежа
 * @param documentNumber номер документа
 * @param paymentId      уникальный идентификатор платежа, он же есть в выписке
 * @param date           дата платежа
 * @param webhookType    тип события, всегда {@code incomingPayment}
 * @param customerCode   уникальный код клиента
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

    /** Сумма платежа — берётся из реквизитов получателя. */
    public BigDecimal amount() {
        return recipient == null ? null : recipient.amount();
    }
}
