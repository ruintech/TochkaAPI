package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Envelope;
import com.tochka.api.http.Transport;
import com.tochka.api.model.PaymentForSignListItemModel;
import com.tochka.api.model.PaymentForSignRequestModel;
import com.tochka.api.model.PaymentForSignResponseModel;
import com.tochka.api.model.PaymentStatusResponseModel;
import java.util.List;

/**
 * Платёжные поручения: создание платежа на подпись и его статус.
 *
 * <p>Экземпляр доступен через {@link com.tochka.api.TochkaClient}.
 */
public final class PaymentsApi {

    private final Transport transport;

    public PaymentsApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Create Payment For Sign. Метод создаёт платёжку и отправляет её на подпись. Сам по себе он
     * деньги не переводит: платёж появляется в интернет-банке в разделе «На подпись», и уходит
     * получателю только после того, как сотрудник подпишет его там же с помощью смс-кода. В ответ
     * приходит ссылка на страницу подписания. *Важно* - Если создаёте платёж за третье лицо, поля
     * {@code payerINN} и {@code payerKPP} обязательны - Если платите за себя в бюджет, обязательным
     * становится {@code payerKPP} - Поле {@code paymentDate} заполняется по часовому поясу Москвы. Как
     * устроена отправка платежа и его статусы — в разделе «Платёжные поручения
     * (/docs/tochka-api/opisanie-metodov/platezhi)».
     *
     * <p>Требуемые разрешения: {@code CreatePaymentForSign}.
     *
     * @param request тело запроса
     */
    public PaymentForSignResponseModel createPaymentForSign(PaymentForSignRequestModel request) {
        return transport.request("POST", "/payment/v1.0/for-sign")
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data")
                .as(PaymentForSignResponseModel.class);
    }

    /**
     * Get Payment For Sign List. Метод возвращает платежи из раздела «На подпись» — созданные и через
     * API, и вручную в интернет-банке. Подробнее об исходящих платежах — в разделе «Платёжные
     * поручения (/docs/tochka-api/opisanie-metodov/platezhi)».
     *
     * <p>Требуемые разрешения: {@code CreatePaymentForSign}.
     *
     * @param customerCode Уникальный код клиента
     */
    public List<PaymentForSignListItemModel> getPaymentForSignList(String customerCode) {
        return transport.request("GET", "/payment/v1.0/for-sign")
                .query("customerCode", customerCode)
                .unwrap("Data", "Payment")
                .as(new TypeReference<List<PaymentForSignListItemModel>>() {});
    }

    /**
     * Get Payment For Sign List. Метод возвращает платежи из раздела «На подпись» — созданные и через
     * API, и вручную в интернет-банке. Подробнее об исходящих платежах — в разделе «Платёжные
     * поручения (/docs/tochka-api/opisanie-metodov/platezhi)».
     *
     * <p>Код клиента берётся из настроек клиента ({@code TochkaClient.builder().customerCode(...)}).
     *
     * <p>Требуемые разрешения: {@code CreatePaymentForSign}.
     */
    public List<PaymentForSignListItemModel> getPaymentForSignList() {
        return transport.request("GET", "/payment/v1.0/for-sign")
                .query("customerCode", requireCustomerCode())
                .unwrap("Data", "Payment")
                .as(new TypeReference<List<PaymentForSignListItemModel>>() {});
    }

    /**
     * Get Payment Status. Метод показывает, на каком этапе платёж: ждёт подписания, передан в
     * обработку, оплачен, отменён или отклонён. Что означают статусы платежа вы можете прочитать в
     * разделе «Платёжные поручения (/docs/tochka-api/opisanie-metodov/platezhi)».
     *
     * <p>Требуемые разрешения: {@code CreatePaymentForSign, CreatePaymentOrder}.
     *
     * @param requestId Идентификатор запроса
     */
    public PaymentStatusResponseModel getPaymentStatus(String requestId) {
        return transport.request("GET", "/payment/v1.0/status/{requestId}")
                .path("requestId", requestId)
                .unwrap("Data")
                .as(PaymentStatusResponseModel.class);
    }

    private String requireCustomerCode() {
        String code = transport.defaultCustomerCode();
        if (code == null || code.isBlank()) {
            throw new IllegalStateException("Не задан customerCode: передайте его параметром метода "
                    + "или задайте по умолчанию через TochkaClient.builder().customerCode(...)");
        }
        return code;
    }

}
