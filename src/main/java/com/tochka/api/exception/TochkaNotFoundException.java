package com.tochka.api.exception;

import java.util.List;

/** 404 Not Found: объект не найден. Также возвращается при неверном пути метода. */
public class TochkaNotFoundException extends TochkaApiException {

    private static final long serialVersionUID = 1L;

    public TochkaNotFoundException(int statusCode,
                 String code,
                 String errorId,
                 String message,
                 List<ApiError> errors,
                 String rawBody) {
        super(statusCode, code, errorId, message, errors, rawBody);
    }
}
