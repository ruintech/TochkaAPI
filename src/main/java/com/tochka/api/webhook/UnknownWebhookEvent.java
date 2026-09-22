package com.tochka.api.webhook;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An event of an unknown type — {@code customWebhook}, or a type added to the API after this
 * version of the library. The payload is exposed as a field map, so a handler does not break on
 * an unfamiliar event.
 */
public final class UnknownWebhookEvent implements WebhookEvent {

    private final Map<String, Object> fields = new LinkedHashMap<>();

    @JsonAnySetter
    void put(String name, Object value) {
        fields.put(name, value);
    }

    /** All event fields as they arrived. */
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
