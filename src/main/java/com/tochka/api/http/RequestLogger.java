package com.tochka.api.http;

/**
 * Приёмник строк лога HTTP-обмена. Подключите свой логгер:
 * {@code .logger(message -> log.debug(message))}.
 */
@FunctionalInterface
public interface RequestLogger {

    void log(String message);

    /** Логгер, который ничего не делает. */
    static RequestLogger noop() {
        return message -> {
        };
    }

    /** Пишет в {@link System#out} — удобно при первичной отладке интеграции. */
    static RequestLogger stdout() {
        return message -> System.out.println("[tochka] " + message);
    }
}
