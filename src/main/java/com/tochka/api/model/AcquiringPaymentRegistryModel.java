package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * AcquiringPaymentRegistryModel
 *
 * @param registry Registry
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringPaymentRegistryModel(
        @JsonProperty("Registry") List<AcquiringPaymentRegistryItemModel> registry) {

    /** Builder for {@link AcquiringPaymentRegistryModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .registry(this.registry);
    }

    /** Builder for {@link AcquiringPaymentRegistryModel}. */
    public static final class Builder {

        private List<AcquiringPaymentRegistryItemModel> registry;

        /** Registry */
        public Builder registry(List<AcquiringPaymentRegistryItemModel> registry) {
            this.registry = registry;
            return this;
        }

        public AcquiringPaymentRegistryModel build() {
            return new AcquiringPaymentRegistryModel(this.registry);
        }
    }
}
