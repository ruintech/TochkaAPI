package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SBPRefundRequestResponse
 *
 * @param requestId ID запроса. Например: "openapi-b96d770e-769f-49ce-9630-890e00d47720"
 * @param status Статус по процессу возрата. Например: "Confirmed"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SBPRefundRequestResponse(
        @JsonProperty("requestId") String requestId,
        @JsonProperty("status") SBPPaymentStatus status) {

    /** Строитель {@link SBPRefundRequestResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .requestId(this.requestId)
                .status(this.status);
    }

    /** Строитель {@link SBPRefundRequestResponse}. */
    public static final class Builder {

        private String requestId;
        private SBPPaymentStatus status;

        /** ID запроса. Например: "openapi-b96d770e-769f-49ce-9630-890e00d47720" */
        public Builder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        /** Статус по процессу возрата. Например: "Confirmed" */
        public Builder status(SBPPaymentStatus status) {
            this.status = status;
            return this;
        }

        public SBPRefundRequestResponse build() {
            return new SBPRefundRequestResponse(this.requestId, this.status);
        }
    }
}
