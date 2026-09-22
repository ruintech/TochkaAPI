package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ContractorInfoModel
 *
 * @param inn ИНН контрагента. Example: "660000000000" (optional)
 * @param name Наименование контрагента. Example: "Индивидуальный Предприниматель Тест" (optional)
 * @param kpp КПП контрагента. Example: "660000000" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractorInfoModel(
        @JsonProperty("inn") String inn,
        @JsonProperty("name") String name,
        @JsonProperty("kpp") String kpp) {

    /** Builder for {@link ContractorInfoModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .inn(this.inn)
                .name(this.name)
                .kpp(this.kpp);
    }

    /** Builder for {@link ContractorInfoModel}. */
    public static final class Builder {

        private String inn;
        private String name;
        private String kpp;

        /** ИНН контрагента. Example: "660000000000" */
        public Builder inn(String inn) {
            this.inn = inn;
            return this;
        }

        /** Наименование контрагента. Example: "Индивидуальный Предприниматель Тест" */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /** КПП контрагента. Example: "660000000" */
        public Builder kpp(String kpp) {
            this.kpp = kpp;
            return this;
        }

        public ContractorInfoModel build() {
            return new ContractorInfoModel(this.inn, this.name, this.kpp);
        }
    }
}
