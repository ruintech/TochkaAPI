package com.tochka.api.api;

import com.tochka.api.http.Envelope;
import com.tochka.api.http.Transport;
import com.tochka.api.model.B2BQrCode;
import com.tochka.api.model.RegisterB2BQRCode;
import com.tochka.api.model.RegisteredB2BQrCode;

/**
 * СБП: B2B QR-коды для приёма платежей от ИП и организаций.
 *
 * <p>Экземпляр доступен через {@link com.tochka.api.TochkaClient}.
 */
public final class SbpB2bQrCodesApi {

    private final Transport transport;

    public SbpB2bQrCodesApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Get B2B Qr Code. Метод возвращает данные одного B2B QR-кода по его {@code qrcId}. Про B2B
     * QR-коды — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param qrcId Идентификатор QR-кода в СБП
     */
    public B2BQrCode getB2BQrCode(String qrcId) {
        return transport.request("GET", "/sbp/v1.0/b2b-qr-code/{qrcId}")
                .path("qrcId", qrcId)
                .unwrap("Data")
                .as(B2BQrCode.class);
    }

    /** Необязательные параметры метода {@code GetB2BQrCode}. */
    public record GetB2BQrCodeOptions(
            Integer width,
            Integer height) {

        public static Builder builder() {
            return new Builder();
        }

        /** Строитель {@link GetB2BQrCodeOptions}. */
        public static final class Builder {

            private Integer width;
            private Integer height;

            /** Ширина изображения (по умолчанию: 300) */
            public Builder width(Integer width) {
                this.width = width;
                return this;
            }

            /** Высота изображения (по умолчанию: 300) */
            public Builder height(Integer height) {
                this.height = height;
                return this;
            }

            public GetB2BQrCodeOptions build() {
                return new GetB2BQrCodeOptions(this.width, this.height);
            }
        }
    }

    /**
     * Get B2B Qr Code. Метод возвращает данные одного B2B QR-кода по его {@code qrcId}. Про B2B
     * QR-коды — в разделе «Работа с QR-кодами
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code ReadSBPData}.
     *
     * @param qrcId Идентификатор QR-кода в СБП
     * @param options необязательные параметры запроса; {@code null} — значения по умолчанию
     */
    public B2BQrCode getB2BQrCode(String qrcId, GetB2BQrCodeOptions options) {
        return transport.request("GET", "/sbp/v1.0/b2b-qr-code/{qrcId}")
                .path("qrcId", qrcId)
                .query("width", options == null ? null : options.width())
                .query("height", options == null ? null : options.height())
                .unwrap("Data")
                .as(B2BQrCode.class);
    }

    /**
     * Register B2B Qr Code. Метод создаёт B2B QR-код для приёма платежей от организаций и ИП, не от
     * физлиц. Сумма для такого кода обязательна. Как работать с B2B QR-кодами — в разделе «Работа с
     * QR-кодами (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-qr-kodami)».
     *
     * <p>Требуемые разрешения: {@code EditSBPData}.
     *
     * @param merchantId Идентификатор ТСП
     * @param accountId Уникальный и неизменный идентификатор счёта юрлица
     * @param request тело запроса
     */
    public RegisteredB2BQrCode registerB2BQrCode(String merchantId, String accountId, RegisterB2BQRCode request) {
        return transport.request("POST", "/sbp/v1.0/b2b-qr-code/merchant/{merchantId}/{accountId}")
                .path("merchantId", merchantId)
                .path("accountId", accountId)
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data")
                .as(RegisteredB2BQrCode.class);
    }

}
