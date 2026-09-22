package com.tochka.api.exception;

import java.util.List;
import java.util.Optional;

/**
 * The API answered with an error status. The body is parsed into {@link #code()},
 * {@link #errorId()} and {@link #errors()} exactly as the bank returns them.
 *
 * <p>Common situations get their own subclasses: {@link TochkaBadRequestException},
 * {@link TochkaUnauthorizedException}, {@link TochkaForbiddenException},
 * {@link TochkaNotFoundException}, {@link TochkaDependencyException},
 * {@link TochkaServerException}.
 */
public class TochkaApiException extends TochkaException {

    private static final long serialVersionUID = 1L;

    private final int statusCode;
    private final String code;
    private final String errorId;
    @SuppressWarnings("serial") // List.copyOf returns a serializable implementation
    private final List<ApiError> errors;
    private final String rawBody;

    public TochkaApiException(int statusCode,
                              String code,
                              String errorId,
                              String message,
                              List<ApiError> errors,
                              String rawBody) {
        super(buildMessage(statusCode, code, message, errors));
        this.statusCode = statusCode;
        this.code = code;
        this.errorId = errorId;
        this.errors = errors == null ? List.of() : List.copyOf(errors);
        this.rawBody = rawBody;
    }

    private static String buildMessage(int statusCode, String code, String message, List<ApiError> errors) {
        StringBuilder sb = new StringBuilder("HTTP ").append(statusCode);
        if (code != null && !code.isBlank() && !code.equals(String.valueOf(statusCode))) {
            sb.append(" [").append(code).append(']');
        }
        if (message != null && !message.isBlank()) {
            sb.append(": ").append(message);
        }
        if (errors != null && !errors.isEmpty()) {
            sb.append(" (");
            for (int i = 0; i < errors.size(); i++) {
                if (i > 0) {
                    sb.append("; ");
                }
                sb.append(errors.get(i));
            }
            sb.append(')');
        }
        return sb.toString();
    }

    /** HTTP status of the response. */
    public int statusCode() {
        return statusCode;
    }

    /** High-level error code from the response body. */
    public String code() {
        return code;
    }

    /** Unique error id — quote it when contacting the bank support. */
    public Optional<String> errorId() {
        return Optional.ofNullable(errorId);
    }

    /** Detailed error list from the {@code Errors} field. */
    public List<ApiError> errors() {
        return errors;
    }

    /** Raw response body, in case the error structure differs from the expected one. */
    public String rawBody() {
        return rawBody;
    }

    /** Low-level {@code errorCode} of the first error, if any. */
    public Optional<String> firstErrorCode() {
        return errors.isEmpty() ? Optional.empty() : Optional.ofNullable(errors.get(0).errorCode());
    }
}
