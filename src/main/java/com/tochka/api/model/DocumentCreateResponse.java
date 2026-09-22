package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DocumentCreateResponse
 *
 * @param documentId Уникальный идентификатор документа. Example: "1cf95c4f-e794-4407-bac4-0829f19bd2be"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DocumentCreateResponse(
        @JsonProperty("documentId") String documentId) {

    /** Builder for {@link DocumentCreateResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .documentId(this.documentId);
    }

    /** Builder for {@link DocumentCreateResponse}. */
    public static final class Builder {

        private String documentId;

        /** Уникальный идентификатор документа. Example: "1cf95c4f-e794-4407-bac4-0829f19bd2be" */
        public Builder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        public DocumentCreateResponse build() {
            return new DocumentCreateResponse(this.documentId);
        }
    }
}
