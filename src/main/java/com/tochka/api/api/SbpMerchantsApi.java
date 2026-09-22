package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Envelope;
import com.tochka.api.http.Transport;
import com.tochka.api.model.Merchant;
import com.tochka.api.model.RegisterMerchant;
import com.tochka.api.model.StatusEnum;
import java.util.List;

/**
 * СБП: торгово-сервисные предприятия (торговые точки).
 *
 * <p>Экземпляр доступен через {@link com.tochka.api.TochkaClient}.
 */
public final class SbpMerchantsApi {

    private final Transport transport;

    public SbpMerchantsApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Get Merchant. Метод возвращает данные одной торговой точки по её {@code merchantId}. Про
     * регистрацию и работу с ТСП — в разделе «Регистрация ЮЛ или ТСП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/registraciya-yul-i-tsp)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param merchantId Идентификатор ТСП
     */
    public Merchant getMerchant(String merchantId) {
        return transport.request("GET", "/sbp/v1.0/merchant/{merchantId}")
                .path("merchantId", merchantId)
                .unwrap("Data")
                .as(Merchant.class);
    }

    /**
     * Get Merchants List. Метод возвращает список торговых точек юрлица с их merchantId и данными. Про
     * регистрацию и работу с ТСП — в разделе «Регистрация ЮЛ или ТСП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/registraciya-yul-i-tsp)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов)
     */
    public List<Merchant> getMerchantsList(String legalId) {
        return transport.request("GET", "/sbp/v1.0/merchant/legal-entity/{legalId}")
                .path("legalId", legalId)
                .unwrap("Data", "MerchantList")
                .as(new TypeReference<List<Merchant>>() {});
    }

    /**
     * Register Merchant. Метод регистрирует торговую точку (ТСП) в СБП. Это нужно один раз перед
     * созданием QR-кодов: без зарегистрированной точки принимать оплату нельзя. В ответ приходит
     * {@code merchantId}. Как зарегистрировать ЮЛ и торговую точку — в разделе «Регистрация ЮЛ или ТСП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/registraciya-yul-i-tsp)».
     *
     * <p>Требуемые разрешения: {@code EditSBPData}.
     *
     * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов)
     * @param request тело запроса
     */
    public String registerMerchant(String legalId, RegisterMerchant request) {
        return transport.request("POST", "/sbp/v1.0/merchant/legal-entity/{legalId}")
                .path("legalId", legalId)
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data", "merchantId")
                .as(String.class);
    }

    /**
     * Set Merchant Status. Метод меняет статус торговой точки — например, приостанавливает её работу
     * или возобновляет. Про работу с ТСП — в разделе «Регистрация ЮЛ или ТСП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/registraciya-yul-i-tsp)».
     *
     * <p>Требуемые разрешения: {@code EditSBPData}.
     *
     * @param merchantId Идентификатор ТСП
     * @param status Статус объекта. Например: "Active"
     */
    public Boolean setMerchantStatus(String merchantId, StatusEnum status) {
        return transport.request("PUT", "/sbp/v1.0/merchant/{merchantId}")
                .path("merchantId", merchantId)
                .body(Envelope.wrap(status, "Data", "status"))
                .unwrap("Data", "result")
                .as(Boolean.class);
    }

}
