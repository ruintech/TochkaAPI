package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PaginatedLinkModel
 *
 * @param self Self. Например: "https://enter.tochka.com/uapi"
 * @param first First. Например: "https://enter.tochka.com/uapi" (необязательное)
 * @param prev Prev. Например: "https://enter.tochka.com/uapi" (необязательное)
 * @param next Next. Например: "https://enter.tochka.com/uapi" (необязательное)
 * @param last Last. Например: "https://enter.tochka.com/uapi" (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaginatedLinkModel(
        @JsonProperty("self") String self,
        @JsonProperty("first") String first,
        @JsonProperty("prev") String prev,
        @JsonProperty("next") String next,
        @JsonProperty("last") String last) {

    /** Строитель {@link PaginatedLinkModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .self(this.self)
                .first(this.first)
                .prev(this.prev)
                .next(this.next)
                .last(this.last);
    }

    /** Строитель {@link PaginatedLinkModel}. */
    public static final class Builder {

        private String self;
        private String first;
        private String prev;
        private String next;
        private String last;

        /** Self. Например: "https://enter.tochka.com/uapi" */
        public Builder self(String self) {
            this.self = self;
            return this;
        }

        /** First. Например: "https://enter.tochka.com/uapi" */
        public Builder first(String first) {
            this.first = first;
            return this;
        }

        /** Prev. Например: "https://enter.tochka.com/uapi" */
        public Builder prev(String prev) {
            this.prev = prev;
            return this;
        }

        /** Next. Например: "https://enter.tochka.com/uapi" */
        public Builder next(String next) {
            this.next = next;
            return this;
        }

        /** Last. Например: "https://enter.tochka.com/uapi" */
        public Builder last(String last) {
            this.last = last;
            return this;
        }

        public PaginatedLinkModel build() {
            return new PaginatedLinkModel(this.self, this.first, this.prev, this.next, this.last);
        }
    }
}
