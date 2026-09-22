package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ContentAct
 *
 * @param act Содержимое акта
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContentAct(
        @JsonProperty("Act") ActModel act) {

    /** Builder for {@link ContentAct}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .act(this.act);
    }

    /** Builder for {@link ContentAct}. */
    public static final class Builder {

        private ActModel act;

        /** Содержимое акта */
        public Builder act(ActModel act) {
            this.act = act;
            return this;
        }

        public ContentAct build() {
            return new ContentAct(this.act);
        }
    }
}
