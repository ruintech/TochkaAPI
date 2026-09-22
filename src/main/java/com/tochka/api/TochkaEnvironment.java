package com.tochka.api;

import java.net.URI;
import java.util.Objects;

/**
 * Среда исполнения запросов: боевой слой или песочница.
 *
 * <p>Песочница повторяет структуру боевых методов, но отвечает захардкоженными тестовыми
 * данными, поэтому пригодна только для отладки формата запросов и ответов.
 */
public final class TochkaEnvironment {

    /** Боевой слой: {@code https://enter.tochka.com/uapi/}. */
    public static final TochkaEnvironment PRODUCTION =
            new TochkaEnvironment(URI.create("https://enter.tochka.com/uapi/"));

    /** Песочница: {@code https://enter.tochka.com/sandbox/v2/}. Токен — {@code sandbox.jwt.token}. */
    public static final TochkaEnvironment SANDBOX =
            new TochkaEnvironment(URI.create("https://enter.tochka.com/sandbox/v2/"));

    /** Токен, с которым работает песочница. */
    public static final String SANDBOX_TOKEN = "sandbox.jwt.token";

    private final URI baseUri;

    private TochkaEnvironment(URI baseUri) {
        this.baseUri = baseUri;
    }

    /**
     * Произвольный базовый адрес — например, корпоративный прокси перед API банка.
     *
     * @param baseUri базовый адрес; завершающий слеш добавляется автоматически
     */
    public static TochkaEnvironment of(String baseUri) {
        Objects.requireNonNull(baseUri, "baseUri");
        String normalized = baseUri.endsWith("/") ? baseUri : baseUri + "/";
        return new TochkaEnvironment(URI.create(normalized));
    }

    public URI baseUri() {
        return baseUri;
    }

    @Override
    public String toString() {
        return baseUri.toString();
    }
}
