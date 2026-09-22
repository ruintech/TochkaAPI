package com.tochka.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.tochka.api.http.Json;
import com.tochka.api.model.AccountModel;
import com.tochka.api.model.ExternalTransationTypeEnum;
import com.tochka.api.model.Measure;
import com.tochka.api.model.PaymentForSignRequestModel;
import com.tochka.api.model.ReceiptItemModel;
import com.tochka.api.model.StatementInitReqModel;
import com.tochka.api.model.VatType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelSerializationTest {

    @Test
    void writesOnlyFilledFieldsWithOriginalNames() throws Exception {
        StatementInitReqModel statement = StatementInitReqModel.builder()
                .accountId("40817810802000000008/044525104")
                .startDateTime(LocalDate.of(2019, 1, 1))
                .endDateTime(LocalDate.of(2019, 1, 31))
                .build();

        JsonNode json = Json.mapper().valueToTree(statement);

        assertEquals("40817810802000000008/044525104", json.get("accountId").asText());
        assertEquals("2019-01-01", json.get("startDateTime").asText());
        assertEquals("2019-01-31", json.get("endDateTime").asText());
    }

    @Test
    void keepsMoneyAsBigDecimalWithoutPrecisionLoss() throws Exception {
        String payload = "{\"paymentAmount\": 700.33, \"paymentNumber\": 9195}";

        PaymentForSignRequestModel payment = Json.mapper().readValue(payload, PaymentForSignRequestModel.class);

        assertEquals(new BigDecimal("700.33"), payment.paymentAmount());
        assertEquals(9195, payment.paymentNumber());
    }

    @Test
    void readsAmountSentAsString() throws Exception {
        ReceiptItemModel item = Json.mapper().readValue(
                "{\"name\":\"Услуга\",\"amount\":\"1234.00\",\"quantity\":1}", ReceiptItemModel.class);

        assertEquals(new BigDecimal("1234.00"), item.amount());
        assertEquals("Услуга", item.name());
    }

    @Test
    void ignoresFieldsAddedByTheBankLater() throws Exception {
        AccountModel account = Json.mapper().readValue(
                "{\"accountId\":\"1/2\",\"totallyNewField\":42}", AccountModel.class);

        assertEquals("1/2", account.accountId());
    }

    @Test
    void mapsEnumsByWireValueIncludingCyrillicAndSlashes() {
        assertEquals("шт.", Measure.SHT.value());
        assertEquals(Measure.SHT, Measure.fromValue("шт."));
        assertEquals(ExternalTransationTypeEnum.MEMORIALNYY_ORDER,
                ExternalTransationTypeEnum.fromValue("Мемориальный ордер"));
        assertEquals(VatType.VAT22, VatType.fromValue("vat22"));
    }

    @Test
    void unknownEnumValueBecomesNullButParseThrows() {
        assertNull(VatType.fromValue("vat33"));
        assertThrows(IllegalArgumentException.class, () -> VatType.parse("vat33"));
    }

    @Test
    void unknownEnumValueInResponseDoesNotBreakDeserialization() throws Exception {
        ReceiptItemModel item = Json.mapper().readValue(
                "{\"name\":\"X\",\"amount\":1,\"quantity\":1,\"vatType\":\"vat99\"}", ReceiptItemModel.class);

        assertNull(item.vatType());
    }

    @Test
    void builderRoundTripsThroughToBuilder() {
        StatementInitReqModel original = StatementInitReqModel.builder()
                .accountId("acc")
                .startDateTime(LocalDate.of(2020, 5, 1))
                .build();

        StatementInitReqModel copy = original.toBuilder().accountId("other").build();

        assertEquals("other", copy.accountId());
        assertEquals(original.startDateTime(), copy.startDateTime());
    }

    @Test
    void nullFieldsAreOmittedFromRequestBody() throws Exception {
        String json = Json.mapper().writeValueAsString(
                StatementInitReqModel.builder().accountId("acc").build());

        assertTrue(json.contains("accountId"));
        assertFalse(json.contains("startDateTime"));
    }
}
