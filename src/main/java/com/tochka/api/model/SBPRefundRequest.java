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

    /** Строитель {@link SBPRefundRequest}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link SBPRefundRequest}. */
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
