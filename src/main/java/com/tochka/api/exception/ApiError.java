package com.tochka.api.exception;

/**
 * Один элемент массива {@code Errors} в теле ошибки API.
 *
 * @param errorCode низкоуровневый код ошибки, например {@code Something going wrong}
 * @param message   описание ошибки
 * @param url       ссылка на документацию, помогающую устранить проблему
 */
public record ApiError(String errorCode, String message, String url) {

    @Override
    public String toString() {
        return errorCode + ": " + message;
    }
}
