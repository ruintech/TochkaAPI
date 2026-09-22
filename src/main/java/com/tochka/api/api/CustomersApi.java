package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Transport;
import com.tochka.api.model.CustomerModel;
import java.util.List;

/**
 * Companies connected to your API access.
 *
 * <p>An instance is available from {@link com.tochka.api.TochkaClient}.
 */
public final class CustomersApi {

    private final Transport transport;

    public CustomersApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Get Customer Info. Метод возвращает данные конкретной компании по её {@code customerCode}:
     * наименование, ИНН, КПП и другие реквизиты. Подробнее о работе с клиентами — в разделе «Клиенты
     * (/docs/tochka-api/opisanie-metodov/klienty)».
     *
     * <p>Required permissions: {@code ReadCustomerData}.
     *
     * @param customerCode Идентификатор клиента
     */
    public CustomerModel getCustomerInfo(String customerCode) {
        return transport.request("GET", "/open-banking/v1.0/customers/{customerCode}")
                .path("customerCode", customerCode)
                .unwrap("Data")
                .as(CustomerModel.class);
    }

    /**
     * Get Customers List. Метод возвращает список компаний, к которым у вас есть доступ, и их {@code
     * customerCode}./nС этого метода обычно начинают работу: {@code customerCode} нужен в большинстве
     * других запросов. Берите значение из объекта с {@code customerType}: "Business". Что такое {@code
     * customerCode} и где он нужен — в разделе «Клиенты (/docs/tochka-api/opisanie-metodov/klienty)».
     *
     * <p>Required permissions: {@code ReadCustomerData}.
     */
    public List<CustomerModel> getCustomersList() {
        return transport.request("GET", "/open-banking/v1.0/customers")
                .unwrap("Data", "Customer")
                .as(new TypeReference<List<CustomerModel>>() {});
    }

}
