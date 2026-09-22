package com.tochka.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.tochka.api.exception.TochkaForbiddenException;
import com.tochka.api.exception.TochkaServerException;
import com.tochka.api.http.BinaryContent;
import com.tochka.api.http.Json;
import com.tochka.api.http.RetryPolicy;
import com.tochka.api.model.AccountModel;
import com.tochka.api.model.PaymentForSignListItemModel;
import com.tochka.api.model.InitStatementModel;
import com.tochka.api.model.StatementInitReqModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Exercises the transport against a local HTTP server, without touching the real API. */
class TransportTest {

    private HttpServer server;
    private final List<RecordedRequest> requests = new ArrayList<>();

    record RecordedRequest(String method, String path, String query, String authorization, String body) {
    }

    @BeforeEach
    void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.start();
    }

    @AfterEach
    void stopServer() {
        server.stop(0);
        requests.clear();
    }

    private void respond(String path, Consumer<HttpExchange> handler) {
        server.createContext(path, exchange -> {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            requests.add(new RecordedRequest(
                    exchange.getRequestMethod(),
                    exchange.getRequestURI().getPath(),
                    exchange.getRequestURI().getQuery(),
                    exchange.getRequestHeaders().getFirst("Authorization"),
                    body));
            handler.accept(exchange);
        });
    }

    private static void send(HttpExchange exchange, int status, String contentType, byte[] body) {
        try {
            exchange.getResponseHeaders().add("Content-Type", contentType);
            exchange.sendResponseHeaders(status, body.length);
            exchange.getResponseBody().write(body);
            exchange.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendJson(HttpExchange exchange, int status, String json) {
        send(exchange, status, "application/json", json.getBytes(StandardCharsets.UTF_8));
    }

    private TochkaClient client() {
        return client(builder -> {
        });
    }

    private TochkaClient client(Consumer<TochkaClient.Builder> customizer) {
        TochkaClient.Builder builder = TochkaClient.builder()
                .jwt("test-token")
                .environment(TochkaEnvironment.of("http://127.0.0.1:" + server.getAddress().getPort()))
                .customerCode("300123123")
                .requestTimeout(Duration.ofSeconds(5))
                .retryPolicy(RetryPolicy.none());
        customizer.accept(builder);
        return builder.build();
    }

    @Test
    void unwrapsDataEnvelopeAndSendsBearerToken() {
        respond("/open-banking/v1.0/accounts", exchange -> sendJson(exchange, 200, """
                {"Data": {"Account": [{"accountId": "40817810802000000008/044525104",
                 "accountType": "Business"}]},
                 "Links": {"self": "x"}, "Meta": {"totalPages": 1}}"""));

        List<AccountModel> accounts = client().accounts().getAccountsList();

        assertEquals(1, accounts.size());
        assertEquals("40817810802000000008/044525104", accounts.get(0).accountId());
        assertEquals("Bearer test-token", requests.get(0).authorization());
    }

    @Test
    void keepsSlashInsideAccountIdPathParameter() {
        respond("/open-banking/v1.0/accounts", exchange ->
                sendJson(exchange, 200, """
                        {"Data": {"Balance": []}, "Links": {"self": "x"}, "Meta": {"totalPages": 1}}"""));

        client().balances().getBalanceInfo("40817810802000000008/044525104");

        assertEquals("/open-banking/v1.0/accounts/40817810802000000008/044525104/balances",
                requests.get(0).path());
    }

    @Test
    void substitutesDefaultCustomerCodeIntoQuery() {
        respond("/payment/v1.0/for-sign", exchange -> sendJson(exchange, 200, """
                {"Data": {"Payment": [{"requestId": "req-1"}]},
                 "Links": {"self": "x"}, "Meta": {"totalPages": 1}}"""));

        List<PaymentForSignListItemModel> payments = client().payments().getPaymentForSignList();

        assertEquals(1, payments.size());
        assertEquals("customerCode=300123123", requests.get(0).query());
    }

    @Test
    void failsWithClearMessageWhenCustomerCodeIsNotConfigured() {
        TochkaClient client = TochkaClient.builder()
                .jwt("t")
                .environment(TochkaEnvironment.of("http://127.0.0.1:" + server.getAddress().getPort()))
                .build();

        IllegalStateException failure =
                assertThrows(IllegalStateException.class, () -> client.payments().getPaymentForSignList());

        assertTrue(failure.getMessage().contains("customerCode"));
    }

    @Test
    void wrapsRequestBodyIntoNestedEnvelope() {
        respond("/open-banking/v1.0/statements", exchange -> sendJson(exchange, 200, """
                {"Data": {"Statement": {"statementId": "23489", "status": "Created"}},
                 "Links": {"self": "x"}, "Meta": {"totalPages": 1}}"""));

        InitStatementModel statement = client().statements().initStatement(
                StatementInitReqModel.builder()
                        .accountId("40817810802000000008/044525104")
                        .startDateTime(LocalDate.of(2019, 1, 1))
                        .endDateTime(LocalDate.of(2019, 1, 31))
                        .build());

        assertEquals("23489", statement.statementId());
        assertEquals("POST", requests.get(0).method());
        assertTrue(requests.get(0).body().startsWith("{\"Data\":{\"Statement\":{"),
                "the request body must be wrapped: " + requests.get(0).body());
    }

    @Test
    void mapsForbiddenResponseToTypedException() {
        respond("/open-banking/v1.0/accounts", exchange -> sendJson(exchange, 403, """
                {"code": "403", "id": "c397b21a", "message": "Forbidden",
                 "Errors": [{"errorCode": "Something going wrong", "message": "Forbidden by consent",
                 "url": "https://developers.tochka.com/"}]}"""));

        TochkaForbiddenException failure =
                assertThrows(TochkaForbiddenException.class, () -> client().accounts().getAccountsList());

        assertEquals(403, failure.statusCode());
        assertEquals("c397b21a", failure.errorId().orElseThrow());
        assertEquals("Something going wrong", failure.firstErrorCode().orElseThrow());
        assertTrue(failure.getMessage().contains("Forbidden by consent"));
    }

    @Test
    void retriesIdempotentRequestAfterServerError() {
        AtomicInteger attempts = new AtomicInteger();
        respond("/open-banking/v1.0/accounts", exchange -> {
            if (attempts.incrementAndGet() == 1) {
                sendJson(exchange, 500, "{\"code\":\"500\",\"message\":\"boom\",\"Errors\":[]}");
            } else {
                sendJson(exchange, 200, """
                        {"Data": {"Account": []}, "Links": {"self": "x"}, "Meta": {"totalPages": 1}}""");
            }
        });

        List<AccountModel> accounts = client(builder -> builder.retryPolicy(
                RetryPolicy.builder().maxAttempts(3).initialBackoff(Duration.ofMillis(1)).build()))
                .accounts().getAccountsList();

        assertTrue(accounts.isEmpty());
        assertEquals(2, attempts.get());
    }

    @Test
    void doesNotRetryPostByDefault() {
        AtomicInteger attempts = new AtomicInteger();
        respond("/open-banking/v1.0/statements", exchange -> {
            attempts.incrementAndGet();
            sendJson(exchange, 500, "{\"code\":\"500\",\"message\":\"boom\",\"Errors\":[]}");
        });

        TochkaClient client = client(builder -> builder.retryPolicy(
                RetryPolicy.builder().maxAttempts(3).initialBackoff(Duration.ofMillis(1)).build()));

        assertThrows(TochkaServerException.class, () -> client.statements()
                .initStatement(StatementInitReqModel.builder().accountId("1/2").build()));
        assertEquals(1, attempts.get(), "retrying a POST could duplicate the operation");
    }

    @Test
    void readsPdfDocumentWithFileName() {
        respond("/invoice/v1.0/bills", exchange -> {
            exchange.getResponseHeaders().add("Content-Disposition", "attachment; filename=\"invoice.pdf\"");
            send(exchange, 200, "application/pdf", new byte[]{'%', 'P', 'D', 'F'});
        });

        BinaryContent invoice = client().invoices().getInvoice("300123123", "doc-1");

        assertEquals("application/pdf", invoice.contentType());
        assertEquals("invoice.pdf", invoice.fileNameIfPresent().orElseThrow());
        assertEquals(4, invoice.size());
    }

    @Test
    void readsPageWithLinksAndMeta() throws Exception {
        respond("/acquiring/v1.0/payments", exchange -> sendJson(exchange, 200, """
                {"Data": {"Operation": [{"operationId": "op-1", "amount": "10.00"}]},
                 "Links": {"self": "s", "next": "n"}, "Meta": {"totalPages": 7}}"""));

        var page = client().acquiring().getPaymentOperationListPage(null);

        assertEquals(1, page.size());
        assertEquals(7, page.totalPages());
        assertTrue(page.hasNext());
        assertEquals("op-1", Json.mapper().valueToTree(page.items().get(0)).get("operationId").asText());
    }
}
