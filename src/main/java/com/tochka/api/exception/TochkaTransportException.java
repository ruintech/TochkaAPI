package com.tochka.api.exception;

/**
 * The request never reached the API, or the response could not be read: a network error, a
 * timeout, a failed TLS handshake, an unreadable response body.
 *
 * <p>A TLS failure against {@code enter.tochka.com} usually means the JVM trust store lacks the
 * Ministry of Digital Development certificates — see {@link com.tochka.api.tls.RussianTrustedCa}.
 */
public class TochkaTransportException extends TochkaException {

    private static final long serialVersionUID = 1L;

    public TochkaTransportException(String message, Throwable cause) {
        super(message, cause);
    }
}
