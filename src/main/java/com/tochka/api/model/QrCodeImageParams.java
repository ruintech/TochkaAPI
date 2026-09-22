package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * QrCodeImageParams
 *
 * @param width Ширина изображения (&gt;=200, по умолчанию: 300)
 * @param height Высота изображения (&gt;=200, по умолчанию: 300)
 * @param mediaType Тип контента (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record QrCodeImageParams(
        @JsonProperty("width") Integer width,
        @JsonProperty("height") Integer height,
        @JsonProperty("mediaType") MediaTypeEnum mediaType) {

    /** Строитель {@link QrCodeImageParams}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .width(this.width)
                .height(this.height)
                .mediaType(this.mediaType);
    }

    /** Строитель {@link QrCodeImageParams}. */
    public static final class Builder {

        private Integer width;
        private Integer height;
        private MediaTypeEnum mediaType;

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

        public QrCodeImageParams build() {
            return new QrCodeImageParams(this.width, this.height, this.mediaType);
        }
    }
}
