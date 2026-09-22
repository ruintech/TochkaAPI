package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DigitalRubleWalletModel
 *
 * @param createdAt Время регистрации. Например: "2019-01-01T06:06:06.364+00:00"
 * @param walletId Идентификатор счета цифрового рубля. Например:
 *        "g.ru.cbrdc.wlt.clt.cdbab25e-a448-476a-922a-bd0de7864819"
 * @param bankCode БИК банка. Например: "044525104"
 * @param walletStatus Статус счета цифрового рубля. Например: "ACTV"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DigitalRubleWalletModel(
        @JsonProperty("createdAt") String createdAt,
        @JsonProperty("walletId") String walletId,
        @JsonProperty("bankCode") String bankCode,
        @JsonProperty("walletStatus") String walletStatus) {

    /** Строитель {@link DigitalRubleWalletModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .createdAt(this.createdAt)
                .walletId(this.walletId)
                .bankCode(this.bankCode)
                .walletStatus(this.walletStatus);
    }

    /** Строитель {@link DigitalRubleWalletModel}. */
    public static final class Builder {

        private String createdAt;
        private String walletId;
        private String bankCode;
        private String walletStatus;

        /** Время регистрации. Например: "2019-01-01T06:06:06.364+00:00" */
        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /** Идентификатор счета цифрового рубля. Например:
        "g.ru.cbrdc.wlt.clt.cdbab25e-a448-476a-922a-bd0de7864819" */
        public Builder walletId(String walletId) {
            this.walletId = walletId;
            return this;
        }

        /** БИК банка. Например: "044525104" */
        public Builder bankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        /** Статус счета цифрового рубля. Например: "ACTV" */
        public Builder walletStatus(String walletStatus) {
            this.walletStatus = walletStatus;
            return this;
        }

        public DigitalRubleWalletModel build() {
            return new DigitalRubleWalletModel(this.createdAt, this.walletId, this.bankCode, this.walletStatus);
        }
    }
}
