package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Envelope;
import com.tochka.api.http.Transport;
import com.tochka.api.model.SBPPayment;
import com.tochka.api.model.SBPRefund;
import com.tochka.api.model.SBPRefundRequestResponse;
import com.tochka.api.model.SBPRefundStatus;
import java.util.List;

/**
 * SBP: refunds of payments accepted via QR codes.
 *
 * <p>An instance is available from {@link com.tochka.api.TochkaClient}.
 */
public final class SbpRefundsApi {

    private final Transport transport;

    public SbpRefundsApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Get Payments. Метод возвращает список платежей по СБП за период. По нему можно найти платёж и
     * получить его идентификатор для возврата. При поиске за прошедшие дни обязательно передавайте
     * {@code fromDate} — начальную дату периода. Без него поиск вернёт результаты только за вчера и
     * сегодня. Как найти платёж для возврата — в разделе «Работа с возвратами через СБП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-vozvratami)».
     *
     * <p>Required permissions: {@code ReadSBPData}.
     *
     * @param customerCode Уникальный код клиента
     */
    public List<SBPPayment> getPayments(String customerCode) {
        return transport.request("GET", "/sbp/v1.0/get-sbp-payments")
                .query("customerCode", customerCode)
                .unwrap("Data", "Payments")
                .as(new TypeReference<List<SBPPayment>>() {});
    }

    /** Optional parameters of {@code GetPayments}. */
    public record GetPaymentsOptions(
            String qrcId,
            String fromDate,
            String toDate,
            Integer page,
            Integer perPage) {

        public static Builder builder() {
            return new Builder();
        }

        /** Builder for {@link GetPaymentsOptions}. */
        public static final class Builder {

            private String qrcId;
            private String fromDate;
            private String toDate;
            private Integer page;
            private Integer perPage;

            /** ID qr-кода для фильтрации */
            public Builder qrcId(String qrcId) {
                this.qrcId = qrcId;
                return this;
            }

            /** Начало периода для запроса статусов платежей. При отсутствии параметра в запросе будет
            задано дефолтное значение */
            public Builder fromDate(String fromDate) {
                this.fromDate = fromDate;
                return this;
            }

            /** Конец периода для запроса статусов платежей */
            public Builder toDate(String toDate) {
                this.toDate = toDate;
                return this;
            }

            /** Номер страницы */
            public Builder page(Integer page) {
                this.page = page;
                return this;
            }

            /** Количество записей на странице */
            public Builder perPage(Integer perPage) {
                this.perPage = perPage;
                return this;
            }

            public GetPaymentsOptions build() {
                return new GetPaymentsOptions(this.qrcId, this.fromDate, this.toDate, this.page, this.perPage);
            }
        }
    }

    /**
     * Get Payments. Метод возвращает список платежей по СБП за период. По нему можно найти платёж и
     * получить его идентификатор для возврата. При поиске за прошедшие дни обязательно передавайте
     * {@code fromDate} — начальную дату периода. Без него поиск вернёт результаты только за вчера и
     * сегодня. Как найти платёж для возврата — в разделе «Работа с возвратами через СБП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-vozvratami)».
     *
     * <p>Required permissions: {@code ReadSBPData}.
     *
     * @param customerCode Уникальный код клиента
     * @param options optional query parameters; {@code null} means defaults
     */
    public List<SBPPayment> getPayments(String customerCode, GetPaymentsOptions options) {
        return transport.request("GET", "/sbp/v1.0/get-sbp-payments")
                .query("customerCode", customerCode)
                .query("qrcId", options == null ? null : options.qrcId())
                .query("fromDate", options == null ? null : options.fromDate())
                .query("toDate", options == null ? null : options.toDate())
                .query("page", options == null ? null : options.page())
                .query("perPage", options == null ? null : options.perPage())
                .unwrap("Data", "Payments")
                .as(new TypeReference<List<SBPPayment>>() {});
    }

    /**
     * Get Payments. Метод возвращает список платежей по СБП за период. По нему можно найти платёж и
     * получить его идентификатор для возврата. При поиске за прошедшие дни обязательно передавайте
     * {@code fromDate} — начальную дату периода. Без него поиск вернёт результаты только за вчера и
     * сегодня. Как найти платёж для возврата — в разделе «Работа с возвратами через СБП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-vozvratami)».
     *
     * <p>The customer code is taken from the client configuration ({@code TochkaClient.builder().customerCode(...)}).
     *
     * <p>Required permissions: {@code ReadSBPData}.
     */
    public List<SBPPayment> getPayments() {
        return transport.request("GET", "/sbp/v1.0/get-sbp-payments")
                .query("customerCode", requireCustomerCode())
                .unwrap("Data", "Payments")
                .as(new TypeReference<List<SBPPayment>>() {});
    }

    /**
     * Get Payments. Метод возвращает список платежей по СБП за период. По нему можно найти платёж и
     * получить его идентификатор для возврата. При поиске за прошедшие дни обязательно передавайте
     * {@code fromDate} — начальную дату периода. Без него поиск вернёт результаты только за вчера и
     * сегодня. Как найти платёж для возврата — в разделе «Работа с возвратами через СБП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-vozvratami)».
     *
     * <p>The customer code is taken from the client configuration ({@code TochkaClient.builder().customerCode(...)}).
     *
     * <p>Required permissions: {@code ReadSBPData}.
     *
     * @param options optional query parameters; {@code null} means defaults
     */
    public List<SBPPayment> getPayments(GetPaymentsOptions options) {
        return transport.request("GET", "/sbp/v1.0/get-sbp-payments")
                .query("customerCode", requireCustomerCode())
                .query("qrcId", options == null ? null : options.qrcId())
                .query("fromDate", options == null ? null : options.fromDate())
                .query("toDate", options == null ? null : options.toDate())
                .query("page", options == null ? null : options.page())
                .query("perPage", options == null ? null : options.perPage())
                .unwrap("Data", "Payments")
                .as(new TypeReference<List<SBPPayment>>() {});
    }

    /**
     * Get Refund Data. Метод показывает статус возврата: выполнен он или отклонён. Как отследить
     * возврат — в разделе «Работа с возвратами через СБП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-vozvratami)».
     *
     * <p>Required permissions: {@code ReadSBPData}.
     *
     * @param requestId ID запроса
     */
    public SBPRefundStatus getRefundData(String requestId) {
        return transport.request("GET", "/sbp/v1.0/refund/{request_id}")
                .path("request_id", requestId)
                .unwrap("Data")
                .as(SBPRefundStatus.class);
    }

    /**
     * Start Refund. Метод возвращает покупателю платёж, поступивший по СБП. Возврат бывает полным или
     * частичным, сумма не должна превышать сумму поступления. Если возвращаете деньги нерезиденту,
     * назначение платежа должно начинаться с {VO99020}. Возврат ошибочно полученной суммы {@code
     * transactionId}, где {@code transactionId} — идентификатор оригинальной операции. Как оформить
     * возврат по СБП — в разделе «Работа с возвратами через СБП
     * (/docs/tochka-api/opisanie-metodov/sbp-sistema-bystryh-platezhej/rabota-s-vozvratami)».
     *
     * <p>Required permissions: {@code EditSBPData}.
     *
     * @param request request body
     */
    public SBPRefundRequestResponse startRefund(SBPRefund request) {
        return transport.request("POST", "/sbp/v1.0/refund")
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data")
                .as(SBPRefundRequestResponse.class);
    }

    private String requireCustomerCode() {
        String code = transport.defaultCustomerCode();
        if (code == null || code.isBlank()) {
            throw new IllegalStateException("Не задан customerCode: передайте его параметром метода "
                    + "или задайте по умолчанию через TochkaClient.builder().customerCode(...)");
        }
        return code;
    }

}
