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
 * Entry point to the Tochka Bank API.
 *
 * <p>The client is thread-safe and meant to exist once per application: it owns a shared
 * {@link HttpClient} with its connection pool.
 *
 * <p>The Ministry of Digital Development certificates that {@code enter.tochka.com} runs on are
 * trusted out of the box — TLS needs no separate setup.
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
 * <p>For debugging without production data there is the sandbox: {@link #sandbox()}.
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
     * A sandbox client with the test token. Sandbox answers are fixed and a payment link created
     * there cannot be paid — it exists to work out request formats.
     *
     * <p>Test identifiers: {@code customerCode = 1234567ab},
     * {@code accountId = 12345810901234567890/044525104}, {@code merchantId = 200000000001097}.
     */
    public static TochkaClient sandbox() {
        return builder()
                .environment(TochkaEnvironment.SANDBOX)
                .jwt(TochkaEnvironment.SANDBOX_TOKEN)
                .customerCode("1234567ab")
                .build();
    }

    /** Company accounts. */
    public AccountsApi accounts() {
        return accounts;
    }

    /** Account balances and authorized card transactions. */
    public BalancesApi balances() {
        return balances;
    }

    /** Account statements. */
    public StatementsApi statements() {
        return statements;
    }

    /** Companies connected to this access. */
    public CustomersApi customers() {
        return customers;
    }

    /** Payment orders (outgoing payments). */
    public PaymentsApi payments() {
        return payments;
    }

    /** Internet acquiring: payment links, refunds, registry, retailers. */
    public AcquiringApi acquiring() {
        return acquiring;
    }

    /** Subscriptions (recurring payments). */
    public SubscriptionsApi subscriptions() {
        return subscriptions;
    }

    /** Invoices. */
    public InvoicesApi invoices() {
        return invoices;
    }

    /** Closing documents: acts, packing lists, invoices, UPD. */
    public ClosingDocumentsApi closingDocuments() {
        return closingDocuments;
    }

    /** Webhook management. */
    public WebhooksApi webhooks() {
        return webhooks;
    }

    /** Consents for OAuth 2.0. */
    public ConsentsApi consents() {
        return consents;
    }

    /** SBP: legal entities. */
    public SbpLegalEntitiesApi sbpLegalEntities() {
        return sbpLegalEntities;
    }

    /** SBP: merchants. */
    public SbpMerchantsApi sbpMerchants() {
        return sbpMerchants;
    }

    /** SBP: static and dynamic QR codes. */
    public SbpQrCodesApi sbpQrCodes() {
        return sbpQrCodes;
    }

    /** SBP: cashbox QR codes. */
    public SbpCashboxQrCodesApi sbpCashboxQrCodes() {
        return sbpCashboxQrCodes;
    }

    /** SBP: B2B QR codes. */
    public SbpB2bQrCodesApi sbpB2bQrCodes() {
        return sbpB2bQrCodes;
    }

    /** SBP: refunds. */
    public SbpRefundsApi sbpRefunds() {
        return sbpRefunds;
    }

    /**
     * The transport for hand-made requests — useful when the bank ships a method this version of
     * the library does not cover yet.
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

    /** Builder for {@link TochkaClient}. */
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

        /** A JWT key issued in the internet bank. */
        public Builder jwt(String token) {
            this.authorization = Authorization.jwt(token);
            return this;
        }

        /** Any token source — {@link com.tochka.api.auth.OAuth2Authorization}, for example. */
        public Builder authorization(Authorization authorization) {
            this.authorization = authorization;
            return this;
        }

        /** Production or sandbox; production by default. */
        public Builder environment(TochkaEnvironment environment) {
            this.environment = Objects.requireNonNull(environment, "environment");
            return this;
        }

        /**
         * Default customer code: used by methods that require one when it is not passed
         * explicitly. Look it up with {@code customers().getCustomersList()} and take the value
         * from the object with {@code customerType: "Business"}.
         */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /**
         * A ready HTTP client. When set, {@link #sslContext(SSLContext)},
         * {@link #trustRussianCa(boolean)} and {@link #connectTimeout(Duration)} are ignored —
         * configure them on that client instead. Do not forget the ministry certificates:
         * {@code HttpClient.newBuilder().sslContext(RussianTrustedCa.sslContext())}.
         */
        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * A custom SSL context instead of the built-in one. Normally unnecessary: the client
         * already uses {@link RussianTrustedCa#sslContext()} by default.
         */
        public Builder sslContext(SSLContext sslContext) {
            this.sslContext = sslContext;
            return this;
        }

        /**
         * Turns off the built-in trust for the ministry certificates and leaves the standard JVM
         * trust store in place. Makes sense only when those certificates are already in
         * {@code cacerts} or in a truststore given via {@code -Djavax.net.ssl.trustStore}.
         */
        public Builder trustRussianCa(boolean trustRussianCa) {
            this.trustRussianCa = trustRussianCa;
            return this;
        }

        /** Connection timeout; 15 seconds by default. */
        public Builder connectTimeout(Duration connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /** Timeout of the whole request; 60 seconds by default. */
        public Builder requestTimeout(Duration requestTimeout) {
            this.requestTimeout = requestTimeout;
            return this;
        }

        /** Retry policy; {@link RetryPolicy#defaults()} by default. */
        public Builder retryPolicy(RetryPolicy retryPolicy) {
            this.retryPolicy = Objects.requireNonNull(retryPolicy, "retryPolicy");
            return this;
        }

        /** Logging verbosity; {@link LogLevel#NONE} by default. */
        public Builder logLevel(LogLevel logLevel) {
            this.logLevel = Objects.requireNonNull(logLevel, "logLevel");
            return this;
        }

        /** Where the log goes; nowhere by default. */
        public Builder logger(RequestLogger logger) {
            this.logger = Objects.requireNonNull(logger, "logger");
            return this;
        }

        /** Value of the {@code User-Agent} header. */
        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public TochkaClient build() {
            return new TochkaClient(this);
        }
    }
}
