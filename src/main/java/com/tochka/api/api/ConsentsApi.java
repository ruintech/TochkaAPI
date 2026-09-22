package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Envelope;
import com.tochka.api.http.Transport;
import com.tochka.api.model.ConsentCreateRequest;
import com.tochka.api.model.ConsentModel;
import com.tochka.api.model.ConsentResponseModel;
import java.util.List;

/**
 * Списки разрешений (consent) для авторизации по OAuth 2.0.
 *
 * <p>Экземпляр доступен через {@link com.tochka.api.TochkaClient}.
 */
public final class ConsentsApi {

    private final Transport transport;

    public ConsentsApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Create New Consent. Метод создаёт список разрешений — набор прав, которые вы запрашиваете у
     * клиента при авторизации по OAuth 2.0. В ответ приходит {@code consentId}, который клиент затем
     * подтверждает. Как устроена авторизация по OAuth 2.0 — в разделе «Авторизация по OAuth 2.0
     * (/docs/tochka-api/algoritm-raboty-po-oauth-2.0)».
     *
     * @param request тело запроса
     */
    public List<ConsentResponseModel> createNewConsent(ConsentCreateRequest request) {
        return transport.request("POST", "/consent/v1.0/consents")
                .body(request)
                .unwrap("Data", "Consent")
                .as(new TypeReference<List<ConsentResponseModel>>() {});
    }

    /**
     * Get All Child Consents. Метод возвращает дочерние разрешения, выданные в рамках родительского
     * списка. Про разрешения и scope — в разделе «Авторизация по OAuth 2.0
     * (/docs/tochka-api/algoritm-raboty-po-oauth-2.0)».
     *
     * @param consentId Уникальный идентификатор, предназначенный для идентификации разрешения
     */
    public List<ConsentResponseModel> getAllChildConsents(String consentId) {
        return transport.request("GET", "/consent/v1.0/consents/{consentId}/child")
                .path("consentId", consentId)
                .unwrap("Data", "Consent")
                .as(new TypeReference<List<ConsentResponseModel>>() {});
    }

    /**
     * Get All Consents List. Метод возвращает список созданных списков разрешений и их статусы.
     * Помогает проверить, какие права выданы и подтверждены. Про разрешения и scope — в разделе
     * «Авторизация по OAuth 2.0 (/docs/tochka-api/algoritm-raboty-po-oauth-2.0)».
     *
     * @param customerCode уникальный код клиента; {@code null} — взять код по умолчанию из клиента
     */
    public List<ConsentResponseModel> getAllConsentsList(String customerCode) {
        return transport.request("GET", "/consent/v1.0/consents")
                .header("customer-code", customerCode != null ? customerCode : transport.defaultCustomerCode())
                .unwrap("Data", "Consent")
                .as(new TypeReference<List<ConsentResponseModel>>() {});
    }

    /**
     * Get All Consents List. Метод возвращает список созданных списков разрешений и их статусы.
     * Помогает проверить, какие права выданы и подтверждены. Про разрешения и scope — в разделе
     * «Авторизация по OAuth 2.0 (/docs/tochka-api/algoritm-raboty-po-oauth-2.0)».
     *
     * <p>Код клиента берётся из настроек клиента ({@code TochkaClient.builder().customerCode(...)}).
     */
    public List<ConsentResponseModel> getAllConsentsList() {
        return transport.request("GET", "/consent/v1.0/consents")
                .header("customer-code", transport.defaultCustomerCode())
                .unwrap("Data", "Consent")
                .as(new TypeReference<List<ConsentResponseModel>>() {});
    }

    /**
     * Get Consent Info. Метод возвращает данные конкретного списка разрешений по его {@code
     * consentId}: статус, набор прав и срок действия. Про разрешения и scope — в разделе «Авторизация
     * по OAuth 2.0 (/docs/tochka-api/algoritm-raboty-po-oauth-2.0)».
     *
     * @param consentId Уникальный идентификатор, предназначенный для идентификации разрешения
     * @param customerCode уникальный код клиента; {@code null} — взять код по умолчанию из клиента
     */
    public ConsentModel getConsentInfo(String consentId, String customerCode) {
        return transport.request("GET", "/consent/v1.0/consents/{consentId}")
                .path("consentId", consentId)
                .header("customer-code", customerCode != null ? customerCode : transport.defaultCustomerCode())
                .unwrap("Data")
                .as(ConsentModel.class);
    }

    /**
     * Get Consent Info. Метод возвращает данные конкретного списка разрешений по его {@code
     * consentId}: статус, набор прав и срок действия. Про разрешения и scope — в разделе «Авторизация
     * по OAuth 2.0 (/docs/tochka-api/algoritm-raboty-po-oauth-2.0)».
     *
     * <p>Код клиента берётся из настроек клиента ({@code TochkaClient.builder().customerCode(...)}).
     *
     * @param consentId Уникальный идентификатор, предназначенный для идентификации разрешения
     */
    public ConsentModel getConsentInfo(String consentId) {
        return transport.request("GET", "/consent/v1.0/consents/{consentId}")
                .path("consentId", consentId)
                .header("customer-code", transport.defaultCustomerCode())
                .unwrap("Data")
                .as(ConsentModel.class);
    }

}
