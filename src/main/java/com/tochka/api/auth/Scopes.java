package com.tochka.api.auth;

import java.util.List;

/**
 * Области доступа (scope) OAuth 2.0.
 *
 * <p>Scope задаёт, к каким сервисам приложение просит доступ, а разрешения (permissions)
 * в списке согласия — точный перечень операций внутри них. Набор scope должен быть одинаковым
 * во всех запросах одного потока авторизации.
 */
public final class Scopes {

    public static final String ACCOUNTS = "accounts";
    public static final String BALANCES = "balances";
    public static final String CUSTOMERS = "customers";
    public static final String STATEMENTS = "statements";
    public static final String SBP = "sbp";
    public static final String PAYMENTS = "payments";
    public static final String ACQUIRING = "acquiring";

    /** Все известные области доступа. */
    public static final List<String> ALL =
            List.of(ACCOUNTS, BALANCES, CUSTOMERS, STATEMENTS, SBP, PAYMENTS, ACQUIRING);

    private Scopes() {
    }
}
