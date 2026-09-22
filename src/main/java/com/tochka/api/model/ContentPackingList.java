package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ContentPackingList
 *
 * @param packingList Содержимое товарной накладной
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ContentPackingList(
        @JsonProperty("PackingList") PackingListModel packingList) {

    /** Строитель {@link ContentPackingList}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .packingList(this.packingList);
    }

    /** Строитель {@link ContentPackingList}. */
    public static final class Builder {

        private PackingListModel packingList;

        /** Содержимое товарной накладной */
        public Builder packingList(PackingListModel packingList) {
            this.packingList = packingList;
            return this;
        }

        public ContentPackingList build() {
            return new ContentPackingList(this.packingList);
        }
    }
}
