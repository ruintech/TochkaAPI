package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * WebhookEditRequest
 *
 * @param webhooksList Новый список событий, на которые нужно подписаться. Например: ["incomingPayment"]
 * @param url url на который необходимо отправлять запрос
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record WebhookEditRequest(
        @JsonProperty("webhooksList") List<WebhookTypeEnum> webhooksList,
        @JsonProperty("url") String url) {

    /** Строитель {@link WebhookEditRequest}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .webhooksList(this.webhooksList)
                .url(this.url);
    }

    /** Строитель {@link WebhookEditRequest}. */
    public static final class Builder {

        private List<WebhookTypeEnum> webhooksList;
        private String url;

        /** Новый список событий, на которые нужно подписаться. Например: ["incomingPayment"] */
        public Builder webhooksList(List<WebhookTypeEnum> webhooksList) {
            this.webhooksList = webhooksList;
            return this;
        }

        /** url на который необходимо отправлять запрос */
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public WebhookEditRequest build() {
            return new WebhookEditRequest(this.webhooksList, this.url);
        }
    }
}
