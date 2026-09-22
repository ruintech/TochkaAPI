package com.tochka.api.http;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.tochka.api.auth.Authorization;
import com.tochka.api.exception.ApiError;
import com.tochka.api.exception.TochkaApiException;
import com.tochka.api.exception.TochkaBadRequestException;
import com.tochka.api.exception.TochkaDependencyException;
import com.tochka.api.exception.TochkaException;
import com.tochka.api.exception.TochkaForbiddenException;
import com.tochka.api.exception.TochkaNotFoundException;
import com.tochka.api.exception.TochkaServerException;
import com.tochka.api.exception.TochkaTransportException;
import com.tochka.api.exception.TochkaUnauthorizedException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Выполняет HTTP-запросы к API: собирает адрес, подставляет авторизацию, повторяет
 * неудавшиеся попытки, разбирает конверт {@code {Data, Links, Meta}} и превращает ответы
 * с кодом ошибки в исключения.
 */
public final class Transport {

    private static final String JSON = "application/json";

    private final URI baseUri;
    private final HttpClient httpClient;
    private final Authorization authorization;
    private final String defaultCustomerCode;
    private final Duration requestTimeout;
    private final RetryPolicy retryPolicy;
    private final LogLevel logLevel;
    private final RequestLogger logger;
    private final String userAgent;

    public Transport(URI baseUri,
                     HttpClient httpClient,
                     Authorization authorization,
                     String defaultCustomerCode,
                     Duration requestTimeout,
                     RetryPolicy retryPolicy,
                     LogLevel logLevel,
                     RequestLogger logger,
                     String userAgent) {
        this.baseUri = Objects.requireNonNull(baseUri, "baseUri");
        this.httpClient = Objects.requireNonNull(httpClient, "httpClient");
        this.authorization = Objects.requireNonNull(authorization, "authorization");
        this.defaultCustomerCode = defaultCustomerCode;
        this.requestTimeout = requestTimeout;
        this.retryPolicy = retryPolicy;
        this.logLevel = logLevel;
        this.logger = logger;
        this.userAgent = userAgent;
    }

    /** Начинает сборку запроса. */
    public ApiRequest request(String method, String pathTemplate) {
        return new ApiRequest(this, method, pathTemplate);
    }

    /** Код клиента, который подставляется в запросы, если он не задан явно. */
    public String defaultCustomerCode() {
        return defaultCustomerCode;
    }

    <T> T execute(ApiRequest request, JavaType type) {
        HttpResponse<byte[]> response = send(request);
        byte[] body = response.body();
        if (body == null || body.length == 0) {
            return null;
        }
        JsonNode root = readTree(body);
        JsonNode target = navigate(root, request.unwrapPath(), body);
        if (target.isNull() || target.isMissingNode()) {
            return null;
        }
        try {
            return Json.mapper().treeToValue(target, type);
        } catch (IOException e) {
            throw new TochkaException("Не удалось разобрать ответ в " + type + ": " + text(body), e);
        }
    }

    <T> Page<T> executePage(ApiRequest request, JavaType itemsType) {
        HttpResponse<byte[]> response = send(request);
        byte[] body = response.body();
        JsonNode root = readTree(body);
        JsonNode items = navigate(root, request.unwrapPath(), body);
        List<T> list;
        try {
            list = items.isMissingNode() || items.isNull()
                    ? List.of()
                    : Json.mapper().treeToValue(items, itemsType);
        } catch (IOException e) {
            throw new TochkaException("Не удалось разобрать список в " + itemsType + ": " + text(body), e);
        }
        JsonNode links = root.path("Links");
        JsonNode meta = root.path("Meta");
        return new Page<>(
                list,
                meta.hasNonNull("totalPages") ? meta.get("totalPages").asInt() : null,
                textOrNull(links, "self"),
                textOrNull(links, "next"),
                textOrNull(links, "prev"),
                textOrNull(links, "first"),
                textOrNull(links, "last"));
    }

    BinaryContent executeBinary(ApiRequest request) {
        HttpResponse<byte[]> response = send(request);
        String contentType = response.headers().firstValue("Content-Type").orElse("application/octet-stream");
        String fileName = response.headers().firstValue("Content-Disposition")
                .map(Transport::parseFileName)
                .orElse(null);
        return new BinaryContent(response.body(), contentType, fileName);
    }

