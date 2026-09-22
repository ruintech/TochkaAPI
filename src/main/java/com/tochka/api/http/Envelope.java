package com.tochka.api.http;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Оборачивает модель в конверт запроса API: тела методов вложены в объект {@code Data},
 * иногда — ещё на уровень глубже, например {@code {"Data": {"Statement": {...}}}}.
 *
 * <p>Классы сервисов принимают сразу содержательную модель и оборачивают её этим помощником,
 * поэтому вручную собирать вложенные обёртки не нужно.
 */
public final class Envelope {

    private Envelope() {
    }

    /**
     * Строит вложенный объект по пути.
     *
     * @param body тело запроса
     * @param path имена полей от внешнего к внутреннему, например {@code "Data", "Statement"}
     */
    public static Map<String, Object> wrap(Object body, String... path) {
        if (path.length == 0) {
            throw new IllegalArgumentException("Путь обёртки не может быть пустым");
        }
        Map<String, Object> result = null;
        Object current = body;
        for (int i = path.length - 1; i >= 0; i--) {
            Map<String, Object> level = new LinkedHashMap<>();
            level.put(path[i], current);
            current = level;
            result = level;
        }
        return result;
    }
}
