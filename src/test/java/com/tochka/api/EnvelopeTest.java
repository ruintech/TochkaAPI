package com.tochka.api;

import com.tochka.api.http.Envelope;
import com.tochka.api.http.Json;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnvelopeTest {

    @Test
    void wrapsBodyIntoSingleLevel() throws Exception {
        Map<String, Object> wrapped = Envelope.wrap(Map.of("amount", 10), "Data");

        assertEquals("{\"Data\":{\"amount\":10}}", Json.mapper().writeValueAsString(wrapped));
    }

    @Test
    void wrapsBodyIntoNestedLevels() throws Exception {
        Map<String, Object> wrapped = Envelope.wrap(Map.of("accountId", "1/2"), "Data", "Statement");

        assertEquals("{\"Data\":{\"Statement\":{\"accountId\":\"1/2\"}}}",
                Json.mapper().writeValueAsString(wrapped));
    }

    @Test
    void rejectsEmptyPath() {
        assertThrows(IllegalArgumentException.class, () -> Envelope.wrap("body"));
    }
}
