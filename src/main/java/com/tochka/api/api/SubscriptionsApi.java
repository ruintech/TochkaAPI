package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Envelope;
import com.tochka.api.http.Page;
import com.tochka.api.http.Transport;
import com.tochka.api.model.AcquiringCreateSubscriptionRequestModel;
import com.tochka.api.model.AcquiringCreateSubscriptionResponseModel;
import com.tochka.api.model.AcquiringCreateSubscriptionWithReceiptRequestModel;
import com.tochka.api.model.AcquiringCreateSubscriptionWithReceiptResponseModel;
import com.tochka.api.model.AcquiringSubscriptionListItemModel;
import com.tochka.api.model.AcquiringSubscriptionStatus;
import com.tochka.api.model.AcquiringSubscriptionStatusInput;
import java.math.BigDecimal;
import java.util.List;

/**
 * Подписки (рекуррентные платежи) по банковским картам.
 *
 * <p>Экземпляр доступен через {@link com.tochka.api.TochkaClient}.
 */
public final class SubscriptionsApi {

    private final Transport transport;

    public SubscriptionsApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Charge Subscription. Метод списывает деньги по подписке без графика. Вы сами вызываете его,
     * когда нужно провести очередное списание, и указываете сумму. Про подписки без графика — в
     * разделе «Подписки (/docs/tochka-api/opisanie-metodov/podpiski-rekurrentnye-platezhi)».
     *
     * <p>Требуемые разрешения: {@code MakeAcquiringOperation}.
     *
     * @param operationId Идентификатор подписки
     * @param amount Сумма платежа. Например: "1234.00"
     */
    public Boolean chargeSubscription(String operationId, BigDecimal amount) {
        return transport.request("POST", "/acquiring/v1.0/subscriptions/{operationId}/charge")
                .path("operationId", operationId)
                .body(Envelope.wrap(amount, "Data", "amount"))
                .unwrap("Data", "result")
                .as(Boolean.class);
    }

    /**
     * Create Subscription. Метод создаёт подписку — регулярные списания с карты покупателя. Списания
     * могут идти по графику или без него, когда вы сами инициируете каждое списание. Как работают
     * подписки с графиком и без — в разделе «Подписки
     * (/docs/tochka-api/opisanie-metodov/podpiski-rekurrentnye-platezhi)».
     *
     * <p>Требуемые разрешения: {@code MakeAcquiringOperation}.
     *
     * @param request тело запроса
     */
    public AcquiringCreateSubscriptionResponseModel createSubscription(AcquiringCreateSubscriptionRequestModel request) {
        return transport.request("POST", "/acquiring/v1.0/subscriptions")
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data")
                .as(AcquiringCreateSubscriptionResponseModel.class);
    }

    /**
     * Create Subscription With Receipt. Метод создаёт подписку и отправляет покупателю кассовый чек по
     * 54-ФЗ. Подходит, если по подписке нужно выдавать чеки. Про подписки и фискализацию — в разделе
     * «Подписки (/docs/tochka-api/opisanie-metodov/podpiski-rekurrentnye-platezhi)».
     *
     * <p>Требуемые разрешения: {@code MakeAcquiringOperation}.
     *
     * @param request тело запроса
     */
    public AcquiringCreateSubscriptionWithReceiptResponseModel createSubscriptionWithReceipt(AcquiringCreateSubscriptionWithReceiptRequestModel request) {
        return transport.request("POST", "/acquiring/v1.0/subscriptions_with_receipt")
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data")
                .as(AcquiringCreateSubscriptionWithReceiptResponseModel.class);
    }

    /**
     * Get Subscription List. Метод возвращает список всех ваших подписок с их данными и статусами.
     * Подробнее о подписках — в разделе «Подписки
     * (/docs/tochka-api/opisanie-metodov/podpiski-rekurrentnye-platezhi)».
     *
     * <p>Требуемые разрешения: {@code ReadAcquiringData}.
     *
     * @param customerCode Уникальный код клиента
     */
    public List<AcquiringSubscriptionListItemModel> getSubscriptionList(String customerCode) {
        return transport.request("GET", "/acquiring/v1.0/subscriptions")
                .query("customerCode", customerCode)
                .unwrap("Data", "Subscription")
                .as(new TypeReference<List<AcquiringSubscriptionListItemModel>>() {});
    }

