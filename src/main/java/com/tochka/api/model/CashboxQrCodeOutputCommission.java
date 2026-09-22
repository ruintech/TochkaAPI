package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * CashboxQrCodeOutputCommission
 *
 * @param mcc MCC код (необязательное)
 * @param percent Размер комиссии в процентах (необязательное)
 * @param description Описание (необязательное)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CashboxQrCodeOutputCommission(
        @JsonProperty("mcc") String mcc,
        @JsonProperty("percent") BigDecimal percent,
        @JsonProperty("description") String description) {

    /** Строитель {@link CashboxQrCodeOutputCommission}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .mcc(this.mcc)
                .percent(this.percent)
                .description(this.description);
    }

    /** Строитель {@link CashboxQrCodeOutputCommission}. */
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
