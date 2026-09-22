package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

/**
 * {@code acquiringInternetPayment} — оплата по платёжной ссылке: картой, через СБП, цифровым
 * рублём или через «Долями». Приходит за 5–10 секунд с момента оплаты.
 *
 * <p>Это же событие используется в двухэтапной оплате: {@code AUTHORIZED} означает, что деньги
 * заморожены на карте покупателя и их ещё нужно списать методом
 * {@code acquiring().capturePayment(...)}, {@code APPROVED} — что оплата завершена.
 *
 * @param operationId   идентификатор платежа
 * @param amount        сумма платежа
 * @param paymentType   способ оплаты: {@code card}, {@code sbp}, {@code digitalRuble}, {@code dolyame}
 * @param status        статус платежа: {@code AUTHORIZED} или {@code APPROVED}
 * @param paymentLinkId номер заказа, переданный при создании ссылки или подписки
 * @param purpose       назначение платежа
 * @param merchantId    идентификатор торговой точки
 * @param consumerId    идентификатор покупателя, если карта была сохранена
 * @param transactionId идентификатор платежа в СБП
 * @param qrcId         идентификатор QR-кода при оплате через СБП
 * @param payerName     данные покупателя при оплате через СБП
 * @param maskedPan     маскированный номер карты, например {@code 220445******0792}
 * @param cardType      платёжная система карты, например {@code MIR}
 * @param tokenCardId   токен карты покупателя
 * @param webhookType   тип события, всегда {@code acquiringInternetPayment}
 * @param customerCode  уникальный код клиента
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

    /** Деньги списаны, оплата завершена. */
    public boolean isApproved() {
        return "APPROVED".equals(status);
    }

    /** Деньги заморожены на карте покупателя и ждут списания — двухэтапная оплата. */
    public boolean isAuthorized() {
        return "AUTHORIZED".equals(status);
    }
}
