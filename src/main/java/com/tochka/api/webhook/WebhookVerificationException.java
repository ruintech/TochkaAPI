package com.tochka.api.webhook;

import com.tochka.api.exception.TochkaException;

/**
 * A webhook failed signature verification or is malformed. Such a request must not be processed:
 * it did not come from Tochka Bank, or it was modified on the way.
 */
public class WebhookVerificationException extends TochkaException {

    private static final long serialVersionUID = 1L;

    public WebhookVerificationException(String message) {
        super(message);
    }

    public WebhookVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
