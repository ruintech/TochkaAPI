package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * AcquiringGetPaymentOperationListResponseModel
 *
 * @param operation Operation
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AcquiringGetPaymentOperationListResponseModel(
        @JsonProperty("Operation") List<AcquiringGetPaymentOperationListItemModel> operation) {

    /** Строитель {@link AcquiringGetPaymentOperationListResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .operation(this.operation);
    }

    /** Строитель {@link AcquiringGetPaymentOperationListResponseModel}. */
    public static final class Builder {

        private List<AcquiringGetPaymentOperationListItemModel> operation;

        /** Operation */
        public Builder operation(List<AcquiringGetPaymentOperationListItemModel> operation) {
            this.operation = operation;
            return this;
        }

        public AcquiringGetPaymentOperationListResponseModel build() {
            return new AcquiringGetPaymentOperationListResponseModel(this.operation);
        }
    }
}
