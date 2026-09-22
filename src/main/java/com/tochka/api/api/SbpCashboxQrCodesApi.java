package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Envelope;
import com.tochka.api.http.Transport;
import com.tochka.api.model.ActivateCashboxQrCodeRequestModel;
import com.tochka.api.model.ActivateCashboxQrCodeResponseModel;
import com.tochka.api.model.CashboxQrCodeResponseModel;
import com.tochka.api.model.ChangeCashboxQRCodeAccountResponseModel;
import com.tochka.api.model.GetCashboxQRCodeListResponseItemModel;
import com.tochka.api.model.GetCashboxQRCodeRequestModel;
import com.tochka.api.model.GetCashboxQrCodeOperationInfoResponseModel;
import com.tochka.api.model.GetCashboxQrCodeStatusResponseModel;
import com.tochka.api.model.RegisterCashboxQrCodeRequestModel;
import com.tochka.api.model.RegisterCashboxQrCodeResponseModel;
import java.util.List;

/**
 * СБП: кассовые QR-коды — один код, много оплат с переактивацией.
 *
 * <p>Экземпляр доступен через {@link com.tochka.api.TochkaClient}.
 */
public final class SbpCashboxQrCodesApi {

    private final Transport transport;

    public SbpCashboxQrCodesApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Activate Cashbox Qrcode. Метод активирует кассовый QR-код перед оплатой: задаёт сумму и срок
     * действия. После оплаты или по истечении срока код нужно активировать заново. Как принимать
     * оплату кассовым QR-кодом — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code EditSBPData}.
     *
     * @param qrcId Идентификатор QR-кода в СБП
     * @param request тело запроса
     */
    public ActivateCashboxQrCodeResponseModel activateCashboxQrcode(String qrcId, ActivateCashboxQrCodeRequestModel request) {
        return transport.request("POST", "/sbp/v1.0/cashbox-qr-code/{qrcId}/activate")
                .path("qrcId", qrcId)
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data")
                .as(ActivateCashboxQrCodeResponseModel.class);
    }

    /**
     * Change Cashbox Qrcode Account. Метод меняет счёт, на который зачисляется оплата по кассовому
     * QR-коду. Про кассовые QR-коды — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code EditSBPData}.
     *
     * @param qrcId Идентификатор QR-кода в СБП
     * @param accountId Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
     */
    public ChangeCashboxQRCodeAccountResponseModel changeCashboxQrcodeAccount(String qrcId, String accountId) {
        return transport.request("POST", "/sbp/v1.0/cashbox-qr-code/{qrcId}/account")
                .path("qrcId", qrcId)
                .body(Envelope.wrap(accountId, "Data", "accountId"))
                .unwrap("Data")
                .as(ChangeCashboxQRCodeAccountResponseModel.class);
    }

    /**
     * Deactivate Cashbox Qrcode. Метод отключает активированный кассовый QR-код — например, если
     * покупатель передумал оплачивать. Про кассовые QR-коды — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code EditSBPData}.
     *
     * @param qrcId Идентификатор QR-кода в СБП
     */
    public Boolean deactivateCashboxQrcode(String qrcId) {
        return transport.request("POST", "/sbp/v1.0/cashbox-qr-code/{qrcId}/deactivate")
                .path("qrcId", qrcId)
                .unwrap("Data", "result")
                .as(Boolean.class);
    }

    /**
     * Get Cashbox Qrcode. Метод возвращает данные одного кассового QR-кода по его qrcId. Про кассовые
     * QR-коды — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param qrcId Идентификатор QR-кода в СБП
     * @param request тело запроса
     */
    public CashboxQrCodeResponseModel getCashboxQrcode(String qrcId, GetCashboxQRCodeRequestModel request) {
        return transport.request("POST", "/sbp/v1.0/cashbox-qr-code/{qrcId}")
                .path("qrcId", qrcId)
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data")
                .as(CashboxQrCodeResponseModel.class);
    }

    /**
     * Get Cashbox Qrcode List. Метод возвращает список кассовых QR-кодов торговой точки. Про кассовые
     * QR-коды — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param merchantId Идентификатор ТСП
     * @param accountId Уникальный и неизменный идентификатор счёта
     */
    public List<GetCashboxQRCodeListResponseItemModel> getCashboxQrcodeList(String merchantId, String accountId) {
        return transport.request("GET", "/sbp/v1.0/cashbox-qr-code/merchant/{merchantId}/{accountId}")
                .path("merchantId", merchantId)
                .path("accountId", accountId)
                .unwrap("Data", "qrCodes")
                .as(new TypeReference<List<GetCashboxQRCodeListResponseItemModel>>() {});
    }

    /**
     * Get Cashbox Qrcode Operation Info. Метод возвращает информацию об операции по кассовому QR-коду
     * и её статус. Про кассовые QR-коды — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param qrcId Идентификатор QR-кода в СБП
     * @param paramsId Идентификатор активных значений параметров QR-кода
     */
    public GetCashboxQrCodeOperationInfoResponseModel getCashboxQrcodeOperationInfo(String qrcId, String paramsId) {
        return transport.request("GET", "/sbp/v1.0/cashbox-qr-code/{qrcId}/operation")
                .path("qrcId", qrcId)
                .query("paramsId", paramsId)
                .unwrap("Data")
                .as(GetCashboxQrCodeOperationInfoResponseModel.class);
    }

    /**
     * Get Cashbox Qrcode Status. Метод показывает текущий статус кассового QR-кода: активен, ожидает
     * оплаты или оплата обрабатывается. Про кассовые QR-коды — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param qrcId Идентификатор QR-кода в СБП
     */
    public GetCashboxQrCodeStatusResponseModel getCashboxQrcodeStatus(String qrcId) {
        return transport.request("GET", "/sbp/v1.0/cashbox-qr-code/{qrcId}/status")
                .path("qrcId", qrcId)
                .unwrap("Data")
                .as(GetCashboxQrCodeStatusResponseModel.class);
    }

    /**
     * Register Cashbox Qrcode. Метод создаёт кассовый QR-код — один код, по которому можно принимать
     * много оплат, но перед каждой его нужно активировать заново. Подходит для кассы. Как работать с
     * кассовыми QR-кодами — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code EditSBPData}.
     *
     * @param request тело запроса
     */
    public RegisterCashboxQrCodeResponseModel registerCashboxQrcode(RegisterCashboxQrCodeRequestModel request) {
        return transport.request("POST", "/sbp/v1.0/cashbox-qr-code")
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data")
                .as(RegisterCashboxQrCodeResponseModel.class);
    }

}
