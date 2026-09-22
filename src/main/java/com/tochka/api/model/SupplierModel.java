package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SupplierModel
 *
 * @param phone Номер телефона поставщика. Например: "+7999999999"
 * @param name Наименование поставщика. Например: "ООО Альтер"
 * @param taxCode ИНН поставщика. Например: "660000000000"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SupplierModel(
        @JsonProperty("phone") String phone,
        @JsonProperty("name") String name,
        @JsonProperty("taxCode") String taxCode) {

    /** Строитель {@link SupplierModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .phone(this.phone)
                .name(this.name)
                .taxCode(this.taxCode);
    }

    /** Строитель {@link SupplierModel}. */
    public static final class Builder {

        private String phone;
        private String name;
        private String taxCode;

        /** Номер телефона поставщика. Например: "+7999999999" */
        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        /** Наименование поставщика. Например: "ООО Альтер" */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /** ИНН поставщика. Например: "660000000000" */
        public Builder taxCode(String taxCode) {
            this.taxCode = taxCode;
            return this;
        }

        public SupplierModel build() {
            return new SupplierModel(this.phone, this.name, this.taxCode);
        }
    }
}
