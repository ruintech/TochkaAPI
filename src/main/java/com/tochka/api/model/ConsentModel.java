package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * ConsentModel
 *
 * @param status Статус разрешения. Example: "AwaitingAuthorisation" (optional)
 * @param creationDateTime Дата и время создания статуса ресурса. Используется стандарт ISO8601. Example:
 *        "2019-01-01T06:06:06.364+00:00" (optional)
 * @param statusUpdateDateTime Дата и время обновления статуса ресурса. Используется стандарт ISO8601. Example:
 *        "2019-01-01T06:06:06.364+00:00" (optional)
 * @param permissions Указание типов данных доступа.. Example: ["ReadAccountsBasic"]
 * @param expirationDateTime Дата и время истечения срока действия разрешений. Используется стандарт ISO8601. Example:
 *        "2019-01-01T06:06:06.364+00:00" (optional)
 * @param consentId Уникальный идентификатор, предназначенный для идентификации разрешения. Example:
 *        "tochka-intent-88379"
 * @param customerCode Уникальный код клиента. Example: "300000092" (optional)
 * @param applicationName Название приложения. Example: "Test" (optional)
 * @param clientId ID приложения в oAuth. Example: "Test" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConsentModel(
        @JsonProperty("status") ConsentStatusEnum status,
        @JsonProperty("creationDateTime") String creationDateTime,
        @JsonProperty("statusUpdateDateTime") String statusUpdateDateTime,
        @JsonProperty("permissions") List<ExternalConsentTypeEnum> permissions,
        @JsonProperty("expirationDateTime") String expirationDateTime,
        @JsonProperty("consentId") String consentId,
        @JsonProperty("customerCode") String customerCode,
        @JsonProperty("applicationName") String applicationName,
        @JsonProperty("clientId") String clientId) {

    /** Builder for {@link ConsentModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .status(this.status)
                .creationDateTime(this.creationDateTime)
                .statusUpdateDateTime(this.statusUpdateDateTime)
                .permissions(this.permissions)
                .expirationDateTime(this.expirationDateTime)
                .consentId(this.consentId)
                .customerCode(this.customerCode)
                .applicationName(this.applicationName)
                .clientId(this.clientId);
    }

    /** Builder for {@link ConsentModel}. */
    public static final class Builder {

        private ConsentStatusEnum status;
        private String creationDateTime;
        private String statusUpdateDateTime;
        private List<ExternalConsentTypeEnum> permissions;
        private String expirationDateTime;
        private String consentId;
        private String customerCode;
        private String applicationName;
        private String clientId;

        /** Статус разрешения. Example: "AwaitingAuthorisation" */
        public Builder status(ConsentStatusEnum status) {
            this.status = status;
            return this;
        }

        /** Дата и время создания статуса ресурса. Используется стандарт ISO8601. Example:
        "2019-01-01T06:06:06.364+00:00" */
        public Builder creationDateTime(String creationDateTime) {
            this.creationDateTime = creationDateTime;
            return this;
        }

        /** Дата и время обновления статуса ресурса. Используется стандарт ISO8601. Example:
        "2019-01-01T06:06:06.364+00:00" */
        public Builder statusUpdateDateTime(String statusUpdateDateTime) {
            this.statusUpdateDateTime = statusUpdateDateTime;
            return this;
        }

        /** Указание типов данных доступа.. Example: ["ReadAccountsBasic"] */
        public Builder permissions(List<ExternalConsentTypeEnum> permissions) {
            this.permissions = permissions;
            return this;
        }

        /** Дата и время истечения срока действия разрешений. Используется стандарт ISO8601. Example:
        "2019-01-01T06:06:06.364+00:00" */
        public Builder expirationDateTime(String expirationDateTime) {
            this.expirationDateTime = expirationDateTime;
            return this;
        }

        /** Уникальный идентификатор, предназначенный для идентификации разрешения. Example:
        "tochka-intent-88379" */
        public Builder consentId(String consentId) {
            this.consentId = consentId;
            return this;
        }

        /** Уникальный код клиента. Example: "300000092" */
        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        /** Название приложения. Example: "Test" */
        public Builder applicationName(String applicationName) {
            this.applicationName = applicationName;
            return this;
        }

        /** ID приложения в oAuth. Example: "Test" */
        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public ConsentModel build() {
            return new ConsentModel(
                    this.status,
                    this.creationDateTime,
                    this.statusUpdateDateTime,
                    this.permissions,
                    this.expirationDateTime,
                    this.consentId,
                    this.customerCode,
                    this.applicationName,
                    this.clientId);
        }
    }
}
