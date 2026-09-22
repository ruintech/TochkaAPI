package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * AcquiringSubscriptionListResponseModel
 *
 * @param subscription Subscription
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringSubscriptionListResponseModel(
        @JsonProperty("Subscription") List<AcquiringSubscriptionListItemModel> subscription) {

    /** Builder for {@link AcquiringSubscriptionListResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .subscription(this.subscription);
    }

    /** Builder for {@link AcquiringSubscriptionListResponseModel}. */
    public static final class Builder {

        private List<AcquiringSubscriptionListItemModel> subscription;

        /** Subscription */
        public Builder subscription(List<AcquiringSubscriptionListItemModel> subscription) {
            this.subscription = subscription;
            return this;
        }

        public AcquiringSubscriptionListResponseModel build() {
            return new AcquiringSubscriptionListResponseModel(this.subscription);
        }
    }
}
