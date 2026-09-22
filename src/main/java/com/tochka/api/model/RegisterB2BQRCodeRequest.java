package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RegisterB2BQRCodeRequest
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RegisterB2BQRCodeRequest(
        @JsonProperty("Data") RegisterB2BQRCode data) {

    /** Строитель {@link RegisterB2BQRCodeRequest}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link RegisterB2BQRCodeRequest}. */
    public static final class Builder {

        private RegisterB2BQRCode data;

        public Builder data(RegisterB2BQRCode data) {
            this.data = data;
            return this;
        }

        public RegisterB2BQRCodeRequest build() {
            return new RegisterB2BQRCodeRequest(this.data);
        }
    }
}
