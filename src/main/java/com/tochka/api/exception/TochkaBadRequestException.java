package com.tochka.api.exception;

import java.util.List;

/** 400 Bad Request: the request failed validation on the bank side. */
public class TochkaBadRequestException extends TochkaApiException {

    private static final long serialVersionUID = 1L;

    public TochkaBadRequestException(int statusCode,
                 String code,
                 String errorId,
                 String message,
                 List<ApiError> errors,
                 String rawBody) {
        super(statusCode, code, errorId, message, errors, rawBody);
    }
}
