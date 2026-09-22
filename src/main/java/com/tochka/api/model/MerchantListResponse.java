package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * MerchantListResponse
 *
 * @param merchantList Merchantlist
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MerchantListResponse(
        @JsonProperty("MerchantList") List<Merchant> merchantList) {

    /** Builder for {@link MerchantListResponse}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .merchantList(this.merchantList);
    }

    /** Builder for {@link MerchantListResponse}. */
    public static final class Builder {

        private List<Merchant> merchantList;

        /** Merchantlist */
        public Builder merchantList(List<Merchant> merchantList) {
            this.merchantList = merchantList;
            return this;
        }

        public MerchantListResponse build() {
            return new MerchantListResponse(this.merchantList);
        }
    }
}
