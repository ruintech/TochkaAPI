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

/** Настроенный {@link ObjectMapper} для моделей API Точки. */
public final class Json {

    private static final ObjectMapper MAPPER = createMapper();

    private Json() {
    }

    /**
     * Маппер, которым клиент сериализует запросы и разбирает ответы.
     *
     * <p>Отличия от умолчаний Jackson: неизвестные поля ответа игнорируются (банк добавляет
     * поля без смены версии API), {@code null}-поля не попадают в тело запроса, даты пишутся
     * строками ISO-8601, а {@code date-time} читается терпимо — часть полей API отдаёт в виде
     * чистой даты.
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
     * Читает {@link OffsetDateTime} и из полного ISO-8601, и из чистой даты вида
     * {@code 2018-03-29} — так, например, приходит {@code paymentDate} в списке платежей на подпись.
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
