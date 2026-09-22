package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PaymentForSignResponseModel
 *
 * @param requestId ID запроса. Example: "openapi-b96d770e-769f-49ce-9630-890e00d47720"
 * @param redirectURL Ссылка на страницу подписания платежа. Example:
 *        "https://i.tochka.com/bank/m/payment-preview/openapi-b96d770e-769f-49ce-9630-890e00d47720?customerCode=300000092"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentForSignResponseModel(
        @JsonProperty("requestId") String requestId,
        @JsonProperty("redirectURL") String redirectURL) {

    /** Builder for {@link PaymentForSignResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .requestId(this.requestId)
                .redirectURL(this.redirectURL);
    }

    /** Builder for {@link PaymentForSignResponseModel}. */
    public static final class Builder {

        private String requestId;
        private String redirectURL;

        /** ID запроса. Example: "openapi-b96d770e-769f-49ce-9630-890e00d47720" */
        public Builder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        /** Ссылка на страницу подписания платежа. Example:
        "https://i.tochka.com/bank/m/payment-preview/openapi-b96d770e-769f-49ce-9630-890e00d47720?customerCode=300000092" */
        public Builder redirectURL(String redirectURL) {
            this.redirectURL = redirectURL;
            return this;
        }

        public PaymentForSignResponseModel build() {
            return new PaymentForSignResponseModel(this.requestId, this.redirectURL);
        }
    }
}
