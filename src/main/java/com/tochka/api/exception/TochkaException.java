package com.tochka.api.exception;

/** Базовое исключение клиента Точка Банка. */
public class TochkaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TochkaException(String message) {
        super(message);
    }

    public TochkaException(String message, Throwable cause) {
        super(message, cause);
    }
}
