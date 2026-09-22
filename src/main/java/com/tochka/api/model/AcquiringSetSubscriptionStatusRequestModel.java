package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringSetSubscriptionStatusRequestModel
 *
 * @param status Статус подписки
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringSetSubscriptionStatusRequestModel(
        @JsonProperty("status") AcquiringSubscriptionStatusInput status) {

    /** Builder for {@link AcquiringSetSubscriptionStatusRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .status(this.status);
    }

    /** Builder for {@link AcquiringSetSubscriptionStatusRequestModel}. */
    public static final class Builder {

        private AcquiringSubscriptionStatusInput status;

        /** Статус подписки */
        public Builder status(AcquiringSubscriptionStatusInput status) {
            this.status = status;
            return this;
        }

        public AcquiringSetSubscriptionStatusRequestModel build() {
            return new AcquiringSetSubscriptionStatusRequestModel(this.status);
        }
    }
}
