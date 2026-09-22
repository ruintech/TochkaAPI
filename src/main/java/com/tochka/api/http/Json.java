package com.tochka.api.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

/** The {@link ObjectMapper} configured for Tochka API models. */
public final class Json {

    private static final ObjectMapper MAPPER = createMapper();

    private Json() {
    }

    /**
     * The mapper the client uses to serialize requests and parse responses.
     *
     * <p>Differences from the Jackson defaults: unknown response fields are ignored (the bank
     * adds fields without bumping the API version), {@code null} fields are left out of request
     * bodies, dates are written as ISO-8601 strings, and {@code date-time} is parsed leniently —
     * some fields arrive as a plain date.
     */
    public static ObjectMapper mapper() {
        return MAPPER;
    }

    private static ObjectMapper createMapper() {
        SimpleModule lenientDates = new SimpleModule("tochka-lenient-dates");
        lenientDates.addDeserializer(OffsetDateTime.class, new LenientOffsetDateTimeDeserializer());

        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(lenientDates)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    }

    /**
     * Reads {@link OffsetDateTime} both from a full ISO-8601 value and from a plain date such as
     * {@code 2018-03-29} — that is how {@code paymentDate} arrives in the list of payments
     * awaiting signature.
     */
    private static final class LenientOffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

        @Override
        public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            String value = parser.getText();
            if (value == null || value.isBlank()) {
                return null;
            }
            String trimmed = value.trim();
            try {
                return OffsetDateTime.parse(trimmed);
            } catch (DateTimeParseException first) {
                try {
                    return LocalDate.parse(trimmed).atStartOfDay().atOffset(ZoneOffset.UTC);
                } catch (DateTimeParseException second) {
                    throw new IOException("Не удалось разобрать дату-время: " + trimmed, first);
                }
            }
        }
    }
}
