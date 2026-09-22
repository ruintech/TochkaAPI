package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * LinkModel
 *
 * @param self Self. Например: "https://enter.tochka.com/uapi"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LinkModel(
        @JsonProperty("self") String self) {

    /** Строитель {@link LinkModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .self(this.self);
    }

    /** Строитель {@link LinkModel}. */
    public static final class Builder {

        private String self;

        /** Self. Например: "https://enter.tochka.com/uapi" */
        public Builder self(String self) {
            this.self = self;
            return this;
        }

        public LinkModel build() {
            return new LinkModel(this.self);
        }
    }
}
