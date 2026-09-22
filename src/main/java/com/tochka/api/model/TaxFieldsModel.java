package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TaxFieldsModel
 *
 * @param originatorStatus Статус плательщика бюджетного платежа (optional)
 * @param kbk КБК (optional)
 * @param oktmo ОКТМО (optional)
 * @param base Основание налогового платежа (optional)
 * @param documentNumber Номер налогового документа (optional)
 * @param documentDate Дата налогового документа (optional)
 * @param type Вид платежа (optional)
 * @param field107 Налоговой период / код таможенного органа (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaxFieldsModel(
        @JsonProperty("originatorStatus") String originatorStatus,
        @JsonProperty("kbk") String kbk,
        @JsonProperty("oktmo") String oktmo,
        @JsonProperty("base") String base,
        @JsonProperty("documentNumber") String documentNumber,
        @JsonProperty("documentDate") com.fasterxml.jackson.databind.JsonNode documentDate,
        @JsonProperty("type") String type,
        @JsonProperty("field107") String field107) {

    /** Builder for {@link TaxFieldsModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .originatorStatus(this.originatorStatus)
                .kbk(this.kbk)
                .oktmo(this.oktmo)
                .base(this.base)
                .documentNumber(this.documentNumber)
                .documentDate(this.documentDate)
                .type(this.type)
                .field107(this.field107);
    }

    /** Builder for {@link TaxFieldsModel}. */
    public static final class Builder {

        private String originatorStatus;
        private String kbk;
        private String oktmo;
        private String base;
        private String documentNumber;
        private com.fasterxml.jackson.databind.JsonNode documentDate;
        private String type;
        private String field107;

        /** Статус плательщика бюджетного платежа */
        public Builder originatorStatus(String originatorStatus) {
            this.originatorStatus = originatorStatus;
            return this;
        }

        /** КБК */
        public Builder kbk(String kbk) {
            this.kbk = kbk;
            return this;
        }

        /** ОКТМО */
        public Builder oktmo(String oktmo) {
            this.oktmo = oktmo;
            return this;
        }

        /** Основание налогового платежа */
        public Builder base(String base) {
            this.base = base;
            return this;
        }

        /** Номер налогового документа */
        public Builder documentNumber(String documentNumber) {
            this.documentNumber = documentNumber;
            return this;
        }

        /** Дата налогового документа */
        public Builder documentDate(com.fasterxml.jackson.databind.JsonNode documentDate) {
            this.documentDate = documentDate;
            return this;
        }

        /** Вид платежа */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /** Налоговой период / код таможенного органа */
        public Builder field107(String field107) {
            this.field107 = field107;
            return this;
        }

        public TaxFieldsModel build() {
            return new TaxFieldsModel(
                    this.originatorStatus,
                    this.kbk,
                    this.oktmo,
                    this.base,
                    this.documentNumber,
                    this.documentDate,
                    this.type,
                    this.field107);
        }
    }
}
