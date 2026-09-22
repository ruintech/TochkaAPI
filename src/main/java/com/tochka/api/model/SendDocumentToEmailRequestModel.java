package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SendDocumentToEmailRequestModel
 *
 * @param email Электронная почта, на которую нужно отправить
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SendDocumentToEmailRequestModel(
        @JsonProperty("email") String email) {

    /** Builder for {@link SendDocumentToEmailRequestModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .email(this.email);
    }

    /** Builder for {@link SendDocumentToEmailRequestModel}. */
    public static final class Builder {

        private String email;

        /** Электронная почта, на которую нужно отправить */
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public SendDocumentToEmailRequestModel build() {
            return new SendDocumentToEmailRequestModel(this.email);
        }
    }
}
