package com.tochka.api.webhook;

import com.tochka.api.exception.TochkaException;

/**
 * Вебхук не прошёл проверку подписи или имеет некорректный формат. Такой запрос обрабатывать
 * нельзя: он пришёл не от Точка Банка либо был изменён по дороге.
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
