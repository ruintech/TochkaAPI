package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * PaymentStatusResponseModel
 *
 * @param requestId ID запроса. Example: "openapi-b96d770e-769f-49ce-9630-890e00d47720"
 * @param status Статус. Example: "WaitingForCreate"
 * @param errors Ошибки (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentStatusResponseModel(
        @JsonProperty("requestId") String requestId,
        @JsonProperty("status") PaymentForSignStatusEnum status,
        @JsonProperty("errors") List<com.fasterxml.jackson.databind.JsonNode> errors) {

    /** Builder for {@link PaymentStatusResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .requestId(this.requestId)
                .status(this.status)
                .errors(this.errors);
    }

    /** Builder for {@link PaymentStatusResponseModel}. */
    public static final class Builder {

        private String requestId;
        private PaymentForSignStatusEnum status;
        private List<com.fasterxml.jackson.databind.JsonNode> errors;

        /** ID запроса. Example: "openapi-b96d770e-769f-49ce-9630-890e00d47720" */
        public Builder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        /** Статус. Example: "WaitingForCreate" */
        public Builder status(PaymentForSignStatusEnum status) {
            this.status = status;
            return this;
        }

        /** Ошибки */
        public Builder errors(List<com.fasterxml.jackson.databind.JsonNode> errors) {
            this.errors = errors;
            return this;
        }

        public PaymentStatusResponseModel build() {
            return new PaymentStatusResponseModel(this.requestId, this.status, this.errors);
        }
    }
}