    /** Необязательные параметры метода {@code GetSubscriptionList}. */
    public record GetSubscriptionListOptions(
            Integer page,
            Integer perPage,
            Boolean recurring) {

        public static Builder builder() {
            return new Builder();
        }

        /** Строитель {@link GetSubscriptionListOptions}. */
        public static final class Builder {

            private Integer page;
            private Integer perPage;
            private Boolean recurring;

            /** Номер страницы */
            public Builder page(Integer page) {
                this.page = page;
                return this;
            }

            /** Количество записей на странице */
            public Builder perPage(Integer perPage) {
                this.perPage = perPage;
                return this;
            }

            /** Получить платежи по рекуррентным подпискам */
            public Builder recurring(Boolean recurring) {
                this.recurring = recurring;
                return this;
            }

            public GetSubscriptionListOptions build() {
                return new GetSubscriptionListOptions(this.page, this.perPage, this.recurring);
            }
        }
    }

    /**
     * Get Subscription List. Метод возвращает список всех ваших подписок с их данными и статусами.
     * Подробнее о подписках — в разделе «Подписки
     * (/docs/tochka-api/opisanie-metodov/podpiski-rekurrentnye-platezhi)».
     *
     * <p>Требуемые разрешения: {@code ReadAcquiringData}.
     *
     * @param customerCode Уникальный код клиента
     * @param options необязательные параметры запроса; {@code null} — значения по умолчанию
     */
    public List<AcquiringSubscriptionListItemModel> getSubscriptionList(String customerCode, GetSubscriptionListOptions options) {
        return transport.request("GET", "/acquiring/v1.0/subscriptions")
                .query("customerCode", customerCode)
                .query("page", options == null ? null : options.page())
                .query("perPage", options == null ? null : options.perPage())
                .query("recurring", options == null ? null : options.recurring())
                .unwrap("Data", "Subscription")
                .as(new TypeReference<List<AcquiringSubscriptionListItemModel>>() {});
    }

    /**
     * Get Subscription List. Метод возвращает список всех ваших подписок с их данными и статусами.
     * Подробнее о подписках — в разделе «Подписки
     * (/docs/tochka-api/opisanie-metodov/podpiski-rekurrentnye-platezhi)».
     *
     * <p>Требуемые разрешения: {@code ReadAcquiringData}.
     *
     * @param customerCode Уникальный код клиента
     * @param options необязательные параметры запроса; {@code null} — значения по умолчанию
     */
    public Page<AcquiringSubscriptionListItemModel> getSubscriptionListPage(String customerCode, GetSubscriptionListOptions options) {
        return transport.request("GET", "/acquiring/v1.0/subscriptions")
                .query("customerCode", customerCode)
                .query("page", options == null ? null : options.page())
                .query("perPage", options == null ? null : options.perPage())
                .query("recurring", options == null ? null : options.recurring())
                .unwrap("Data", "Subscription")
                .asPage(new TypeReference<List<AcquiringSubscriptionListItemModel>>() {});
    }

    /**
     * Get Subscription List. Метод возвращает список всех ваших подписок с их данными и статусами.
     * Подробнее о подписках — в разделе «Подписки
     * (/docs/tochka-api/opisanie-metodov/podpiski-rekurrentnye-platezhi)».
     *
     * <p>Код клиента берётся из настроек клиента ({@code TochkaClient.builder().customerCode(...)}).
     *
     * <p>Требуемые разрешения: {@code ReadAcquiringData}.
     */
    public List<AcquiringSubscriptionListItemModel> getSubscriptionList() {
        return transport.request("GET", "/acquiring/v1.0/subscriptions")
                .query("customerCode", requireCustomerCode())
                .unwrap("Data", "Subscription")
                .as(new TypeReference<List<AcquiringSubscriptionListItemModel>>() {});
    }

