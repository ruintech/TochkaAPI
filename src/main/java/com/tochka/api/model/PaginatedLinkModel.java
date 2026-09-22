package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PaginatedLinkModel
 *
 * @param self Self. Example: "https://enter.tochka.com/uapi"
 * @param first First. Example: "https://enter.tochka.com/uapi" (optional)
 * @param prev Prev. Example: "https://enter.tochka.com/uapi" (optional)
 * @param next Next. Example: "https://enter.tochka.com/uapi" (optional)
 * @param last Last. Example: "https://enter.tochka.com/uapi" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaginatedLinkModel(
        @JsonProperty("self") String self,
        @JsonProperty("first") String first,
        @JsonProperty("prev") String prev,
        @JsonProperty("next") String next,
        @JsonProperty("last") String last) {

    /** Builder for {@link PaginatedLinkModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .self(this.self)
                .first(this.first)
                .prev(this.prev)
                .next(this.next)
                .last(this.last);
    }

    /** Builder for {@link PaginatedLinkModel}. */
    public static final class Builder {

        private String self;
        private String first;
        private String prev;
        private String next;
        private String last;

        /** Self. Example: "https://enter.tochka.com/uapi" */
        public Builder self(String self) {
            this.self = self;
            return this;
        }

        /** First. Example: "https://enter.tochka.com/uapi" */
        public Builder first(String first) {
            this.first = first;
            return this;
        }

        /** Prev. Example: "https://enter.tochka.com/uapi" */
        public Builder prev(String prev) {
            this.prev = prev;
            return this;
        }

        /** Next. Example: "https://enter.tochka.com/uapi" */
        public Builder next(String next) {
            this.next = next;
            return this;
        }

        /** Last. Example: "https://enter.tochka.com/uapi" */
        public Builder last(String last) {
            this.last = last;
            return this;
        }

        public PaginatedLinkModel build() {
            return new PaginatedLinkModel(this.self, this.first, this.prev, this.next, this.last);
        }
    }
}
