package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Validation ErrorResponse
 *
 * @param code Высокоуровневый текстовый код ошибки, необходимый для классификации.. Например: "400"
 * @param id Уникальный идентификатор ошибки, для целей аудита. Например:
 *        "c397b21a-d998-4c4d-9471-e60eaf816b87"
 * @param message Краткое сообщение об ошибке.. Например: "Что-то пошло не так"
 * @param errors Errors
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ValidationErrorResponse(
        @JsonProperty("code") String code,
        @JsonProperty("id") String id,
        @JsonProperty("message") String message,
        @JsonProperty("Errors") List<ValidationError> errors) {

    /** Строитель {@link ValidationErrorResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .code(this.code)
                .id(this.id)
                .message(this.message)
                .errors(this.errors);
    }

    /** Строитель {@link ValidationErrorResponse}. */
    public static final class Builder {

        private String code;
        private String id;
        private String message;
        private List<ValidationError> errors;

        /** Высокоуровневый текстовый код ошибки, необходимый для классификации.. Например: "400" */
        public Builder code(String code) {
            this.code = code;
            return this;
        }

        /** Уникальный идентификатор ошибки, для целей аудита. Например:
        "c397b21a-d998-4c4d-9471-e60eaf816b87" */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Краткое сообщение об ошибке.. Например: "Что-то пошло не так" */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /** Errors */
        public Builder errors(List<ValidationError> errors) {
            this.errors = errors;
            return this;
        }

        public ValidationErrorResponse build() {
            return new ValidationErrorResponse(this.code, this.id, this.message, this.errors);
        }
    }
}
