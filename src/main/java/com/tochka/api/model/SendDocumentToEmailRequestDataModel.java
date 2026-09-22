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

    /** Builder for {@link SendDocumentToEmailRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Builder for {@link SendDocumentToEmailRequestDataModel}. */
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
