package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Webhook
 *
 * @param webhooksList Список событий, на которое подписано приложение. Example: ["incomingPayment"]
 * @param url url на который необходимо отправлять запрос
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Webhook(
        @JsonProperty("webhooksList") List<WebhookTypeEnum> webhooksList,
        @JsonProperty("url") String url) {

    /** Builder for {@link Webhook}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .webhooksList(this.webhooksList)
                .url(this.url);
    }

    /** Builder for {@link Webhook}. */
    public static final class Builder {

        private List<WebhookTypeEnum> webhooksList;
        private String url;

        /** Список событий, на которое подписано приложение. Example: ["incomingPayment"] */
        public Builder webhooksList(List<WebhookTypeEnum> webhooksList) {
            this.webhooksList = webhooksList;
            return this;
        }

        /** url на который необходимо отправлять запрос */
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Webhook build() {
            return new Webhook(this.webhooksList, this.url);
        }
    }
}
