package com.tochka.api;

import com.tochka.api.webhook.AcquiringInternetPaymentEvent;
import com.tochka.api.webhook.IncomingPaymentEvent;
import com.tochka.api.webhook.IncomingSbpB2bPaymentEvent;
import com.tochka.api.webhook.IncomingSbpPaymentEvent;
import com.tochka.api.webhook.UnknownWebhookEvent;
import com.tochka.api.webhook.WebhookEvent;
import com.tochka.api.webhook.WebhookVerificationException;
import com.tochka.api.webhook.WebhookVerifier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;
import java.time.LocalDate;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WebhookVerifierTest {

    private static KeyPair keyPair;
    private static WebhookVerifier verifier;

    @BeforeAll
    static void generateKeys() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        keyPair = generator.generateKeyPair();
        verifier = WebhookVerifier.builder().publicKey(keyPair.getPublic()).build();
    }

    private static String sign(String payloadJson, PrivateKey key) throws Exception {
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String header = encoder.encodeToString(
                "{\"typ\":\"JWT\",\"alg\":\"RS256\"}".getBytes(StandardCharsets.UTF_8));
        String payload = encoder.encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(key);
        signature.update((header + '.' + payload).getBytes(StandardCharsets.US_ASCII));
        return header + '.' + payload + '.' + encoder.encodeToString(signature.sign());
    }

    @Test
    void parsesIncomingPaymentEvent() throws Exception {
        String jwt = sign("""
                {"SidePayer": {"account": "40702810000000000001", "name": "ИП Тест", "amount": "40.0",
                  "currency": "RUB", "inn": "0000000000", "bankCode": "044525104"},
                 "SideRecipient": {"account": "40802810000000000002", "amount": "40.0", "currency": "RUB"},
                 "purpose": "Тестовое назначение платежа", "documentNumber": "00001",
                 "paymentId": "0000000010", "date": "2018-10-01",
                 "webhookType": "incomingPayment", "customerCode": "300123123"}""",
                keyPair.getPrivate());

        WebhookEvent event = verifier.verify(jwt);

        IncomingPaymentEvent incoming = assertInstanceOf(IncomingPaymentEvent.class, event);
        assertEquals("300123123", incoming.customerCode());
        assertEquals("0000000010", incoming.paymentId());
        assertEquals(LocalDate.of(2018, 10, 1), incoming.date());
        assertEquals(new BigDecimal("40.0"), incoming.amount());
        assertEquals("ИП Тест", incoming.payer().name());
    }

    @Test
    void parsesSbpPaymentEvent() throws Exception {
        String jwt = sign("""
                {"operationId": "A2200110026382010000533E625FCB3",
                 "qrcId": "AS10006DPRTEFPFS9HJ9SQSDSVRHJD3L", "amount": "0.33",
                 "paymentType": "sbpPayment", "payerMobileNumber": "+79991234567",
                 "payerName": "Иван Иванович И.", "merchantId": "MF0000000001",
                 "webhookType": "incomingSbpPayment", "customerCode": "300123123",
                 "refTransactionId": "cb1ef10b-39e9-4e55-aef9-d6bcc396ff0f"}""",
                keyPair.getPrivate());

        IncomingSbpPaymentEvent event = assertInstanceOf(IncomingSbpPaymentEvent.class, verifier.verify(jwt));

        assertTrue(event.isSbp());
        assertFalse(event.isDigitalRuble());
        assertEquals("cb1ef10b-39e9-4e55-aef9-d6bcc396ff0f", event.refTransactionId());
    }

    @Test
    void parsesDigitalRublePaymentEvent() throws Exception {
        String jwt = sign("""
                {"drClientId": "g.ru.cbrdc.prt.org.329672b4", "operationId": "49fffbcd-9706",
                 "paymentType": "drPayment", "amount": "101.00",
                 "webhookType": "incomingSbpPayment", "customerCode": "300123123"}""",
                keyPair.getPrivate());

        IncomingSbpPaymentEvent event = assertInstanceOf(IncomingSbpPaymentEvent.class, verifier.verify(jwt));

        assertTrue(event.isDigitalRuble());
        assertEquals("g.ru.cbrdc.prt.org.329672b4", event.drClientId());
    }

    @Test
    void parsesB2bAndAcquiringEvents() throws Exception {
        String b2b = sign("""
                {"qrcId": "AS10006", "amount": "0.33", "purpose": "Оплата по счёту",
                 "webhookType": "incomingSbpB2BPayment", "customerCode": "300123123"}""",
                keyPair.getPrivate());
        String acquiring = sign("""
                {"customerCode": "300123123", "amount": "0.33", "paymentType": "card",
                 "operationId": "beeac8a4", "status": "AUTHORIZED", "paymentLinkId": "12345-ab-cd",
                 "maskedPan": "4080******08941", "cardType": "MIR",
                 "webhookType": "acquiringInternetPayment"}""", keyPair.getPrivate());

        assertInstanceOf(IncomingSbpB2bPaymentEvent.class, verifier.verify(b2b));
        AcquiringInternetPaymentEvent event =
                assertInstanceOf(AcquiringInternetPaymentEvent.class, verifier.verify(acquiring));

        assertTrue(event.isAuthorized());
        assertFalse(event.isApproved());
        assertEquals("12345-ab-cd", event.paymentLinkId());
    }

    @Test
    void unknownEventTypeKeepsRawFields() throws Exception {
        String jwt = sign("""
                {"webhookType": "customWebhook", "customerCode": "300123123", "anything": 1}""",
                keyPair.getPrivate());

        UnknownWebhookEvent event = assertInstanceOf(UnknownWebhookEvent.class, verifier.verify(jwt));

        assertEquals("customWebhook", event.webhookType());
        assertEquals(1, event.fields().get("anything"));
    }

    @Test
    void rejectsTamperedPayload() throws Exception {
        String jwt = sign("""
                {"webhookType": "incomingPayment", "customerCode": "300123123",
                 "SideRecipient": {"amount": "1.00"}}""", keyPair.getPrivate());
        String[] parts = jwt.split("\\.");
        String forged = Base64.getUrlEncoder().withoutPadding().encodeToString("""
                {"webhookType": "incomingPayment", "customerCode": "300123123",
                 "SideRecipient": {"amount": "1000000.00"}}""".getBytes(StandardCharsets.UTF_8));

        WebhookVerificationException failure = assertThrows(WebhookVerificationException.class,
                () -> verifier.verify(parts[0] + '.' + forged + '.' + parts[2]));

        assertTrue(failure.getMessage().contains("не сходится"));
    }

    @Test
    void rejectsSignatureFromAnotherKey() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        String jwt = sign("""
                {"webhookType": "incomingPayment", "customerCode": "300123123"}""",
                generator.generateKeyPair().getPrivate());

        assertThrows(WebhookVerificationException.class, () -> verifier.verify(jwt));
    }

    @Test
    void rejectsMalformedToken() {
        assertThrows(WebhookVerificationException.class, () -> verifier.verify("not-a-jwt"));
        assertThrows(WebhookVerificationException.class, () -> verifier.verify(""));
    }

    @Test
    void rejectsUnsignedToken() {
        String none = Base64.getUrlEncoder().withoutPadding()
                .encodeToString("{\"alg\":\"none\"}".getBytes(StandardCharsets.UTF_8))
                + "." + Base64.getUrlEncoder().withoutPadding()
                .encodeToString("{\"webhookType\":\"incomingPayment\"}".getBytes(StandardCharsets.UTF_8))
                + ".AAAA";

        WebhookVerificationException failure =
                assertThrows(WebhookVerificationException.class, () -> verifier.verify(none));

        assertTrue(failure.getMessage().contains("алгоритм"));
    }

    @Test
    void verifiesRealWebhookWithKeyPublishedByTheBank() throws Exception {
        // The key published by the bank, and the incomingPayment sample from the documentation.
        WebhookVerifier bankVerifier = WebhookVerifier.builder()
                .publicKeyPem(readResource("/tochka_webhook_public_key.json"))
                .build();

        WebhookEvent event = bankVerifier.verify(readResource("/incoming_payment_sample.jwt"));

        IncomingPaymentEvent incoming = assertInstanceOf(IncomingPaymentEvent.class, event);
        assertEquals("300123123", incoming.customerCode());
        assertEquals("0000000000", incoming.paymentId());
        assertEquals(new BigDecimal("40.0"), incoming.amount());
        assertEquals("ООО Банк Точка", incoming.payer().bankName());
    }

    @Test
    void defaultsToKeyUrlPublishedByTheBank() {
        assertEquals("https://enter.tochka.com/doc/openapi/static/keys/public",
                WebhookVerifier.TOCHKA_PUBLIC_KEY_URL);
        assertNotNull(WebhookVerifier.usingTochkaPublicKey());
    }

    private static String readResource(String name) throws Exception {
        try (var stream = WebhookVerifierTest.class.getResourceAsStream(name)) {
            assertNotNull(stream, "missing resource " + name);
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8).trim();
        }
    }

    @Test
    void acceptsPublicKeyInPemFormat() throws Exception {
        String pem = "-----BEGIN PUBLIC KEY-----\n"
                + Base64.getMimeEncoder(64, "\n".getBytes(StandardCharsets.US_ASCII))
                        .encodeToString(keyPair.getPublic().getEncoded())
                + "\n-----END PUBLIC KEY-----\n";
        WebhookVerifier pemVerifier = WebhookVerifier.builder().publicKeyPem(pem).build();

        String jwt = sign("""
                {"webhookType": "incomingSbpB2BPayment", "customerCode": "300123123", "amount": "1.00"}""",
                keyPair.getPrivate());

        assertEquals("300123123", pemVerifier.verify(jwt).customerCode());
    }
}
