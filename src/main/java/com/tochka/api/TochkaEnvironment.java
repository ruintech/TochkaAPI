package com.tochka.api;

import java.net.URI;
import java.util.Objects;

/**
 * The environment requests are sent to: production or sandbox.
 *
 * <p>The sandbox mirrors the structure of the production methods but answers with hard-coded
 * test data, so it is only good for working out request and response formats.
 */
public final class TochkaEnvironment {

    /** Production: {@code https://enter.tochka.com/uapi/}. */
    public static final TochkaEnvironment PRODUCTION =
            new TochkaEnvironment(URI.create("https://enter.tochka.com/uapi/"));

    /** Sandbox: {@code https://enter.tochka.com/sandbox/v2/}. The token is {@code sandbox.jwt.token}. */
    public static final TochkaEnvironment SANDBOX =
            new TochkaEnvironment(URI.create("https://enter.tochka.com/sandbox/v2/"));

    /** The token the sandbox accepts. */
    public static final String SANDBOX_TOKEN = "sandbox.jwt.token";

    private final URI baseUri;

    private TochkaEnvironment(URI baseUri) {
        this.baseUri = baseUri;
    }

    /**
     * An arbitrary base URL — a corporate proxy in front of the bank API, for example.
     *
     * @param baseUri base URL; the trailing slash is added automatically
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
