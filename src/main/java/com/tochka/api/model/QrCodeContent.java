package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * QrCodeContent
 *
 * @param width Ширина изображения (&gt;=200, по умолчанию: 300)
 * @param height Высота изображения (&gt;=200, по умолчанию: 300)
 * @param mediaType Тип контента (необязательное)
 * @param content содержимое изображения (для image/png - в кодировке base64). Например:
 *        "iVBORw0KGgoAAAANSUhEUgAAASwAAAEs..."
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record QrCodeContent(
        @JsonProperty("width") Integer width,
        @JsonProperty("height") Integer height,
        @JsonProperty("mediaType") MediaTypeEnum mediaType,
        @JsonProperty("content") String content) {

    /** Строитель {@link QrCodeContent}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .width(this.width)
                .height(this.height)
                .mediaType(this.mediaType)
                .content(this.content);
    }

    /** Строитель {@link QrCodeContent}. */
    public static final class Builder {

        private Integer width;
        private Integer height;
        private MediaTypeEnum mediaType;
        private String content;

        /** Ширина изображения (&gt;=200, по умолчанию: 300) */
        public Builder width(Integer width) {
            this.width = width;
            return this;
        }

        /** Высота изображения (&gt;=200, по умолчанию: 300) */
        public Builder height(Integer height) {
            this.height = height;
            return this;
        }

        /** Тип контента */
        public Builder mediaType(MediaTypeEnum mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        /** содержимое изображения (для image/png - в кодировке base64). Например:
        "iVBORw0KGgoAAAANSUhEUgAAASwAAAEs..." */
        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public QrCodeContent build() {
            return new QrCodeContent(this.width, this.height, this.mediaType, this.content);
        }
    }
}
