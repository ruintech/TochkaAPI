package com.tochka.api.exception;

import java.util.List;
import java.util.Optional;

/**
 * API вернул ответ с кодом ошибки. Тело разобрано в поля {@link #code()}, {@link #errorId()},
 * {@link #errors()} — в том виде, в каком их отдаёт банк.
 *
 * <p>Для типовых ситуаций выбрасываются подклассы: {@link TochkaBadRequestException},
 * {@link TochkaUnauthorizedException}, {@link TochkaForbiddenException},
 * {@link TochkaNotFoundException}, {@link TochkaDependencyException},
 * {@link TochkaServerException}.
 */
public class TochkaApiException extends TochkaException {

    private static final long serialVersionUID = 1L;

    private final int statusCode;
    private final String code;
    private final String errorId;
    @SuppressWarnings("serial") // List.copyOf возвращает сериализуемую реализацию
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

    /** HTTP-статус ответа. */
    public int statusCode() {
        return statusCode;
    }

    /** Высокоуровневый код ошибки из тела ответа. */
    public String code() {
        return code;
    }

    /** Уникальный идентификатор ошибки — его стоит указывать при обращении в поддержку банка. */
    public Optional<String> errorId() {
        return Optional.ofNullable(errorId);
    }

    /** Подробный список ошибок из поля {@code Errors}. */
    public List<ApiError> errors() {
        return errors;
    }

    /** Тело ответа как есть — на случай, если структура ошибки отличается от ожидаемой. */
    public String rawBody() {
        return rawBody;
    }

    /** Низкоуровневый {@code errorCode} первой ошибки, если он есть. */
    public Optional<String> firstErrorCode() {
        return errors.isEmpty() ? Optional.empty() : Optional.ofNullable(errors.get(0).errorCode());
    }
}
