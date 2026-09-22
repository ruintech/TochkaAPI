package com.tochka.api.auth;

import java.util.List;

/**
 * OAuth 2.0 scopes.
 *
 * <p>A scope says which services the application asks access to, while the permissions of a
 * consent list say exactly which operations inside them are allowed. The set of scopes must be
 * identical across all requests of one authorization flow.
 */
public final class Scopes {

    public static final String ACCOUNTS = "accounts";
    public static final String BALANCES = "balances";
    public static final String CUSTOMERS = "customers";
    public static final String STATEMENTS = "statements";
    public static final String SBP = "sbp";
    public static final String PAYMENTS = "payments";
    public static final String ACQUIRING = "acquiring";

    /** All known scopes. */
    public static final List<String> ALL =
            List.of(ACCOUNTS, BALANCES, CUSTOMERS, STATEMENTS, SBP, PAYMENTS, ACQUIRING);

    private Scopes() {
    }
}
