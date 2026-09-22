package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

/**
 * {@code incomingSbpPayment} — оплата по QR-коду: через Систему быстрых платежей либо цифровым
 * рублём. Приходит примерно за 5 секунд с момента зачисления.
 *
 * <p>Способ оплаты различается по {@link #paymentType()}: {@code sbpPayment} — СБП,
 * {@code drPayment} — цифровой рубль. Набор заполненных полей зависит от способа.
 *
 * @param operationId       идентификатор операции (для СБП — он же {@code trxId} при возврате)
 * @param qrcId             идентификатор QR-кода
 * @param amount            сумма операции
 * @param paymentType       способ оплаты: {@code sbpPayment} или {@code drPayment}
 * @param payerMobileNumber номер телефона покупателя (только СБП)
 * @param payerName         имя, отчество и первая буква фамилии покупателя
 * @param brandName         наименование ТСП
 * @param merchantId        идентификатор ТСП
 * @param purpose           назначение платежа
 * @param refTransactionId  идентификатор транзакции — нужен для возврата
 * @param drClientId        идентификатор клиента на Платформе цифрового рубля
 * @param webhookType       тип события, всегда {@code incomingSbpPayment}
 * @param customerCode      уникальный код клиента
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

    /** Оплата прошла через Систему быстрых платежей. */
    public boolean isSbp() {
        return "sbpPayment".equals(paymentType);
    }

    /** Оплата прошла цифровым рублём — такой платёж возвращается только в интернет-банке. */
    public boolean isDigitalRuble() {
        return "drPayment".equals(paymentType);
    }
}
