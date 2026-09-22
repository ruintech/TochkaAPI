package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ClosingDocumentCreateRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClosingDocumentCreateRequestDataModel(
        @JsonProperty("Data") ClosingDocumentCreateRequestModel data) {

    /** Строитель {@link ClosingDocumentCreateRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link ClosingDocumentCreateRequestDataModel}. */
    public static final class Builder {

        private ClosingDocumentCreateRequestModel data;

        public Builder data(ClosingDocumentCreateRequestModel data) {
            this.data = data;
            return this;
        }

        public ClosingDocumentCreateRequestDataModel build() {
            return new ClosingDocumentCreateRequestDataModel(this.data);
        }
    }
}
