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

    /** Builder for {@link ContentPackingList}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .packingList(this.packingList);
    }

    /** Builder for {@link ContentPackingList}. */
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
