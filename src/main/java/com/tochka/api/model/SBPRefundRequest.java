package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SBPRefundRequest
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SBPRefundRequest(
        @JsonProperty("Data") SBPRefund data) {

    /** Builder for {@link SBPRefundRequest}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Builder for {@link SBPRefundRequest}. */
    public static final class Builder {

        private SBPRefund data;

        public Builder data(SBPRefund data) {
            this.data = data;
            return this;
        }

        public SBPRefundRequest build() {
            return new SBPRefundRequest(this.data);
        }
    }
}
