package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SendDocumentToEmailRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SendDocumentToEmailRequestDataModel(
        @JsonProperty("Data") SendDocumentToEmailRequestModel data) {

    /** Строитель {@link SendDocumentToEmailRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link SendDocumentToEmailRequestDataModel}. */
    public static final class Builder {

        private SendDocumentToEmailRequestModel data;

        public Builder data(SendDocumentToEmailRequestModel data) {
            this.data = data;
            return this;
        }

        public SendDocumentToEmailRequestDataModel build() {
            return new SendDocumentToEmailRequestDataModel(this.data);
        }
    }
}
