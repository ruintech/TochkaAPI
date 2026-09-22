package com.tochka.api.exception;

import java.util.List;

/** 424 Failed Dependency: a downstream service of the bank did not answer. Worth retrying. */
public class TochkaDependencyException extends TochkaApiException {

    private static final long serialVersionUID = 1L;

    public TochkaDependencyException(int statusCode,
                 String code,
                 String errorId,
                 String message,
                 List<ApiError> errors,
                 String rawBody) {
        super(statusCode, code, errorId, message, errors, rawBody);
    }
}
