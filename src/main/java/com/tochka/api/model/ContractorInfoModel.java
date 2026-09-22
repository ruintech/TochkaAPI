package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ContractorInfoModel
 *
 * @param inn ИНН контрагента. Например: "660000000000" (необязательное)
 * @param name Наименование контрагента. Например: "Индивидуальный Предприниматель Тест" (необязательное)
 * @param kpp КПП контрагента. Например: "660000000" (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContractorInfoModel(
        @JsonProperty("inn") String inn,
        @JsonProperty("name") String name,
        @JsonProperty("kpp") String kpp) {

    /** Строитель {@link ContractorInfoModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .inn(this.inn)
                .name(this.name)
                .kpp(this.kpp);
    }

    /** Строитель {@link ContractorInfoModel}. */
    public static final class Builder {

        private String inn;
        private String name;
        private String kpp;

        /** ИНН контрагента. Например: "660000000000" */
        public Builder inn(String inn) {
            this.inn = inn;
            return this;
        }

        /** Наименование контрагента. Например: "Индивидуальный Предприниматель Тест" */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /** КПП контрагента. Например: "660000000" */
        public Builder kpp(String kpp) {
            this.kpp = kpp;
            return this;
        }

        public ContractorInfoModel build() {
            return new ContractorInfoModel(this.inn, this.name, this.kpp);
        }
    }
}
