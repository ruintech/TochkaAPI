package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * CashboxQrCodeOutputCommission
 *
 * @param mcc MCC код (optional)
 * @param percent Размер комиссии в процентах (optional)
 * @param description Описание (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CashboxQrCodeOutputCommission(
        @JsonProperty("mcc") String mcc,
        @JsonProperty("percent") BigDecimal percent,
        @JsonProperty("description") String description) {

    /** Builder for {@link CashboxQrCodeOutputCommission}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .mcc(this.mcc)
                .percent(this.percent)
                .description(this.description);
    }

    /** Builder for {@link CashboxQrCodeOutputCommission}. */
    public static final class Builder {

        private String mcc;
        private BigDecimal percent;
        private String description;

        /** MCC код */
        public Builder mcc(String mcc) {
            this.mcc = mcc;
            return this;
        }

        /** Размер комиссии в процентах */
        public Builder percent(BigDecimal percent) {
            this.percent = percent;
            return this;
        }

        /** Описание */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public CashboxQrCodeOutputCommission build() {
            return new CashboxQrCodeOutputCommission(this.mcc, this.percent, this.description);
        }
    }
}
