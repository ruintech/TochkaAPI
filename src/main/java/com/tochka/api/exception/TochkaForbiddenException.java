package com.tochka.api.exception;

import java.util.List;

/** 403 Forbidden: у токена нет нужного разрешения (scope) либо указан чужой customerCode. */
public class TochkaForbiddenException extends TochkaApiException {

    private static final long serialVersionUID = 1L;

    public TochkaForbiddenException(int statusCode,
                 String code,
                 String errorId,
                 String message,
                 List<ApiError> errors,
                 String rawBody) {
        super(statusCode, code, errorId, message, errors, rawBody);
    }
}
