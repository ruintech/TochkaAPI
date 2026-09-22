package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * AcquiringRetailerListModel
 *
 * @param retailer Retailer
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringRetailerListModel(
        @JsonProperty("Retailer") List<AcquiringRetailerModel> retailer) {

    /** Builder for {@link AcquiringRetailerListModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .retailer(this.retailer);
    }

    /** Builder for {@link AcquiringRetailerListModel}. */
    public static final class Builder {

        private List<AcquiringRetailerModel> retailer;

        /** Retailer */
        public Builder retailer(List<AcquiringRetailerModel> retailer) {
            this.retailer = retailer;
            return this;
        }

        public AcquiringRetailerListModel build() {
            return new AcquiringRetailerListModel(this.retailer);
        }
    }
}
