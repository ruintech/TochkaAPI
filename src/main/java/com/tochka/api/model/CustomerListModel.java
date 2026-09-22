package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * CustomerListModel
 *
 * @param customer Customer
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerListModel(
        @JsonProperty("Customer") List<CustomerModel> customer) {

    /** Строитель {@link CustomerListModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .customer(this.customer);
    }

    /** Строитель {@link CustomerListModel}. */
    public static final class Builder {

        private List<CustomerModel> customer;

        /** Customer */
        public Builder customer(List<CustomerModel> customer) {
            this.customer = customer;
            return this;
        }

        public CustomerListModel build() {
            return new CustomerListModel(this.customer);
        }
    }
}
