package com.tochka.api.http;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Запрос к методу API, собираемый по частям. Экземпляры создаёт {@link Transport#request};
 * пользователю библиотеки они обычно не видны — их строят классы сервисов из пакета
 * {@code com.tochka.api.api}.
 */
public final class ApiRequest {

    private final Transport transport;
    private final String method;
    private final String pathTemplate;
    private final Map<String, String> pathParams = new LinkedHashMap<>();
    private final Map<String, Object> queryParams = new LinkedHashMap<>();
    private final Map<String, String> headers = new LinkedHashMap<>();
    private Object body;
    private String[] unwrapPath = new String[0];

    ApiRequest(Transport transport, String method, String pathTemplate) {
        this.transport = transport;
        this.method = method;
        this.pathTemplate = pathTemplate;
    }

    /** Подставляет значение в плейсхолдер пути вида <code>{accountId}</code>. */
    public ApiRequest path(String name, Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Параметр пути " + name + " обязателен");
        }
        pathParams.put(name, String.valueOf(value));
        return this;
    }

    /** Добавляет query-параметр; {@code null} игнорируется. */
    public ApiRequest query(String name, Object value) {
        if (value != null) {
            queryParams.put(name, value);
        }
        return this;
    }

    /** Добавляет заголовок; {@code null} игнорируется. */
    public ApiRequest header(String name, String value) {
        if (value != null) {
            headers.put(name, value);
        }
        return this;
    }

    /** Тело запроса — объект модели, который будет сериализован в JSON. */
    public ApiRequest body(Object body) {
        this.body = body;
        return this;
    }

    /**
     * Путь внутри конверта ответа, который нужно вернуть вызывающему коду,
     * например {@code unwrap("Data", "AccountList")}.
     */
    public ApiRequest unwrap(String... path) {
        this.unwrapPath = path;
        return this;
    }

    /** Выполняет запрос и разбирает результат в указанный тип. */
    public <T> T as(Class<T> type) {
        return transport.execute(this, Json.mapper().getTypeFactory().constructType(type));
    }

    /** Выполняет запрос и разбирает результат в указанный обобщённый тип. */
    public <T> T as(TypeReference<T> type) {
        return transport.execute(this, Json.mapper().getTypeFactory().constructType(type));
    }

    /** Выполняет запрос и возвращает страницу списка вместе с {@code Links} и {@code Meta}. */
    public <T> Page<T> asPage(TypeReference<java.util.List<T>> itemsType) {
        return transport.executePage(this, Json.mapper().getTypeFactory().constructType(itemsType));
    }

    /** Выполняет запрос и возвращает файл: PDF счёта или закрывающего документа. */
    public BinaryContent asBinary() {
        return transport.executeBinary(this);
    }

    /** Выполняет запрос, игнорируя тело ответа. */
    public void execute() {
        transport.execute(this, Json.mapper().getTypeFactory().constructType(Object.class));
    }

    String method() {
        return method;
    }

    String pathTemplate() {
        return pathTemplate;
    }

    Map<String, String> pathParams() {
        return pathParams;
    }

    Map<String, Object> queryParams() {
        return queryParams;
    }

    Map<String, String> headers() {
        return headers;
    }

    Object body() {
        return body;
    }

    String[] unwrapPath() {
        return unwrapPath;
    }
}
