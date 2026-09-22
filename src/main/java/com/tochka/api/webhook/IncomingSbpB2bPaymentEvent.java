package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

/**
 * {@code incomingSbpB2BPayment} — оплата по B2B QR-коду СБП, то есть платёж от ИП или
 * организации. Приходит примерно за 10 секунд с момента зачисления.
 *
 * @param qrcId        идентификатор QR-кода
 * @param amount       сумма операции
 * @param purpose      назначение платежа
 * @param webhookType  тип события, всегда {@code incomingSbpB2BPayment}
 * @param customerCode уникальный код клиента
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IncomingSbpB2bPaymentEvent(
        String qrcId,
        BigDecimal amount,
        String purpose,
        String webhookType,
        String customerCode) implements WebhookEvent {
}
