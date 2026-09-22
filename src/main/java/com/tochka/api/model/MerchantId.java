package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * MerchantId
 *
 * @param merchantId Идентификатор ТСП. Например: "MF0000000001"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MerchantId(
        @JsonProperty("merchantId") String merchantId) {

    /** Строитель {@link MerchantId}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .merchantId(this.merchantId);
    }

    /** Строитель {@link MerchantId}. */
    public static final class Builder {

        private String merchantId;

        /** Идентификатор ТСП. Например: "MF0000000001" */
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public MerchantId build() {
            return new MerchantId(this.merchantId);
        }
    }
}
