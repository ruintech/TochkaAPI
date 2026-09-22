package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * StatusRequest
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record StatusRequest(
        @JsonProperty("Data") Status data) {

    /** Builder for {@link StatusRequest}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Builder for {@link StatusRequest}. */
    public static final class Builder {

        private Status data;

        public Builder data(Status data) {
            this.data = data;
            return this;
        }

        public StatusRequest build() {
            return new StatusRequest(this.data);
        }
    }
}
