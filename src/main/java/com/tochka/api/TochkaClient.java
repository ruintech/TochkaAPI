package com.tochka.api;

import com.tochka.api.api.AccountsApi;
import com.tochka.api.api.AcquiringApi;
import com.tochka.api.api.BalancesApi;
import com.tochka.api.api.ClosingDocumentsApi;
import com.tochka.api.api.ConsentsApi;
import com.tochka.api.api.CustomersApi;
import com.tochka.api.api.InvoicesApi;
import com.tochka.api.api.PaymentsApi;
import com.tochka.api.api.SbpB2bQrCodesApi;
import com.tochka.api.api.SbpCashboxQrCodesApi;
import com.tochka.api.api.SbpLegalEntitiesApi;
import com.tochka.api.api.SbpMerchantsApi;
import com.tochka.api.api.SbpQrCodesApi;
import com.tochka.api.api.SbpRefundsApi;
import com.tochka.api.api.StatementsApi;
import com.tochka.api.api.SubscriptionsApi;
import com.tochka.api.api.WebhooksApi;
import com.tochka.api.auth.Authorization;
import com.tochka.api.http.LogLevel;
import com.tochka.api.http.RequestLogger;
import com.tochka.api.http.RetryPolicy;
import com.tochka.api.http.Transport;
import com.tochka.api.tls.RussianTrustedCa;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Objects;

/**
 * Точка входа в API Точка Банка.
 *
 * <p>Клиент потокобезопасен и рассчитан на то, что в приложении он один: внутри живёт общий
 * {@link HttpClient} с пулом соединений.
 *
 * <p>Сертификатам Минцифры, на которых работает {@code enter.tochka.com}, клиент доверяет из
 * коробки — настраивать TLS отдельно не нужно.
 *
 * <pre>{@code
 * TochkaClient client = TochkaClient.builder()
 *         .jwt(System.getenv("TOCHKA_TOKEN"))
 *         .customerCode("300123123")
 *         .build();
 *
 * List<AccountModel> accounts = client.accounts().getAccountsList();
 * List<BalanceModel> balances = client.balances().getBalancesList();
 * }</pre>
 *
 * <p>Для отладки без боевых данных есть песочница: {@link #sandbox()}.
 */
public final class TochkaClient {

    private final Transport transport;

    private final AccountsApi accounts;
    private final BalancesApi balances;
    private final StatementsApi statements;
    private final CustomersApi customers;
    private final PaymentsApi payments;
    private final AcquiringApi acquiring;
    private final SubscriptionsApi subscriptions;
    private final InvoicesApi invoices;
    private final ClosingDocumentsApi closingDocuments;
    private final WebhooksApi webhooks;
    private final ConsentsApi consents;
    private final SbpLegalEntitiesApi sbpLegalEntities;
    private final SbpMerchantsApi sbpMerchants;
    private final SbpQrCodesApi sbpQrCodes;
    private final SbpCashboxQrCodesApi sbpCashboxQrCodes;
    private final SbpB2bQrCodesApi sbpB2bQrCodes;
    private final SbpRefundsApi sbpRefunds;

