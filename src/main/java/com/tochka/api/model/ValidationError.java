package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Validation Error
 *
 * @param errorCode Низкоуровневое текстовое описание ошибки. Например: "Validation Error"
 * @param message Описание ошибки. Обрезается если длина ошибки превышает максимальное значение. Например:
 *        "Something going wrong"
 * @param url URL для помощи в устранении проблемы. Например: "https://developers.tochka.com/"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ValidationError(
        @JsonProperty("errorCode") String errorCode,
        @JsonProperty("message") String message,
        @JsonProperty("url") String url) {

    /** Строитель {@link ValidationError}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .errorCode(this.errorCode)
                .message(this.message)
                .url(this.url);
    }

    /** Строитель {@link ValidationError}. */
    public static final class Builder {

        private String errorCode;
        private String message;
        private String url;

        /** Низкоуровневое текстовое описание ошибки. Например: "Validation Error" */
        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        /** Описание ошибки. Обрезается если длина ошибки превышает максимальное значение. Например:
        "Something going wrong" */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /** URL для помощи в устранении проблемы. Например: "https://developers.tochka.com/" */
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public ValidationError build() {
            return new ValidationError(this.errorCode, this.message, this.url);
        }
    }
}
