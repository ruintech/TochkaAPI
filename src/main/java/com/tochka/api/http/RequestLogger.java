package com.tochka.api.http;

/**
 * Sink for HTTP exchange log lines. Plug in your own logger:
 * {@code .logger(message -> log.debug(message))}.
 */
@FunctionalInterface
public interface RequestLogger {

    void log(String message);

    /** A logger that does nothing. */
    static RequestLogger noop() {
        return message -> {
        };
    }

    /** Writes to {@link System#out} — handy while bringing an integration up. */
    static RequestLogger stdout() {
        return message -> System.out.println("[tochka] " + message);
    }
}
