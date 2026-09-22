package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ActivateCashboxQrCodeRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ActivateCashboxQrCodeRequestDataModel(
        @JsonProperty("Data") ActivateCashboxQrCodeRequestModel data) {

    /** Строитель {@link ActivateCashboxQrCodeRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link ActivateCashboxQrCodeRequestDataModel}. */
    public static final class Builder {

        private ActivateCashboxQrCodeRequestModel data;

        public Builder data(ActivateCashboxQrCodeRequestModel data) {
            this.data = data;
            return this;
        }

        public ActivateCashboxQrCodeRequestDataModel build() {
            return new ActivateCashboxQrCodeRequestDataModel(this.data);
        }
    }
}
