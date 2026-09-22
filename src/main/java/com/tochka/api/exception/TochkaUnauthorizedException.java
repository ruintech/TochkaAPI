package com.tochka.api.exception;

import java.util.List;

/** 401 Unauthorized: токен отсутствует, истёк или отозван. */
public class TochkaUnauthorizedException extends TochkaApiException {

    private static final long serialVersionUID = 1L;

    public TochkaUnauthorizedException(int statusCode,
                 String code,
                 String errorId,
                 String message,
                 List<ApiError> errors,
                 String rawBody) {
        super(statusCode, code, errorId, message, errors, rawBody);
    }
}
