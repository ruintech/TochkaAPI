package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SBPRefundStatus
 *
 * @param requestId ID запроса. Example: "openapi-b96d770e-769f-49ce-9630-890e00d47720"
 * @param status Статус по процессу возрата. Example: "Confirmed"
 * @param statusDescription Statusdescription. Описание статуса (причина ошибки или сообщение об успехе) (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SBPRefundStatus(
        @JsonProperty("requestId") String requestId,
        @JsonProperty("status") SBPPaymentStatus status,
        @JsonProperty("statusDescription") String statusDescription) {

    /** Builder for {@link SBPRefundStatus}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .requestId(this.requestId)
                .status(this.status)
                .statusDescription(this.statusDescription);
    }

    /** Builder for {@link SBPRefundStatus}. */
    public static final class Builder {

        private String requestId;
        private SBPPaymentStatus status;
        private String statusDescription;

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

        /** Statusdescription. Описание статуса (причина ошибки или сообщение об успехе) */
        public Builder statusDescription(String statusDescription) {
            this.statusDescription = statusDescription;
            return this;
        }

        public SBPRefundStatus build() {
            return new SBPRefundStatus(this.requestId, this.status, this.statusDescription);
        }
    }
}