    private HttpResponse<byte[]> send(ApiRequest request) {
        URI uri = buildUri(request);
        byte[] payload = serializeBody(request.body());
        String method = request.method().toUpperCase(Locale.ROOT);

        int attempt = 0;
        while (true) {
            attempt++;
            HttpRequest httpRequest = buildHttpRequest(method, uri, payload, request.headers());
            long startedAt = System.nanoTime();
            HttpResponse<byte[]> response;
            try {
                logRequest(method, uri, httpRequest, payload);
                response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
            } catch (IOException e) {
                if (retryPolicy.shouldRetryAfterIoError(attempt, method)) {
                    int failedAttempt = attempt;
                    log(LogLevel.BASIC, () -> method + " " + uri + " — сетевая ошибка ("
                            + e.getMessage() + "), попытка " + failedAttempt + " из " + retryPolicy.maxAttempts());
                    sleep(retryPolicy.backoff(attempt, null));
                    continue;
                }
                throw new TochkaTransportException(method + " " + uri + " не выполнен: " + e.getMessage(), e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new TochkaTransportException(method + " " + uri + " прерван", e);
            }

            logResponse(method, uri, response, startedAt);

            int status = response.statusCode();
            if (status / 100 == 2) {
                return response;
            }
            if (retryPolicy.shouldRetry(attempt, method, status)) {
                sleep(retryPolicy.backoff(attempt, retryAfterSeconds(response)));
                continue;
            }
            throw toException(status, response.body());
        }
    }

    private HttpRequest buildHttpRequest(String method, URI uri, byte[] payload, Map<String, String> headers) {
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri)
                .header("Authorization", authorization.authorizationHeader())
                .header("Accept", JSON);
        if (userAgent != null) {
            builder.header("User-Agent", userAgent);
        }
        if (requestTimeout != null) {
            builder.timeout(requestTimeout);
        }
        headers.forEach(builder::header);
        if (payload == null) {
            builder.method(method, HttpRequest.BodyPublishers.noBody());
        } else {
            builder.header("Content-Type", JSON)
                    .method(method, HttpRequest.BodyPublishers.ofByteArray(payload));
        }
        return builder.build();
    }

