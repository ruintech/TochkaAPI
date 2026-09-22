package com.tochka.api.http;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An API request assembled piece by piece. Instances come from {@link Transport#request} and are
 * normally invisible to library users: they are built by the service classes in
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

    /** Fills in a path placeholder such as <code>{accountId}</code>. */
    public ApiRequest path(String name, Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Параметр пути " + name + " обязателен");
        }
        pathParams.put(name, String.valueOf(value));
        return this;
    }

    /** Adds a query parameter; {@code null} is ignored. */
    public ApiRequest query(String name, Object value) {
        if (value != null) {
            queryParams.put(name, value);
        }
        return this;
    }

    /** Adds a header; {@code null} is ignored. */
    public ApiRequest header(String name, String value) {
        if (value != null) {
            headers.put(name, value);
        }
        return this;
    }

    /** Request body — a model object that will be serialized to JSON. */
    public ApiRequest body(Object body) {
        this.body = body;
        return this;
    }

    /**
     * Path inside the response envelope whose content is returned to the caller,
     * for example {@code unwrap("Data", "AccountList")}.
     */
    public ApiRequest unwrap(String... path) {
        this.unwrapPath = path;
        return this;
    }

    /** Executes the request and parses the result into the given type. */
    public <T> T as(Class<T> type) {
        return transport.execute(this, Json.mapper().getTypeFactory().constructType(type));
    }

    /** Executes the request and parses the result into the given generic type. */
    public <T> T as(TypeReference<T> type) {
        return transport.execute(this, Json.mapper().getTypeFactory().constructType(type));
    }

    /** Executes the request and returns a page together with {@code Links} and {@code Meta}. */
    public <T> Page<T> asPage(TypeReference<java.util.List<T>> itemsType) {
        return transport.executePage(this, Json.mapper().getTypeFactory().constructType(itemsType));
    }

    /** Executes the request and returns a file: the PDF of an invoice or a closing document. */
    public BinaryContent asBinary() {
        return transport.executeBinary(this);
    }

    /** Executes the request, ignoring the response body. */
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
