package com.tochka.api.auth;

import java.util.Objects;
import java.util.function.Supplier;

/** Source of the {@code Authorization} header value for API requests. */
@FunctionalInterface
public interface Authorization {

    /** The ready header value, for example {@code Bearer eyJ...}. */
    String authorizationHeader();

    /**
     * A long-lived JWT key created in the internet bank («Интеграции и API» → «Создать JWT-ключ»).
     * The right choice when you are the only user of the integration.
     */
    static Authorization jwt(String token) {
        Objects.requireNonNull(token, "token");
        String header = "Bearer " + token;
        return () -> header;
    }

    /**
     * A token resolved on every request — for example when it lives in an external secret store.
     *
     * @param tokenSupplier supplier of the bare token, without the {@code Bearer} prefix
     */
    static Authorization dynamic(Supplier<String> tokenSupplier) {
        Objects.requireNonNull(tokenSupplier, "tokenSupplier");
        return () -> "Bearer " + tokenSupplier.get();
    }
}
