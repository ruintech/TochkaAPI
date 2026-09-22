package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Status
 *
 * @param status Статус объекта. Например: "Active"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Status(
        @JsonProperty("status") StatusEnum status) {

    /** Строитель {@link Status}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .status(this.status);
    }

    /** Строитель {@link Status}. */
    public static final class Builder {

        private StatusEnum status;

        /** Статус объекта. Например: "Active" */
        public Builder status(StatusEnum status) {
            this.status = status;
            return this;
        }

        public Status build() {
            return new Status(this.status);
        }
    }
}