    private URI buildUri(ApiRequest request) {
        String path = request.pathTemplate();
        for (Map.Entry<String, String> entry : request.pathParams().entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            if (!path.contains(placeholder)) {
                throw new IllegalStateException("В пути " + request.pathTemplate()
                        + " нет плейсхолдера " + placeholder);
            }
            path = path.replace(placeholder, encodePathSegment(entry.getValue()));
        }
        if (path.contains("{")) {
            throw new IllegalStateException("Не заполнены параметры пути: " + path);
        }
        String relative = path.startsWith("/") ? path.substring(1) : path;
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, Object> entry : request.queryParams().entrySet()) {
            query.append(query.length() == 0 ? '?' : '&')
                    .append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                    .append('=')
                    .append(URLEncoder.encode(String.valueOf(entry.getValue()), StandardCharsets.UTF_8));
        }
        return baseUri.resolve(relative + query);
    }

    /**
     * Кодирует значение параметра пути, сохраняя слеши: {@code accountId} — это номер счёта и
     * БИК через слеш, и банк ждёт его в пути как есть.
     */
    private static String encodePathSegment(String value) {
        StringBuilder sb = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            boolean safe = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')
                    || c == '-' || c == '.' || c == '_' || c == '~' || c == '/' || c == ',' || c == ':';
            if (safe) {
                sb.append(c);
            } else {
                for (byte b : String.valueOf(c).getBytes(StandardCharsets.UTF_8)) {
                    sb.append('%').append(String.format("%02X", b));
                }
            }
        }
        return sb.toString();
    }

    private byte[] serializeBody(Object body) {
        if (body == null) {
            return null;
        }
        try {
            return Json.mapper().writeValueAsBytes(body);
        } catch (IOException e) {
            throw new TochkaException("Не удалось сериализовать тело запроса", e);
        }
    }

    private JsonNode readTree(byte[] body) {
        try {
            return Json.mapper().readTree(body);
        } catch (IOException e) {
            throw new TochkaTransportException("Ответ API не является корректным JSON: " + text(body), e);
        }
    }

    private static JsonNode navigate(JsonNode root, String[] path, byte[] body) {
        JsonNode current = root;
        for (String segment : path) {
            if (!current.has(segment)) {
                throw new TochkaException("В ответе API нет поля '" + segment
                        + "' (ожидался путь " + String.join(".", path) + "): " + text(body));
            }
            current = current.get(segment);
        }
        return current;
    }

    private TochkaApiException toException(int status, byte[] body) {
        String raw = text(body);
        String code = null;
        String id = null;
        String message = null;
        List<ApiError> errors = new ArrayList<>();
        try {
            JsonNode root = Json.mapper().readTree(body);
            code = textOrNull(root, "code");
            id = textOrNull(root, "id");
            message = textOrNull(root, "message");
            JsonNode errorsNode = root.path("Errors");
            if (errorsNode.isArray()) {
                for (JsonNode error : errorsNode) {
                    errors.add(new ApiError(
                            textOrNull(error, "errorCode"),
                            textOrNull(error, "message"),
                            textOrNull(error, "url")));
                }
            }
        } catch (IOException | RuntimeException ignored) {
            message = raw;
        }
        return switch (status) {
            case 400, 422 -> new TochkaBadRequestException(status, code, id, message, errors, raw);
            case 401 -> new TochkaUnauthorizedException(status, code, id, message, errors, raw);
            case 403 -> new TochkaForbiddenException(status, code, id, message, errors, raw);
            case 404 -> new TochkaNotFoundException(status, code, id, message, errors, raw);
            case 424 -> new TochkaDependencyException(status, code, id, message, errors, raw);
            default -> status >= 500
                    ? new TochkaServerException(status, code, id, message, errors, raw)
                    : new TochkaApiException(status, code, id, message, errors, raw);
        };
    }

    private static String textOrNull(JsonNode node, String field) {
        JsonNode value = node.path(field);
        return value.isMissingNode() || value.isNull() ? null : value.asText();
    }

    private static String text(byte[] body) {
        return body == null ? "" : new String(body, StandardCharsets.UTF_8);
    }

    private static Long retryAfterSeconds(HttpResponse<?> response) {
        return response.headers().firstValue("Retry-After")
                .filter(value -> value.chars().allMatch(Character::isDigit))
                .map(Long::parseLong)
                .orElse(null);
    }

    private static String parseFileName(String contentDisposition) {
        for (String part : contentDisposition.split(";")) {
            String trimmed = part.trim();
            if (trimmed.regionMatches(true, 0, "filename=", 0, "filename=".length())) {
                String name = trimmed.substring("filename=".length()).trim();
                if (name.length() >= 2 && name.startsWith("\"") && name.endsWith("\"")) {
                    name = name.substring(1, name.length() - 1);
                }
                return name;
            }
        }
        return null;
    }

    private static void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TochkaTransportException("Ожидание перед повтором прервано", e);
        }
    }

    private void logRequest(String method, URI uri, HttpRequest httpRequest, byte[] payload) {
        if (logLevel == LogLevel.NONE) {
            return;
        }
        StringBuilder sb = new StringBuilder("--> ").append(method).append(' ').append(uri);
        if (logLevel == LogLevel.HEADERS || logLevel == LogLevel.BODY) {
            httpRequest.headers().map().forEach((name, values) ->
                    sb.append("\n    ").append(name).append(": ")
                            .append("authorization".equalsIgnoreCase(name) ? "***" : String.join(", ", values)));
        }
        if (logLevel == LogLevel.BODY && payload != null) {
            sb.append("\n    ").append(text(payload));
        }
        logger.log(sb.toString());
    }

    private void logResponse(String method, URI uri, HttpResponse<byte[]> response, long startedAt) {
        if (logLevel == LogLevel.NONE) {
            return;
        }
        long millis = (System.nanoTime() - startedAt) / 1_000_000;
        StringBuilder sb = new StringBuilder("<-- ").append(response.statusCode()).append(' ')
                .append(method).append(' ').append(uri).append(" (").append(millis).append(" ms)");
        if (logLevel == LogLevel.HEADERS || logLevel == LogLevel.BODY) {
            response.headers().map().forEach((name, values) ->
                    sb.append("\n    ").append(name).append(": ").append(String.join(", ", values)));
        }
        if (logLevel == LogLevel.BODY) {
            sb.append("\n    ").append(text(response.body()));
        }
        logger.log(sb.toString());
    }

    private void log(LogLevel minimumLevel, java.util.function.Supplier<String> message) {
        if (logLevel.ordinal() >= minimumLevel.ordinal() && logLevel != LogLevel.NONE) {
            logger.log(message.get());
        }
    }
}