    private TochkaClient(Builder builder) {
        Authorization authorization = Objects.requireNonNull(builder.authorization,
                "Не задана авторизация: вызовите jwt(...) или authorization(...)");

        HttpClient httpClient = builder.httpClient;
        if (httpClient == null) {
            HttpClient.Builder clientBuilder = HttpClient.newBuilder()
                    .connectTimeout(builder.connectTimeout)
                    .followRedirects(HttpClient.Redirect.NORMAL);
            SSLContext sslContext = builder.sslContext;
            if (sslContext == null && builder.trustRussianCa) {
                sslContext = RussianTrustedCa.sslContext();
            }
            if (sslContext != null) {
                clientBuilder.sslContext(sslContext);
            }
            httpClient = clientBuilder.build();
        }

        this.transport = new Transport(
                builder.environment.baseUri(),
                httpClient,
                authorization,
                builder.customerCode,
                builder.requestTimeout,
                builder.retryPolicy,
                builder.logLevel,
                builder.logger,
                builder.userAgent);

        this.accounts = new AccountsApi(transport);
        this.balances = new BalancesApi(transport);
        this.statements = new StatementsApi(transport);
        this.customers = new CustomersApi(transport);
        this.payments = new PaymentsApi(transport);
        this.acquiring = new AcquiringApi(transport);
        this.subscriptions = new SubscriptionsApi(transport);
        this.invoices = new InvoicesApi(transport);
        this.closingDocuments = new ClosingDocumentsApi(transport);
        this.webhooks = new WebhooksApi(transport);
        this.consents = new ConsentsApi(transport);
        this.sbpLegalEntities = new SbpLegalEntitiesApi(transport);
        this.sbpMerchants = new SbpMerchantsApi(transport);
        this.sbpQrCodes = new SbpQrCodesApi(transport);
        this.sbpCashboxQrCodes = new SbpCashboxQrCodesApi(transport);
        this.sbpB2bQrCodes = new SbpB2bQrCodesApi(transport);
        this.sbpRefunds = new SbpRefundsApi(transport);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Клиент песочницы с тестовым токеном. Ответы в песочнице фиксированные, оплатить
     * сформированную платёжную ссылку нельзя — она нужна для отладки формата запросов.
     *
     * <p>Тестовые идентификаторы: {@code customerCode = 1234567ab},
     * {@code accountId = 12345810901234567890/044525104}, {@code merchantId = 200000000001097}.
     */
    public static TochkaClient sandbox() {
        return builder()
                .environment(TochkaEnvironment.SANDBOX)
                .jwt(TochkaEnvironment.SANDBOX_TOKEN)
                .customerCode("1234567ab")
                .build();
    }

    /** Счета компании. */
    public AccountsApi accounts() {
        return accounts;
    }

    /** Остатки по счетам и авторизованные карточные операции. */
    public BalancesApi balances() {
        return balances;
    }

    /** Выписки по счёту. */
    public StatementsApi statements() {
        return statements;
    }

    /** Компании, подключённые к доступу. */
    public CustomersApi customers() {
        return customers;
    }

    /** Платёжные поручения (исходящие платежи). */
    public PaymentsApi payments() {
        return payments;
    }

    /** Интернет-эквайринг: платёжные ссылки, возвраты, реестр, торговые точки. */
    public AcquiringApi acquiring() {
        return acquiring;
    }

    /** Подписки (рекуррентные платежи). */
    public SubscriptionsApi subscriptions() {
        return subscriptions;
    }

    /** Счета на оплату. */
    public InvoicesApi invoices() {
        return invoices;
    }

    /** Закрывающие документы: акты, накладные, счета-фактуры, УПД. */
    public ClosingDocumentsApi closingDocuments() {
        return closingDocuments;
    }

    /** Управление вебхуками. */
    public WebhooksApi webhooks() {
        return webhooks;
    }

    /** Списки разрешений для OAuth 2.0. */
    public ConsentsApi consents() {
        return consents;
    }

    /** СБП: юридические лица. */
    public SbpLegalEntitiesApi sbpLegalEntities() {
        return sbpLegalEntities;
    }

    /** СБП: торговые точки (ТСП). */
    public SbpMerchantsApi sbpMerchants() {
        return sbpMerchants;
    }

    /** СБП: статические и динамические QR-коды. */
    public SbpQrCodesApi sbpQrCodes() {
        return sbpQrCodes;
    }

    /** СБП: кассовые QR-коды. */
    public SbpCashboxQrCodesApi sbpCashboxQrCodes() {
        return sbpCashboxQrCodes;
    }

    /** СБП: B2B QR-коды. */
    public SbpB2bQrCodesApi sbpB2bQrCodes() {
        return sbpB2bQrCodes;
    }

    /** СБП: возвраты. */
    public SbpRefundsApi sbpRefunds() {
        return sbpRefunds;
    }

    /**
     * Транспорт для ручных запросов — пригодится, если банк выпустил метод, которого ещё нет
     * в этой версии библиотеки.
     *
     * <pre>{@code
     * JsonNode raw = client.transport()
     *         .request("GET", "/open-banking/v1.0/accounts")
     *         .as(JsonNode.class);
     * }</pre>
     */
    public Transport transport() {
        return transport;
    }

    /** Строитель {@link TochkaClient}. */
    public static final class Builder {
        private Authorization authorization;
        private TochkaEnvironment environment = TochkaEnvironment.PRODUCTION;
        private String customerCode;
        private HttpClient httpClient;
        private SSLContext sslContext;
        private boolean trustRussianCa = true;
        private Duration connectTimeout = Duration.ofSeconds(15);
        private Duration requestTimeout = Duration.ofSeconds(60);
        private RetryPolicy retryPolicy = RetryPolicy.defaults();
        private LogLevel logLevel = LogLevel.NONE;
        private RequestLogger logger = RequestLogger.noop();
        private String userAgent = "tochka-api-java";

        /** JWT-ключ из интернет-банка. */
        public Builder jwt(String token) {
            this.authorization = Authorization.jwt(token);
            return this;
        }

        /** Произвольный источник токена — например {@link com.tochka.api.auth.OAuth2Authorization}. */
        public Builder authorization(Authorization authorization) {
            this.authorization = authorization;
            return this;
        }

        /** Боевой слой или песочница; по умолчанию боевой. */
        public Builder environment(TochkaEnvironment environment) {
            this.environment = Objects.requireNonNull(environment, "environment");
            return this;
        }

        /**
         * Код клиента по умолчанию: подставляется в методы, где он обязателен, если вы не передали
         * его явно. Узнать код можно методом {@code customers().getCustomersList()} — берите
         * значение из объекта с {@code customerType: "Business"}.
         */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /**
         * Готовый HTTP-клиент. Если задан, {@link #sslContext(SSLContext)},
         * {@link #trustRussianCa(boolean)} и {@link #connectTimeout(Duration)} не применяются —
         * настройте их в самом клиенте. Не забудьте про сертификаты Минцифры:
         * {@code HttpClient.newBuilder().sslContext(RussianTrustedCa.sslContext())}.
         */
        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Свой SSL-контекст вместо встроенного. Задавать его не нужно: по умолчанию клиент уже
         * использует {@link RussianTrustedCa#sslContext()}.
         */
        public Builder sslContext(SSLContext sslContext) {
            this.sslContext = sslContext;
            return this;
        }

        /**
         * Отключает встроенное доверие сертификатам Минцифры и оставляет стандартное хранилище
         * JVM. Имеет смысл, только если сертификаты уже добавлены в {@code cacerts} или в
         * truststore, заданный через {@code -Djavax.net.ssl.trustStore}.
         */
        public Builder trustRussianCa(boolean trustRussianCa) {
            this.trustRussianCa = trustRussianCa;
            return this;
        }

        /** Таймаут установки соединения; по умолчанию 15 секунд. */
        public Builder connectTimeout(Duration connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /** Таймаут запроса целиком; по умолчанию 60 секунд. */
        public Builder requestTimeout(Duration requestTimeout) {
            this.requestTimeout = requestTimeout;
            return this;
        }

        /** Политика повторов; по умолчанию {@link RetryPolicy#defaults()}. */
        public Builder retryPolicy(RetryPolicy retryPolicy) {
            this.retryPolicy = Objects.requireNonNull(retryPolicy, "retryPolicy");
            return this;
        }

        /** Подробность логирования; по умолчанию {@link LogLevel#NONE}. */
        public Builder logLevel(LogLevel logLevel) {
            this.logLevel = Objects.requireNonNull(logLevel, "logLevel");
            return this;
        }

        /** Куда писать лог; по умолчанию никуда. */
        public Builder logger(RequestLogger logger) {
            this.logger = Objects.requireNonNull(logger, "logger");
            return this;
        }

        /** Значение заголовка {@code User-Agent}. */
        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public TochkaClient build() {
            return new TochkaClient(this);
        }
    }
}
