package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * ConsentCreateRequest
 *
 * @param data Data
 * @param risks Risks (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConsentCreateRequest(
        @JsonProperty("Data") ConsentCreateRequestModel data,
        @JsonProperty("Risks") Map<String, Object> risks) {

    /** Строитель {@link ConsentCreateRequest}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .data(this.data)
                .risks(this.risks);
    }

    /** Строитель {@link ConsentCreateRequest}. */
    public static final class Builder {

        private ConsentCreateRequestModel data;
        private Map<String, Object> risks;

        public Builder data(ConsentCreateRequestModel data) {
            this.data = data;
            return this;
        }

        /** Risks */
        public Builder risks(Map<String, Object> risks) {
            this.risks = risks;
            return this;
        }

        public ConsentCreateRequest build() {
            return new ConsentCreateRequest(this.data, this.risks);
        }
    }
}
