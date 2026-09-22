package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringGetSubscriptionStatusResponseModel
 *
 * @param status status
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringGetSubscriptionStatusResponseModel(
        @JsonProperty("status") AcquiringSubscriptionStatus status) {

    /** Builder for {@link AcquiringGetSubscriptionStatusResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .status(this.status);
    }

    /** Builder for {@link AcquiringGetSubscriptionStatusResponseModel}. */
    public static final class Builder {

        private AcquiringSubscriptionStatus status;

        public Builder status(AcquiringSubscriptionStatus status) {
            this.status = status;
            return this;
        }

        public AcquiringGetSubscriptionStatusResponseModel build() {
            return new AcquiringGetSubscriptionStatusResponseModel(this.status);
        }
    }
}
