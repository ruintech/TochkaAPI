package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GetCashboxQrCodeStatusResponseModel
 *
 * @param status Статус операции
 * @param paramsId Идентификатор активных значений параметров QR-кода
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetCashboxQrCodeStatusResponseModel(
        @JsonProperty("status") SBPCashboxQrCodeStatus status,
        @JsonProperty("paramsId") String paramsId) {

    /** Строитель {@link GetCashboxQrCodeStatusResponseModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** Копия строителя, заполненная значениями этого объекта. */
    public Builder toBuilder() {
        return new Builder()
                .status(this.status)
                .paramsId(this.paramsId);
    }

    /** Строитель {@link GetCashboxQrCodeStatusResponseModel}. */
    public static final class Builder {

        private SBPCashboxQrCodeStatus status;
        private String paramsId;

        /** Статус операции */
        public Builder status(SBPCashboxQrCodeStatus status) {
            this.status = status;
            return this;
        }

        /** Идентификатор активных значений параметров QR-кода */
        public Builder paramsId(String paramsId) {
            this.paramsId = paramsId;
            return this;
        }

        public GetCashboxQrCodeStatusResponseModel build() {
            return new GetCashboxQrCodeStatusResponseModel(this.status, this.paramsId);
        }
    }
}
