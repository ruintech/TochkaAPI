package com.tochka.api.exception;

import java.util.List;

/** 5xx: an internal error on the bank side. */
public class TochkaServerException extends TochkaApiException {

    private static final long serialVersionUID = 1L;

    public TochkaServerException(int statusCode,
                 String code,
                 String errorId,
                 String message,
                 List<ApiError> errors,
                 String rawBody) {
        super(statusCode, code, errorId, message, errors, rawBody);
    }
}
