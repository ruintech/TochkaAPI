package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * ErrorResponse
 *
 * @param code Высокоуровневый текстовый код ошибки, необходимый для классификации.. Example: "500"
 * @param id Уникальный идентификатор ошибки, для целей аудита. Example:
 *        "c397b21a-d998-4c4d-9471-e60eaf816b87"
 * @param message Краткое сообщение об ошибке.. Example: "Что-то пошло не так"
 * @param errors Подробное описание ошибок
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        @JsonProperty("code") String code,
        @JsonProperty("id") String id,
        @JsonProperty("message") String message,
        @JsonProperty("Errors") List<ErrorModel> errors) {

    /** Builder for {@link ErrorResponse}. */
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

    /** Builder for {@link ErrorResponse}. */
    public static final class Builder {

        private String code;
        private String id;
        private String message;
        private List<ErrorModel> errors;

        /** Высокоуровневый текстовый код ошибки, необходимый для классификации.. Example: "500" */
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

        /** Подробное описание ошибок */
        public Builder errors(List<ErrorModel> errors) {
            this.errors = errors;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this.code, this.id, this.message, this.errors);
        }
    }
}