    /**
     * Get Subscription List. Метод возвращает список всех ваших подписок с их данными и статусами.
     * Подробнее о подписках — в разделе «Подписки
     * (/docs/tochka-api/opisanie-metodov/podpiski-rekurrentnye-platezhi)».
     *
     * <p>Код клиента берётся из настроек клиента ({@code TochkaClient.builder().customerCode(...)}).
     *
     * <p>Требуемые разрешения: {@code ReadAcquiringData}.
     *
     * @param options необязательные параметры запроса; {@code null} — значения по умолчанию
     */
    public List<AcquiringSubscriptionListItemModel> getSubscriptionList(GetSubscriptionListOptions options) {
        return transport.request("GET", "/acquiring/v1.0/subscriptions")
                .query("customerCode", requireCustomerCode())
                .query("page", options == null ? null : options.page())
                .query("perPage", options == null ? null : options.perPage())
                .query("recurring", options == null ? null : options.recurring())
                .unwrap("Data", "Subscription")
                .as(new TypeReference<List<AcquiringSubscriptionListItemModel>>() {});
    }

    /**
     * Get Subscription List. Метод возвращает список всех ваших подписок с их данными и статусами.
     * Подробнее о подписках — в разделе «Подписки
     * (/docs/tochka-api/opisanie-metodov/podpiski-rekurrentnye-platezhi)».
     *
     * <p>Код клиента берётся из настроек клиента ({@code TochkaClient.builder().customerCode(...)}).
     *
     * <p>Требуемые разрешения: {@code ReadAcquiringData}.
     *
     * @param options необязательные параметры запроса; {@code null} — значения по умолчанию
     */
    public Page<AcquiringSubscriptionListItemModel> getSubscriptionListPage(GetSubscriptionListOptions options) {
        return transport.request("GET", "/acquiring/v1.0/subscriptions")
                .query("customerCode", requireCustomerCode())
                .query("page", options == null ? null : options.page())
                .query("perPage", options == null ? null : options.perPage())
                .query("recurring", options == null ? null : options.recurring())
                .unwrap("Data", "Subscription")
                .asPage(new TypeReference<List<AcquiringSubscriptionListItemModel>>() {});
    }

    /**
     * Get Subscription Status. Метод показывает текущий статус подписки: активна, приостановлена,
     * завершена и другие. Подробнее о подписках — в разделе «Подписки
     * (/docs/tochka-api/opisanie-metodov/podpiski-rekurrentnye-platezhi)».
     *
     * <p>Требуемые разрешения: {@code ReadAcquiringData}.
     *
     * @param operationId Идентификатор подписки
     */
    public AcquiringSubscriptionStatus getSubscriptionStatus(String operationId) {
        return transport.request("GET", "/acquiring/v1.0/subscriptions/{operationId}/status")
                .path("operationId", operationId)
                .unwrap("Data", "status")
                .as(AcquiringSubscriptionStatus.class);
    }

    /**
     * Set Subscription Status. Метод меняет статус подписки — например, отменяет её. Доступно только
     * для подписок с графиком списания. У подписок без графика статус изменить нельзя. Подробнее о
     * подписках — в разделе «Подписки
     * (/docs/tochka-api/opisanie-metodov/podpiski-rekurrentnye-platezhi)».
     *
     * <p>Требуемые разрешения: {@code MakeAcquiringOperation}.
     *
     * @param operationId Идентификатор подписки
     * @param status Статус подписки
     */
    public Boolean setSubscriptionStatus(String operationId, AcquiringSubscriptionStatusInput status) {
        return transport.request("POST", "/acquiring/v1.0/subscriptions/{operationId}/status")
                .path("operationId", operationId)
                .body(Envelope.wrap(status, "Data", "status"))
                .unwrap("Data", "result")
                .as(Boolean.class);
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
