package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ContentUpd
 *
 * @param upd Содержимое УПД
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContentUpd(
        @JsonProperty("Upd") UpdModel upd) {

    /** Builder for {@link ContentUpd}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .upd(this.upd);
    }

    /** Builder for {@link ContentUpd}. */
    public static final class Builder {

        private UpdModel upd;

        /** Содержимое УПД */
        public Builder upd(UpdModel upd) {
            this.upd = upd;
            return this;
        }

        public ContentUpd build() {
            return new ContentUpd(this.upd);
        }
    }
}
