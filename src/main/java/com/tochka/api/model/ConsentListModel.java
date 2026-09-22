package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * ConsentListModel
 *
 * @param consent Consent
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConsentListModel(
        @JsonProperty("Consent") List<ConsentResponseModel> consent) {

    /** Builder for {@link ConsentListModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .consent(this.consent);
    }

    /** Builder for {@link ConsentListModel}. */
    public static final class Builder {

        private List<ConsentResponseModel> consent;

        /** Consent */
        public Builder consent(List<ConsentResponseModel> consent) {
            this.consent = consent;
            return this;
        }

        public ConsentListModel build() {
            return new ConsentListModel(this.consent);
        }
    }
}
