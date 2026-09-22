package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CardTransactionTerminalData
 *
 * @param city City. Город терминала. Example: "Perm" (optional)
 * @param location Location. Адрес терминала. Example: "Ekaterinburg" (optional)
 * @param owner Owner. Название торговой точки (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CardTransactionTerminalData(
        @JsonProperty("city") String city,
        @JsonProperty("location") String location,
        @JsonProperty("owner") String owner) {

    /** Builder for {@link CardTransactionTerminalData}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .city(this.city)
                .location(this.location)
                .owner(this.owner);
    }

    /** Builder for {@link CardTransactionTerminalData}. */
    public static final class Builder {

        private String city;
        private String location;
        private String owner;

        /** City. Город терминала. Example: "Perm" */
        public Builder city(String city) {
            this.city = city;
            return this;
        }

        /** Location. Адрес терминала. Example: "Ekaterinburg" */
        public Builder location(String location) {
            this.location = location;
            return this;
        }

        /** Owner. Название торговой точки */
        public Builder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public CardTransactionTerminalData build() {
            return new CardTransactionTerminalData(this.city, this.location, this.owner);
        }
    }
}
