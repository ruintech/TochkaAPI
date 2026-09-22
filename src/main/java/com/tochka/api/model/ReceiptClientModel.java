package com.tochka.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ReceiptClientModel
 *
 * @param name Для юрлица — название организации, для ИП и физического лица — ФИО. Example: "Иванов Иван
 *        Иванович" (optional)
 * @param email Email покупателя, на который будет отправлен чек. Example: "ivanov&#64;mail.com"
 * @param phone Телефон пользователя для отправки чека.. Example: "+7999999999" (optional)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReceiptClientModel(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("phone") String phone) {

    /** Builder for {@link ReceiptClientModel}. */
    public static Builder builder() {
        return new Builder();
    }

    /** A builder pre-filled with the values of this object. */
    public Builder toBuilder() {
        return new Builder()
                .name(this.name)
                .email(this.email)
                .phone(this.phone);
    }

    /** Builder for {@link ReceiptClientModel}. */
    public static final class Builder {

        private String name;
        private String email;
        private String phone;

        /** Для юрлица — название организации, для ИП и физического лица — ФИО. Example: "Иванов Иван
        Иванович" */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /** Email покупателя, на который будет отправлен чек. Example: "ivanov&#64;mail.com" */
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        /** Телефон пользователя для отправки чека.. Example: "+7999999999" */
        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public ReceiptClientModel build() {
            return new ReceiptClientModel(this.name, this.email, this.phone);
        }
    }
}
