package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * HTTPNotFoundResponse
 *
 * @param code Высокоуровневый текстовый код ошибки, необходимый для классификации.. Example: "404"
 * @param id Уникальный идентификатор ошибки, для целей аудита. Example:
 *        "c397b21a-d998-4c4d-9471-e60eaf816b87"
 * @param message Краткое сообщение об ошибке.. Example: "Что-то пошло не так"
 * @param errors Errors
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HTTPNotFoundResponse(
        @JsonProperty("code") String code,
        @JsonProperty("id") String id,
        @JsonProperty("message") String message,
        @JsonProperty("Errors") List<HTTPNotFound> errors) {

    /** Builder for {@link HTTPNotFoundResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .code(this.code)
                .id(this.id)
                .message(this.message)
                .errors(this.errors);
    }

    /** Builder for {@link HTTPNotFoundResponse}. */
    public static final class Builder {

        private String code;
        private String id;
        private String message;
        private List<HTTPNotFound> errors;

        /** Высокоуровневый текстовый код ошибки, необходимый для классификации.. Example: "404" */
        public Builder code(String code) {
            this.code = code;
            return this;
        }

        /** Уникальный идентификатор ошибки, для целей аудита. Example:
        "c397b21a-d998-4c4d-9471-e60eaf816b87" */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Краткое сообщение об ошибке.. Example: "Что-то пошло не так" */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /** Errors */
        public Builder errors(List<HTTPNotFound> errors) {
            this.errors = errors;
            return this;
        }

        public HTTPNotFoundResponse build() {
            return new HTTPNotFoundResponse(this.code, this.id, this.message, this.errors);
        }
    }
}
