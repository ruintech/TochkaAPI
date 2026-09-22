package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * HTTPNotFound
 *
 * @param errorCode Низкоуровневое текстовое описание ошибки. Example: "HTTPNotFound"
 * @param message Описание ошибки. Обрезается если длина ошибки превышает максимальное значение. Example:
 *        "Something going wrong"
 * @param url URL для помощи в устранении проблемы. Example: "https://developers.tochka.com/"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HTTPNotFound(
        @JsonProperty("errorCode") String errorCode,
        @JsonProperty("message") String message,
        @JsonProperty("url") String url) {

    /** Builder for {@link HTTPNotFound}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .errorCode(this.errorCode)
                .message(this.message)
                .url(this.url);
    }

    /** Builder for {@link HTTPNotFound}. */
    public static final class Builder {

        private String errorCode;
        private String message;
        private String url;

        /** Низкоуровневое текстовое описание ошибки. Example: "HTTPNotFound" */
        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        /** Описание ошибки. Обрезается если длина ошибки превышает максимальное значение. Example:
        "Something going wrong" */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /** URL для помощи в устранении проблемы. Example: "https://developers.tochka.com/" */
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public HTTPNotFound build() {
            return new HTTPNotFound(this.errorCode, this.message, this.url);
        }
    }
}
