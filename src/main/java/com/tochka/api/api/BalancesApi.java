package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Transport;
import com.tochka.api.model.BalanceModel;
import com.tochka.api.model.CardTransactionModel;
import java.util.List;

/**
 * Остатки по счетам и авторизованные карточные операции.
 *
 * <p>Экземпляр доступен через {@link com.tochka.api.TochkaClient}.
 */
public final class BalancesApi {

    private final Transport transport;

    public BalancesApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Get Authorized Card Transactions. Метод возвращает карточные операции по счёту, которые уже
     * совершены, но ещё не отражены в выписке. Когда покупатель платит картой или снимает наличные,
     * сумма сразу попадает в резерв и становится недоступной, а в выписку операция приходит только
     * после окончательного списания — обычно через несколько дней. Так вы видите движение по карте в
     * реальном времени, не дожидаясь выписки. Как устроен резерв и карточные операции — в разделе
     * «Баланс счёта (/docs/tochka-api/opisanie-metodov/balans-schyota)».
     *
     * <p>Требуемые разрешения: {@code ReadBalances}.
     *
     * @param accountId Идентификатор счета
     */
    public List<CardTransactionModel> getAuthorizedCardTransactions(String accountId) {
        return transport.request("GET", "/open-banking/v1.0/accounts/{accountId}/authorized-card-transactions")
                .path("accountId", accountId)
                .unwrap("Data", "Transactions")
                .as(new TypeReference<List<CardTransactionModel>>() {});
    }

    /**
     * Get Balance Info. Метод возвращает остатки по одному счёту: сколько денег на счёте, сколько
     * доступно к трате и сколько заблокировано. Нужен, чтобы проверить баланс перед платежом или
     * показать его пользователю. Счёт указывается в параметре {@code accountId}. Про типы баланса и
     * то, как посчитать доступную сумму — в разделе «Баланс счёта
     * (/docs/tochka-api/opisanie-metodov/balans-schyota)».
     *
     * <p>Требуемые разрешения: {@code ReadBalances}.
     *
     * @param accountId Идентификатор счета
     */
    public List<BalanceModel> getBalanceInfo(String accountId) {
        return transport.request("GET", "/open-banking/v1.0/accounts/{accountId}/balances")
                .path("accountId", accountId)
                .unwrap("Data", "Balance")
                .as(new TypeReference<List<BalanceModel>>() {});
    }

    /**
     * Get Balances List. Метод возвращает остатки сразу по всем счетам организации — по каждому
     * отдельным элементом. Удобен, когда счетов несколько и нужно получить их балансы одним запросом,
     * а не вызывать метод для каждого счёта. Про типы баланса и формат ответа — в разделе «Баланс
     * счёта (/docs/tochka-api/opisanie-metodov/balans-schyota)»
     *
     * <p>Требуемые разрешения: {@code ReadBalances}.
     */
    public List<BalanceModel> getBalancesList() {
        return transport.request("GET", "/open-banking/v1.0/balances")
                .unwrap("Data", "Balance")
                .as(new TypeReference<List<BalanceModel>>() {});
    }

}
