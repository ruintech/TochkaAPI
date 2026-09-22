package com.tochka.api.auth;

import java.util.Objects;
import java.util.function.Supplier;

/** Источник значения заголовка {@code Authorization} для запросов к API. */
@FunctionalInterface
public interface Authorization {

    /** Готовое значение заголовка, например {@code Bearer eyJ...}. */
    String authorizationHeader();

    /**
     * Долгоживущий JWT-ключ, созданный в интернет-банке («Интеграции и API» → «Создать JWT-ключ»).
     * Подходит, когда интеграцией пользуетесь только вы.
     */
    static Authorization jwt(String token) {
        Objects.requireNonNull(token, "token");
        String header = "Bearer " + token;
        return () -> header;
    }

    /**
     * Токен, вычисляемый на каждый запрос, — например, если он хранится во внешнем секрет-сторе.
     *
     * @param tokenSupplier поставщик самого токена, без префикса {@code Bearer}
     */
    static Authorization dynamic(Supplier<String> tokenSupplier) {
        Objects.requireNonNull(tokenSupplier, "tokenSupplier");
        return () -> "Bearer " + tokenSupplier.get();
    }
}
