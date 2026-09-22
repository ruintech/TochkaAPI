package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Envelope;
import com.tochka.api.http.Transport;
import com.tochka.api.model.QrCode;
import com.tochka.api.model.QrCodePaymentStatus;
import com.tochka.api.model.RegisterQRCode;
import com.tochka.api.model.RegisteredQrCode;
import java.util.List;

/**
 * СБП: статические и динамические QR-коды.
 *
 * <p>Экземпляр доступен через {@link com.tochka.api.TochkaClient}.
 */
public final class SbpQrCodesApi {

    private final Transport transport;

    public SbpQrCodesApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Get Qr Code. Метод возвращает данные одного QR-кода по его {@code qrcId}. Подробнее о работе с
     * QR-кодами — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param qrcId Идентификатор QR-кода в СБП
     */
    public QrCode getQrCode(String qrcId) {
        return transport.request("GET", "/sbp/v1.0/qr-code/{qrcId}")
                .path("qrcId", qrcId)
                .unwrap("Data")
                .as(QrCode.class);
    }

    /**
     * Get Qr Codes List. Метод возвращает список QR-кодов юрлица с их данными и статусами. Подробнее о
     * работе с QR-кодами — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов)
     */
    public List<QrCode> getQrCodesList(String legalId) {
        return transport.request("GET", "/sbp/v1.0/qr-code/legal-entity/{legalId}")
                .path("legalId", legalId)
                .unwrap("Data", "qrCodeList")
                .as(new TypeReference<List<QrCode>>() {});
    }

    /**
     * Get Qr Codes Payment Status. Метод показывает, оплачен ли динамический QR-код. По длине
     * идентификатора {@code trxId} в ответе можно понять способ оплаты: - 32 символа — оплата по СБП -
     * 36 — цифровым рублём Как принимать оплату по QR-кодам — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param qrcIds Список qr-кодов для запроса статусов, разделенных через запятую
     */
    public List<QrCodePaymentStatus> getQrCodesPaymentStatus(List<String> qrcIds) {
        return transport.request("GET", "/sbp/v1.0/qr-codes/{qrcIds}/payment-status")
                .path("qrcIds", String.join(",", qrcIds))
                .unwrap("Data", "paymentList")
                .as(new TypeReference<List<QrCodePaymentStatus>>() {});
    }

    /**
     * Register Qr Code. Метод создаёт статический или динамический QR-код для приёма оплаты по СБП. По
     * статическому коду можно принимать много оплат, динамический создаётся под конкретную сумму. Тип
     * задаётся в поле {@code qrcType}. Чем отличаются типы QR-кодов и как их создавать — в разделе
     * «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code EditSBPData}.
     *
     * @param merchantId Идентификатор ТСП
     * @param accountId Уникальный и неизменный идентификатор счёта юрлица
     * @param request тело запроса
     */
    public RegisteredQrCode registerQrCode(String merchantId, String accountId, RegisterQRCode request) {
        return transport.request("POST", "/sbp/v1.0/qr-code/merchant/{merchantId}/{accountId}")
                .path("merchantId", merchantId)
                .path("accountId", accountId)
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data")
                .as(RegisteredQrCode.class);
    }

}
