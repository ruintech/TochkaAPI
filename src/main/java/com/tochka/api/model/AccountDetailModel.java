package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AccountDetailModel
 *
 * @param schemeName Наименование схемы идентификации счёта. Например: "RU.CBR.AccountNumber"
 * @param identification Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
 * @param name Название идентификатора счёта. Например: "Основной текущий счёт"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AccountDetailModel(
        @JsonProperty("schemeName") String schemeName,
        @JsonProperty("identification") String identification,
        @JsonProperty("name") String name) {

    /** Строитель {@link AccountDetailModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .schemeName(this.schemeName)
                .identification(this.identification)
                .name(this.name);
    }

    /** Строитель {@link AccountDetailModel}. */
    public static final class Builder {

        private String schemeName;
        private String identification;
        private String name;

        /** Наименование схемы идентификации счёта. Например: "RU.CBR.AccountNumber" */
        public Builder schemeName(String schemeName) {
            this.schemeName = schemeName;
            return this;
        }

        /** Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104" */
        public Builder identification(String identification) {
            this.identification = identification;
            return this;
        }

        /** Название идентификатора счёта. Например: "Основной текущий счёт" */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public AccountDetailModel build() {
            return new AccountDetailModel(this.schemeName, this.identification, this.name);
        }
    }
}
