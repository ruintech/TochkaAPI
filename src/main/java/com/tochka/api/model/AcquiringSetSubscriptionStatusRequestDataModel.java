package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AcquiringSetSubscriptionStatusRequestDataModel
 *
 * @param data Data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringSetSubscriptionStatusRequestDataModel(
        @JsonProperty("Data") AcquiringSetSubscriptionStatusRequestModel data) {

    /** Строитель {@link AcquiringSetSubscriptionStatusRequestDataModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data);
    }

    /** Строитель {@link AcquiringSetSubscriptionStatusRequestDataModel}. */
    public static final class Builder {

        private AcquiringSetSubscriptionStatusRequestModel data;

        public Builder data(AcquiringSetSubscriptionStatusRequestModel data) {
            this.data = data;
            return this;
        }

        public AcquiringSetSubscriptionStatusRequestDataModel build() {
            return new AcquiringSetSubscriptionStatusRequestDataModel(this.data);
        }
    }
}
