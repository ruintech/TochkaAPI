package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringCreateSubscriptionRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringCreateSubscriptionRequestDataModel(
        @JsonProperty("Data") AcquiringCreateSubscriptionRequestModel data) {

    /** Строитель {@link AcquiringCreateSubscriptionRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link AcquiringCreateSubscriptionRequestDataModel}. */
    public static final class Builder {

        private AcquiringCreateSubscriptionRequestModel data;

        public Builder data(AcquiringCreateSubscriptionRequestModel data) {
            this.data = data;
            return this;
        }

        public AcquiringCreateSubscriptionRequestDataModel build() {
            return new AcquiringCreateSubscriptionRequestDataModel(this.data);
        }
    }
}
