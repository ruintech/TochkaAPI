package com.tochka.api.exception;

/**
 * A single entry of the {@code Errors} array in an API error body.
 *
 * @param errorCode low-level error code, for example {@code Something going wrong}
 * @param message   error description
 * @param url       link to documentation that helps to resolve the problem
 */
public record ApiError(String errorCode, String message, String url) {

    @Override
    public String toString() {
        return errorCode + ": " + message;
    }
}
