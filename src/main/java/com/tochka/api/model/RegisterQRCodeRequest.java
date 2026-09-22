package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RegisterQRCodeRequest
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterQRCodeRequest(
        @JsonProperty("Data") RegisterQRCode data) {

    /** Builder for {@link RegisterQRCodeRequest}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Builder for {@link RegisterQRCodeRequest}. */
    public static final class Builder {

        private RegisterQRCode data;

        public Builder data(RegisterQRCode data) {
            this.data = data;
            return this;
        }

        public RegisterQRCodeRequest build() {
            return new RegisterQRCodeRequest(this.data);
        }
    }
}
