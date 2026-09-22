package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

/**
 * ShipmentDocumentModel
 *
 * @param name Наименование документа об отгрузке
 * @param date Дата документа об отгрузке. Например: "2010-10-29"
 * @param number Номер документа об отгрузке
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ShipmentDocumentModel(
        @JsonProperty("name") String name,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("number") String number) {

    /** Строитель {@link ShipmentDocumentModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .name(this.name)
                .date(this.date)
                .number(this.number);
    }

    /** Строитель {@link ShipmentDocumentModel}. */
    public static final class Builder {

        private String name;
        private LocalDate date;
        private String number;

        /** Наименование документа об отгрузке */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /** Дата документа об отгрузке. Например: "2010-10-29" */
        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        /** Номер документа об отгрузке */
        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public ShipmentDocumentModel build() {
            return new ShipmentDocumentModel(this.name, this.date, this.number);
        }
    }
}
