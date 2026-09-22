package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ChangeCashboxQRCodeAccountRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChangeCashboxQRCodeAccountRequestDataModel(
        @JsonProperty("Data") ChangeCashboxQRCodeAccountRequestModel data) {

    /** Строитель {@link ChangeCashboxQRCodeAccountRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link ChangeCashboxQRCodeAccountRequestDataModel}. */
    public static final class Builder {

        private ChangeCashboxQRCodeAccountRequestModel data;

        public Builder data(ChangeCashboxQRCodeAccountRequestModel data) {
            this.data = data;
            return this;
        }

        public ChangeCashboxQRCodeAccountRequestDataModel build() {
            return new ChangeCashboxQRCodeAccountRequestDataModel(this.data);
        }
    }
}
