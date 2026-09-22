package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * LinkModel
 *
 * @param self Self. Example: "https://enter.tochka.com/uapi"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LinkModel(
        @JsonProperty("self") String self) {

    /** Builder for {@link LinkModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .self(this.self);
    }

    /** Builder for {@link LinkModel}. */
    public static final class Builder {

        private String self;

        /** Self. Example: "https://enter.tochka.com/uapi" */
        public Builder self(String self) {
            this.self = self;
            return this;
        }

        public LinkModel build() {
            return new LinkModel(this.self);
        }
    }
}
