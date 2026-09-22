package com.tochka.api.http;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Wraps a model into the API request envelope: request bodies are nested inside {@code Data},
 * and sometimes one level deeper, for example {@code {"Data": {"Statement": {...}}}}.
 *
 * <p>Service classes accept the meaningful model and wrap it with this helper, so the nested
 * envelopes never have to be built by hand.
 */
public final class Envelope {

    private Envelope() {
    }

    /**
     * Builds the nested object described by the path.
     *
     * @param body request body
     * @param path field names from the outermost to the innermost, e.g. {@code "Data", "Statement"}
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
