package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ChangeCashboxQRCodeAccountResponseModel
 *
 * @param accountId Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104"
 * @param qrcId Идентификатор QR-кода в СБП. Например: "AS000000000000000000000000000001"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChangeCashboxQRCodeAccountResponseModel(
        @JsonProperty("accountId") String accountId,
        @JsonProperty("qrcId") String qrcId) {

    /** Строитель {@link ChangeCashboxQRCodeAccountResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .accountId(this.accountId)
                .qrcId(this.qrcId);
    }

    /** Строитель {@link ChangeCashboxQRCodeAccountResponseModel}. */
    public static final class Builder {

        private String accountId;
        private String qrcId;

        /** Уникальный и неизменный идентификатор счёта. Например: "40817810802000000008/044525104" */
        public Builder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        /** Идентификатор QR-кода в СБП. Например: "AS000000000000000000000000000001" */
        public Builder qrcId(String qrcId) {
            this.qrcId = qrcId;
            return this;
        }

        public ChangeCashboxQRCodeAccountResponseModel build() {
            return new ChangeCashboxQRCodeAccountResponseModel(this.accountId, this.qrcId);
        }
    }
}
