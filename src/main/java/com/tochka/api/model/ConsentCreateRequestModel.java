package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * ConsentCreateRequestModel
 *
 * @param status Статус разрешения. Example: "AwaitingAuthorisation" (optional)
 * @param creationDateTime Дата и время создания статуса ресурса. Используется стандарт ISO8601. Example:
 *        "2019-01-01T06:06:06.364+00:00" (optional)
 * @param statusUpdateDateTime Дата и время обновления статуса ресурса. Используется стандарт ISO8601. Example:
 *        "2019-01-01T06:06:06.364+00:00" (optional)
 * @param permissions Указание типов данных доступа.. Example: ["ReadAccountsBasic"]
 * @param expirationDateTime Дата и время истечения срока действия разрешений. Используется стандарт ISO8601. Example:
 *        "2019-01-01T06:06:06.364+00:00" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConsentCreateRequestModel(
        @JsonProperty("status") String status,
        @JsonProperty("creationDateTime") OffsetDateTime creationDateTime,
        @JsonProperty("statusUpdateDateTime") OffsetDateTime statusUpdateDateTime,
        @JsonProperty("permissions") List<ExternalConsentTypeEnum> permissions,
        @JsonProperty("expirationDateTime") OffsetDateTime expirationDateTime) {

    /** Builder for {@link ConsentCreateRequestModel}. */
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
                .expirationDateTime(this.expirationDateTime);
    }

    /** Builder for {@link ConsentCreateRequestModel}. */
    public static final class Builder {

        private String status;
        private OffsetDateTime creationDateTime;
        private OffsetDateTime statusUpdateDateTime;
        private List<ExternalConsentTypeEnum> permissions;
        private OffsetDateTime expirationDateTime;

        /** Статус разрешения. Example: "AwaitingAuthorisation" */
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        /** Дата и время создания статуса ресурса. Используется стандарт ISO8601. Example:
        "2019-01-01T06:06:06.364+00:00" */
        public Builder creationDateTime(OffsetDateTime creationDateTime) {
            this.creationDateTime = creationDateTime;
            return this;
        }

        /** Дата и время обновления статуса ресурса. Используется стандарт ISO8601. Example:
        "2019-01-01T06:06:06.364+00:00" */
        public Builder statusUpdateDateTime(OffsetDateTime statusUpdateDateTime) {
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
        public Builder expirationDateTime(OffsetDateTime expirationDateTime) {
            this.expirationDateTime = expirationDateTime;
            return this;
        }

        public ConsentCreateRequestModel build() {
            return new ConsentCreateRequestModel(
                    this.status,
                    this.creationDateTime,
                    this.statusUpdateDateTime,
                    this.permissions,
                    this.expirationDateTime);
        }
    }
}
