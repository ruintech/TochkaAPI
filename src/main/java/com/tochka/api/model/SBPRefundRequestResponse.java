package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SBPRefundRequestResponse
 *
 * @param requestId ID запроса. Example: "openapi-b96d770e-769f-49ce-9630-890e00d47720"
 * @param status Статус по процессу возрата. Example: "Confirmed"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SBPRefundRequestResponse(
        @JsonProperty("requestId") String requestId,
        @JsonProperty("status") SBPPaymentStatus status) {

    /** Builder for {@link SBPRefundRequestResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .requestId(this.requestId)
                .status(this.status);
    }

    /** Builder for {@link SBPRefundRequestResponse}. */
    public static final class Builder {

        private String requestId;
        private SBPPaymentStatus status;

        /** ID запроса. Example: "openapi-b96d770e-769f-49ce-9630-890e00d47720" */
        public Builder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        /** Статус по процессу возрата. Example: "Confirmed" */
        public Builder status(SBPPaymentStatus status) {
            this.status = status;
            return this;
        }

        public SBPRefundRequestResponse build() {
            return new SBPRefundRequestResponse(this.requestId, this.status);
        }
    }
}
