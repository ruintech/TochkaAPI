package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Envelope;
import com.tochka.api.http.Transport;
import com.tochka.api.model.InitStatementModel;
import com.tochka.api.model.StatementInitReqModel;
import com.tochka.api.model.StatementModel;
import java.util.List;

/**
 * Account statements: ordering, retrieval and listing.
 *
 * <p>An instance is available from {@link com.tochka.api.TochkaClient}.
 */
public final class StatementsApi {

    private final Transport transport;

    public StatementsApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Get Statement. Метод возвращает готовую выписку по её statementId. Пока выписка формируется, по
     * нему же можно отследить статус: - *Created* — запрос создан - *Processing* — в обработке -
     * *Ready* — выписка готова В выписку попадают только операции в финальном статусе. Как заказать
     * выписку и что в ней приходит — в разделе «Выписки (/docs/tochka-api/opisanie-metodov/vypiski)».
     *
     * <p>Required permissions: {@code ReadStatements}.
     *
     * @param accountId Идентификатор счета
     * @param statementId Идентификатор выписки
     */
    public List<StatementModel> getStatement(String accountId, String statementId) {
        return transport.request("GET", "/open-banking/v1.0/accounts/{accountId}/statements/{statementId}")
                .path("accountId", accountId)
                .path("statementId", statementId)
                .unwrap("Data", "Statement")
                .as(new TypeReference<List<StatementModel>>() {});
    }

    /**
     * Get Statements List. Метод возвращает список выписок, которые вы заказывали, с их статусами.
     * Помогает найти нужную выписку и понять, готова ли она. Как работать с выписками — в разделе
     * «Выписки (/docs/tochka-api/opisanie-metodov/vypiski)».
     *
     * <p>Required permissions: {@code ReadStatements}.
     */
    public List<StatementModel> getStatementsList() {
        return transport.request("GET", "/open-banking/v1.0/statements")
                .unwrap("Data", "Statement")
                .as(new TypeReference<List<StatementModel>>() {});
    }

    /** Optional parameters of {@code GetStatementsList}. */
    public record GetStatementsListOptions(
            Integer limit) {

        public static Builder builder() {
            return new Builder();
        }

        /** Builder for {@link GetStatementsListOptions}. */
        public static final class Builder {

            private Integer limit;

            /** Максимальное количество выписок в ответе */
            public Builder limit(Integer limit) {
                this.limit = limit;
                return this;
            }

            public GetStatementsListOptions build() {
                return new GetStatementsListOptions(this.limit);
            }
        }
    }

    /**
     * Get Statements List. Метод возвращает список выписок, которые вы заказывали, с их статусами.
     * Помогает найти нужную выписку и понять, готова ли она. Как работать с выписками — в разделе
     * «Выписки (/docs/tochka-api/opisanie-metodov/vypiski)».
     *
     * <p>Required permissions: {@code ReadStatements}.
     *
     * @param options optional query parameters; {@code null} means defaults
     */
    public List<StatementModel> getStatementsList(GetStatementsListOptions options) {
        return transport.request("GET", "/open-banking/v1.0/statements")
                .query("limit", options == null ? null : options.limit())
                .unwrap("Data", "Statement")
                .as(new TypeReference<List<StatementModel>>() {});
    }

    /**
     * Init Statement. Метод запускает формирование выписки по счёту за нужный период. Выписка
     * готовится асинхронно: этот метод только ставит её в очередь и возвращает {@code statementId}, по
     * которому потом можно забрать готовый документ. Как заказать и получить выписку — в разделе
     * «Выписки (/docs/tochka-api/opisanie-metodov/vypiski)».
     *
     * <p>Required permissions: {@code ReadStatements}.
     *
     * @param statement request body
     */
    public InitStatementModel initStatement(StatementInitReqModel statement) {
        return transport.request("POST", "/open-banking/v1.0/statements")
                .body(Envelope.wrap(statement, "Data", "Statement"))
                .unwrap("Data", "Statement")
                .as(InitStatementModel.class);
    }

}
