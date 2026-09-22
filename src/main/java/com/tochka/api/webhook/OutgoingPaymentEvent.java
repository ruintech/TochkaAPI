package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * {@code outgoingPayment} — списание со счёта. Приходит в течение 20 секунд после платежа.
 *
 * @param payer          реквизиты плательщика
 * @param recipient      реквизиты получателя
 * @param purpose        назначение платежа
 * @param documentNumber номер документа
 * @param paymentId      уникальный идентификатор платежа, он же есть в выписке
 * @param date           дата платежа
 * @param webhookType    тип события, всегда {@code outgoingPayment}
 * @param customerCode   уникальный код клиента
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

    /** Сумма платежа — берётся из реквизитов плательщика. */
    public BigDecimal amount() {
        return payer == null ? null : payer.amount();
    }
}
