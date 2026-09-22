package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Событие неизвестного типа — например, {@code customWebhook} или новый тип, появившийся в API
 * позже этой версии библиотеки. Полезная нагрузка доступна как карта полей, так что обработчик
 * не сломается на незнакомом событии.
 */
public final class UnknownWebhookEvent implements WebhookEvent {

    private final Map<String, Object> fields = new LinkedHashMap<>();

    @JsonAnySetter
    void put(String name, Object value) {
        fields.put(name, value);
    }

    /** Все поля события как есть. */
    @JsonAnyGetter
    public Map<String, Object> fields() {
        return fields;
    }

    @Override
    @JsonIgnore
    public String webhookType() {
        return (String) fields.get("webhookType");
    }

    @Override
    @JsonIgnore
    public String customerCode() {
        return (String) fields.get("customerCode");
    }

    @Override
    public String toString() {
        return "UnknownWebhookEvent" + fields;
    }
}
