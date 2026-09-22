package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Envelope;
import com.tochka.api.http.Transport;
import com.tochka.api.model.Account;
import com.tochka.api.model.CustomerCodeAndBankCode;
import com.tochka.api.model.CustomerInfoResponseV3;
import com.tochka.api.model.LegalEntity;
import com.tochka.api.model.StatusEnum;
import java.util.List;

/**
 * СБП: регистрация юрлица и его счета.
 *
 * <p>Экземпляр доступен через {@link com.tochka.api.TochkaClient}.
 */
public final class SbpLegalEntitiesApi {

    private final Transport transport;

    public SbpLegalEntitiesApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Get Accounts List. Метод возвращает счета юрлица, доступные для приёма оплаты по СБП. Про
     * регистрацию и счета в СБП — в разделе «Регистрация ЮЛ или ТСП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/registraciya-yul-i-tsp)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов)
     */
    public List<Account> getAccountsList(String legalId) {
        return transport.request("GET", "/sbp/v1.0/account/{legalId}")
                .path("legalId", legalId)
                .unwrap("Data", "AccountList")
                .as(new TypeReference<List<Account>>() {});
    }

    /**
     * Get Customer Info. Метод возвращает данные клиента в СБП. По нему же можно проверить, подключён
     * ли у клиента цифровой рубль: если да, в ответе придёт объект {@code digitalRubleWallet}. Как
     * проверить регистрацию в СБП вы можете изучить в разделе «Регистрация ЮЛ или ТСП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/registraciya-yul-i-tsp)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param customerCode Уникальный код клиента
     * @param bankCode БИК банка
     */
    public CustomerInfoResponseV3 getCustomerInfo(String customerCode, String bankCode) {
        return transport.request("GET", "/sbp/v1.0/customer/{customerCode}/{bankCode}")
                .path("customerCode", customerCode)
                .path("bankCode", bankCode)
                .unwrap("Data")
                .as(CustomerInfoResponseV3.class);
    }

    /**
     * Get Legal Entity. Метод возвращает данные юрлица в СБП по его {@code legalId}. Про регистрацию
     * юрлица — в разделе «Регистрация ЮЛ или ТСП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/registraciya-yul-i-tsp)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов)
     */
    public LegalEntity getLegalEntity(String legalId) {
        return transport.request("GET", "/sbp/v1.0/legal-entity/{legalId}")
                .path("legalId", legalId)
                .unwrap("Data")
                .as(LegalEntity.class);
    }

    /**
     * Register Legal Entity. Метод регистрирует юридическое лицо в СБП. Это первый шаг перед работой с
     * QR-кодами: в ответ приходит {@code legalId}, который нужен для регистрации торговых точек. С
     * чего начать работу с СБП вы можете прочитать в разделе «Регистрация ЮЛ или ТСП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/registraciya-yul-i-tsp)».
     *
     * <p>Требуемые разрешения: {@code EditSBPData}.
     *
     * @param request тело запроса
     */
    public String registerLegalEntity(CustomerCodeAndBankCode request) {
        return transport.request("POST", "/sbp/v1.0/register-sbp-legal-entity")
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data", "legalId")
                .as(String.class);
    }

    /**
     * Set Legal Entity Status. Метод меняет статус юрлица в СБП. Про работу с юрлицом в СБП — в
     * разделе «Регистрация ЮЛ или ТСП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/registraciya-yul-i-tsp)».
     *
     * <p>Требуемые разрешения: {@code EditSBPData}.
     *
     * @param legalId Идентификатор зарегистрированного юрлица в СБП (12 символов)
     * @param status Статус объекта. Например: "Active"
     */
    public Boolean setLegalEntityStatus(String legalId, StatusEnum status) {
        return transport.request("POST", "/sbp/v1.0/legal-entity/{legalId}")
                .path("legalId", legalId)
                .body(Envelope.wrap(status, "Data", "status"))
                .unwrap("Data", "result")
                .as(Boolean.class);
    }

}
