package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * PaymentStatusResponseModel
 *
 * @param requestId ID запроса. Например: "openapi-b96d770e-769f-49ce-9630-890e00d47720"
 * @param status Статус. Например: "WaitingForCreate"
 * @param errors Ошибки (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentStatusResponseModel(
        @JsonProperty("requestId") String requestId,
        @JsonProperty("status") PaymentForSignStatusEnum status,
        @JsonProperty("errors") List<com.fasterxml.jackson.databind.JsonNode> errors) {

    /** Строитель {@link PaymentStatusResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .requestId(this.requestId)
                .status(this.status)
                .errors(this.errors);
    }

    /** Строитель {@link PaymentStatusResponseModel}. */
    public static final class Builder {

        private String requestId;
        private PaymentForSignStatusEnum status;
        private List<com.fasterxml.jackson.databind.JsonNode> errors;

        /** ID запроса. Например: "openapi-b96d770e-769f-49ce-9630-890e00d47720" */
        public Builder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        /** Статус. Например: "WaitingForCreate" */
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
