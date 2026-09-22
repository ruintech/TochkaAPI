package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DocumentCreateResponse
 *
 * @param documentId Уникальный идентификатор документа. Например: "1cf95c4f-e794-4407-bac4-0829f19bd2be"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DocumentCreateResponse(
        @JsonProperty("documentId") String documentId) {

    /** Строитель {@link DocumentCreateResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .documentId(this.documentId);
    }

    /** Строитель {@link DocumentCreateResponse}. */
    public static final class Builder {

        private String documentId;

        /** Уникальный идентификатор документа. Например: "1cf95c4f-e794-4407-bac4-0829f19bd2be" */
        public Builder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        public DocumentCreateResponse build() {
            return new DocumentCreateResponse(this.documentId);
        }
    }
}
