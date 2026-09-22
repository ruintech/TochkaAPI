package com.tochka.api.exception;

import java.util.List;

/** 424 Failed Dependency: смежный сервис банка не ответил. Запрос имеет смысл повторить. */
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
