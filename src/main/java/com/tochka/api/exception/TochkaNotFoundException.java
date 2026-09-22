package com.tochka.api.exception;

import java.util.List;

/** 404 Not Found: the object does not exist. Also returned when the method path is wrong. */
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
