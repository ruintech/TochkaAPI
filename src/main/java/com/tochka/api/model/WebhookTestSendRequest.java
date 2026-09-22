package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * WebhookTestSendRequest
 *
 * @param webhookType Тип вебхука
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record WebhookTestSendRequest(
        @JsonProperty("webhookType") WebhookTypeEnum webhookType) {

    /** Builder for {@link WebhookTestSendRequest}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .webhookType(this.webhookType);
    }

    /** Builder for {@link WebhookTestSendRequest}. */
    public static final class Builder {

        private WebhookTypeEnum webhookType;

        /** Тип вебхука */
        public Builder webhookType(WebhookTypeEnum webhookType) {
            this.webhookType = webhookType;
            return this;
        }

        public WebhookTestSendRequest build() {
            return new WebhookTestSendRequest(this.webhookType);
        }
    }
}
