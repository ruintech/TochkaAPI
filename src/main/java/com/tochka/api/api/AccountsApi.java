package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Transport;
import com.tochka.api.model.AccountModel;
import java.util.List;

/**
 * Company accounts: list and details.
 *
 * <p>An instance is available from {@link com.tochka.api.TochkaClient}.
 */
public final class AccountsApi {

    private final Transport transport;

    public AccountsApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Get Account Info. Метод возвращает данные одного счёта по его {@code accountId}: статус, тип,
     * валюту и другие параметры. Подробнее о счетах — в разделе «Счета
     * (/docs/tochka-api/opisanie-metodov/scheta)».
     *
     * <p>Required permissions: {@code ReadAccountsBasic, ReadAccountsDetail}.
     *
     * @param accountId Уникальный и неизменный идентификатор счёта
     */
    public AccountModel getAccountInfo(String accountId) {
        return transport.request("GET", "/open-banking/v1.0/accounts/{accountId}")
                .path("accountId", accountId)
                .unwrap("Data")
                .as(AccountModel.class);
    }

    /**
     * Get Accounts List. Метод возвращает список счетов организации и их {@code accountId}. С него
     * удобно начинать: {@code accountId} нужен для запросов баланса, выписок и других операций по
     * счёту. Подробнее о счетах — в разделе «Счета (/docs/tochka-api/opisanie-metodov/scheta)».
     *
     * <p>Required permissions: {@code ReadAccountsBasic, ReadAccountsDetail}.
     */
    public List<AccountModel> getAccountsList() {
        return transport.request("GET", "/open-banking/v1.0/accounts")
                .unwrap("Data", "Account")
                .as(new TypeReference<List<AccountModel>>() {});
    }

}
