package com.tochka.api.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tochka.api.http.Envelope;
import com.tochka.api.http.Page;
import com.tochka.api.http.Transport;
import com.tochka.api.model.AcquiringCreatePaymentOperationRequestModel;
import com.tochka.api.model.AcquiringCreatePaymentOperationResponseModel;
import com.tochka.api.model.AcquiringCreatePaymentOperationWithReceiptRequestModel;
import com.tochka.api.model.AcquiringCreatePaymentOperationWithReceiptResponseModel;
import com.tochka.api.model.AcquiringGetPaymentOperationListItemModel;
import com.tochka.api.model.AcquiringPaymentOperationRefundModel;
import com.tochka.api.model.AcquiringPaymentRegistryItemModel;
import com.tochka.api.model.AcquiringPaymentStatus;
import com.tochka.api.model.AcquiringRetailerModel;
import java.math.BigDecimal;
import java.util.List;

/**
 * Internet acquiring: payment links, refunds, registry and retailers.
 *
 * <p>An instance is available from {@link com.tochka.api.TochkaClient}.
 */
public final class AcquiringApi {

    private final Transport transport;

    public AcquiringApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Capture Payment. Метод для списания средств при двухэтапной оплате
     *
     * <p>Required permissions: {@code MakeAcquiringOperation}.
     *
     * @param operationId Идентификатор подписки
     */
    public Boolean capturePayment(String operationId) {
        return transport.request("POST", "/acquiring/v1.0/payments/{operationId}/capture")
                .path("operationId", operationId)
                .unwrap("Data", "result")
                .as(Boolean.class);
    }

    /**
     * Create Payment Operation. Метод для создания ссылки на оплату
     *
     * <p>Required permissions: {@code MakeAcquiringOperation}.
     *
     * @param request request body
     */
    public AcquiringCreatePaymentOperationResponseModel createPaymentOperation(AcquiringCreatePaymentOperationRequestModel request) {
        return transport.request("POST", "/acquiring/v1.0/payments")
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data")
                .as(AcquiringCreatePaymentOperationResponseModel.class);
    }

    /**
     * Create Payment Operation With Receipt. Метод для создания ссылки на оплату и отправки чека
     *
     * <p>Required permissions: {@code MakeAcquiringOperation}.
     *
     * @param request request body
     */
    public AcquiringCreatePaymentOperationWithReceiptResponseModel createPaymentOperationWithReceipt(AcquiringCreatePaymentOperationWithReceiptRequestModel request) {
        return transport.request("POST", "/acquiring/v1.0/payments_with_receipt")
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data")
                .as(AcquiringCreatePaymentOperationWithReceiptResponseModel.class);
    }

    /**
     * Get Payment Operation Info. Метод для получения информации о конкретной операции - *CREATED* -
     * Операция создана - *APPROVED* - Операция одобрена (оплата прошла успешно) - *ON-REFUND* -
     * Операция заблокирована на время выполнения возврата - *REFUNDED* - Осуществлен возврат -
     * *EXPIRED* - Истек срок действия
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     *
     * @param operationId Идентификатор платежа
     */
    public List<AcquiringGetPaymentOperationListItemModel> getPaymentOperationInfo(String operationId) {
        return transport.request("GET", "/acquiring/v1.0/payments/{operationId}")
                .path("operationId", operationId)
                .unwrap("Data", "Operation")
                .as(new TypeReference<List<AcquiringGetPaymentOperationListItemModel>>() {});
    }

    /**
     * Get Payment Operation List. Метод для получения списка операций - *CREATED* - Операция создана -
     * *APPROVED* - Операция одобрена (оплата прошла успешно) - *ON-REFUND* - Операция заблокирована на
     * время выполнения возврата - *REFUNDED* - Осуществлен возврат - *EXPIRED* - Истек срок действия
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     *
     * @param customerCode Уникальный код клиента
     */
    public List<AcquiringGetPaymentOperationListItemModel> getPaymentOperationList(String customerCode) {
        return transport.request("GET", "/acquiring/v1.0/payments")
                .query("customerCode", customerCode)
                .unwrap("Data", "Operation")
                .as(new TypeReference<List<AcquiringGetPaymentOperationListItemModel>>() {});
    }

    /** Optional parameters of {@code GetPaymentOperationList}. */
    public record GetPaymentOperationListOptions(
            String fromDate,
            String toDate,
            Integer page,
            Integer perPage,
            AcquiringPaymentStatus status) {

        public static Builder builder() {
            return new Builder();
        }

        /** Builder for {@link GetPaymentOperationListOptions}. */
        public static final class Builder {

            private String fromDate;
            private String toDate;
            private Integer page;
            private Integer perPage;
            private AcquiringPaymentStatus status;

            /** Начало периода создания операций */
            public Builder fromDate(String fromDate) {
                this.fromDate = fromDate;
                return this;
            }

            /** Конец периода создания операций */
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

            /** Статус операции */
            public Builder status(AcquiringPaymentStatus status) {
                this.status = status;
                return this;
            }

            public GetPaymentOperationListOptions build() {
                return new GetPaymentOperationListOptions(this.fromDate, this.toDate, this.page, this.perPage, this.status);
            }
        }
    }

    /**
     * Get Payment Operation List. Метод для получения списка операций - *CREATED* - Операция создана -
     * *APPROVED* - Операция одобрена (оплата прошла успешно) - *ON-REFUND* - Операция заблокирована на
     * время выполнения возврата - *REFUNDED* - Осуществлен возврат - *EXPIRED* - Истек срок действия
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     *
     * @param customerCode Уникальный код клиента
     * @param options optional query parameters; {@code null} means defaults
     */
    public List<AcquiringGetPaymentOperationListItemModel> getPaymentOperationList(String customerCode, GetPaymentOperationListOptions options) {
        return transport.request("GET", "/acquiring/v1.0/payments")
                .query("customerCode", customerCode)
                .query("fromDate", options == null ? null : options.fromDate())
                .query("toDate", options == null ? null : options.toDate())
                .query("page", options == null ? null : options.page())
                .query("perPage", options == null ? null : options.perPage())
                .query("status", options == null ? null : options.status())
                .unwrap("Data", "Operation")
                .as(new TypeReference<List<AcquiringGetPaymentOperationListItemModel>>() {});
    }

    /**
     * Get Payment Operation List. Метод для получения списка операций - *CREATED* - Операция создана -
     * *APPROVED* - Операция одобрена (оплата прошла успешно) - *ON-REFUND* - Операция заблокирована на
     * время выполнения возврата - *REFUNDED* - Осуществлен возврат - *EXPIRED* - Истек срок действия
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     *
     * @param customerCode Уникальный код клиента
     * @param options optional query parameters; {@code null} means defaults
     */
    public Page<AcquiringGetPaymentOperationListItemModel> getPaymentOperationListPage(String customerCode, GetPaymentOperationListOptions options) {
        return transport.request("GET", "/acquiring/v1.0/payments")
                .query("customerCode", customerCode)
                .query("fromDate", options == null ? null : options.fromDate())
                .query("toDate", options == null ? null : options.toDate())
                .query("page", options == null ? null : options.page())
                .query("perPage", options == null ? null : options.perPage())
                .query("status", options == null ? null : options.status())
                .unwrap("Data", "Operation")
                .asPage(new TypeReference<List<AcquiringGetPaymentOperationListItemModel>>() {});
    }

    /**
     * Get Payment Operation List. Метод для получения списка операций - *CREATED* - Операция создана -
     * *APPROVED* - Операция одобрена (оплата прошла успешно) - *ON-REFUND* - Операция заблокирована на
     * время выполнения возврата - *REFUNDED* - Осуществлен возврат - *EXPIRED* - Истек срок действия
     *
     * <p>The customer code is taken from the client configuration ({@code TochkaClient.builder().customerCode(...)}).
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     */
    public List<AcquiringGetPaymentOperationListItemModel> getPaymentOperationList() {
        return transport.request("GET", "/acquiring/v1.0/payments")
                .query("customerCode", requireCustomerCode())
                .unwrap("Data", "Operation")
                .as(new TypeReference<List<AcquiringGetPaymentOperationListItemModel>>() {});
    }

    /**
     * Get Payment Operation List. Метод для получения списка операций - *CREATED* - Операция создана -
     * *APPROVED* - Операция одобрена (оплата прошла успешно) - *ON-REFUND* - Операция заблокирована на
     * время выполнения возврата - *REFUNDED* - Осуществлен возврат - *EXPIRED* - Истек срок действия
     *
     * <p>The customer code is taken from the client configuration ({@code TochkaClient.builder().customerCode(...)}).
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     *
     * @param options optional query parameters; {@code null} means defaults
     */
    public List<AcquiringGetPaymentOperationListItemModel> getPaymentOperationList(GetPaymentOperationListOptions options) {
        return transport.request("GET", "/acquiring/v1.0/payments")
                .query("customerCode", requireCustomerCode())
                .query("fromDate", options == null ? null : options.fromDate())
                .query("toDate", options == null ? null : options.toDate())
                .query("page", options == null ? null : options.page())
                .query("perPage", options == null ? null : options.perPage())
                .query("status", options == null ? null : options.status())
                .unwrap("Data", "Operation")
                .as(new TypeReference<List<AcquiringGetPaymentOperationListItemModel>>() {});
    }

    /**
     * Get Payment Operation List. Метод для получения списка операций - *CREATED* - Операция создана -
     * *APPROVED* - Операция одобрена (оплата прошла успешно) - *ON-REFUND* - Операция заблокирована на
     * время выполнения возврата - *REFUNDED* - Осуществлен возврат - *EXPIRED* - Истек срок действия
     *
     * <p>The customer code is taken from the client configuration ({@code TochkaClient.builder().customerCode(...)}).
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     *
     * @param options optional query parameters; {@code null} means defaults
     */
    public Page<AcquiringGetPaymentOperationListItemModel> getPaymentOperationListPage(GetPaymentOperationListOptions options) {
        return transport.request("GET", "/acquiring/v1.0/payments")
                .query("customerCode", requireCustomerCode())
                .query("fromDate", options == null ? null : options.fromDate())
                .query("toDate", options == null ? null : options.toDate())
                .query("page", options == null ? null : options.page())
                .query("perPage", options == null ? null : options.perPage())
                .query("status", options == null ? null : options.status())
                .unwrap("Data", "Operation")
                .asPage(new TypeReference<List<AcquiringGetPaymentOperationListItemModel>>() {});
    }

    /**
     * Get Payment Registry. Метод для получения реестра платежей по интернет-эквайрингу
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     *
     * @param customerCode Уникальный код клиента
     * @param merchantId Идентификатор торговой точки в интернет-эквайринге
     * @param date Дата реестра
     */
    public List<AcquiringPaymentRegistryItemModel> getPaymentRegistry(String customerCode, String merchantId, String date) {
        return transport.request("GET", "/acquiring/v1.0/registry")
                .query("customerCode", customerCode)
                .query("merchantId", merchantId)
                .query("date", date)
                .unwrap("Data", "Registry")
                .as(new TypeReference<List<AcquiringPaymentRegistryItemModel>>() {});
    }

    /** Optional parameters of {@code GetPaymentRegistry}. */
    public record GetPaymentRegistryOptions(
            String paymentId) {

        public static Builder builder() {
            return new Builder();
        }

        /** Builder for {@link GetPaymentRegistryOptions}. */
        public static final class Builder {

            private String paymentId;

            /** Уникальный идентификатор платежа, по которому произошла транзакция */
            public Builder paymentId(String paymentId) {
                this.paymentId = paymentId;
                return this;
            }

            public GetPaymentRegistryOptions build() {
                return new GetPaymentRegistryOptions(this.paymentId);
            }
        }
    }

    /**
     * Get Payment Registry. Метод для получения реестра платежей по интернет-эквайрингу
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     *
     * @param customerCode Уникальный код клиента
     * @param merchantId Идентификатор торговой точки в интернет-эквайринге
     * @param date Дата реестра
     * @param options optional query parameters; {@code null} means defaults
     */
    public List<AcquiringPaymentRegistryItemModel> getPaymentRegistry(String customerCode, String merchantId, String date, GetPaymentRegistryOptions options) {
        return transport.request("GET", "/acquiring/v1.0/registry")
                .query("customerCode", customerCode)
                .query("merchantId", merchantId)
                .query("date", date)
                .query("paymentId", options == null ? null : options.paymentId())
                .unwrap("Data", "Registry")
                .as(new TypeReference<List<AcquiringPaymentRegistryItemModel>>() {});
    }

    /**
     * Get Payment Registry. Метод для получения реестра платежей по интернет-эквайрингу
     *
     * <p>The customer code is taken from the client configuration ({@code TochkaClient.builder().customerCode(...)}).
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     *
     * @param merchantId Идентификатор торговой точки в интернет-эквайринге
     * @param date Дата реестра
     */
    public List<AcquiringPaymentRegistryItemModel> getPaymentRegistry(String merchantId, String date) {
        return transport.request("GET", "/acquiring/v1.0/registry")
                .query("customerCode", requireCustomerCode())
                .query("merchantId", merchantId)
                .query("date", date)
                .unwrap("Data", "Registry")
                .as(new TypeReference<List<AcquiringPaymentRegistryItemModel>>() {});
    }

    /**
     * Get Payment Registry. Метод для получения реестра платежей по интернет-эквайрингу
     *
     * <p>The customer code is taken from the client configuration ({@code TochkaClient.builder().customerCode(...)}).
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     *
     * @param merchantId Идентификатор торговой точки в интернет-эквайринге
     * @param date Дата реестра
     * @param options optional query parameters; {@code null} means defaults
     */
    public List<AcquiringPaymentRegistryItemModel> getPaymentRegistry(String merchantId, String date, GetPaymentRegistryOptions options) {
        return transport.request("GET", "/acquiring/v1.0/registry")
                .query("customerCode", requireCustomerCode())
                .query("merchantId", merchantId)
                .query("date", date)
                .query("paymentId", options == null ? null : options.paymentId())
                .unwrap("Data", "Registry")
                .as(new TypeReference<List<AcquiringPaymentRegistryItemModel>>() {});
    }

    /**
     * Get Retailers. Метод возвращает данные ваших торговых точек в интернет-эквайринге: их {@code
     * merchantId}, статус регистрации, доступные способы оплаты и комиссию. {@code merchantId} нужен
     * при создании платёжных ссылок, если торговых точек несколько. Зачем нужен {@code merchantId} — в
     * разделе «Платёжные ссылки (/docs/tochka-api/opisanie-metodov/platyozhnye-ssylki)».
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     *
     * @param customerCode Уникальный код клиента
     */
    public List<AcquiringRetailerModel> getRetailers(String customerCode) {
        return transport.request("GET", "/acquiring/v1.0/retailers")
                .query("customerCode", customerCode)
                .unwrap("Data", "Retailer")
                .as(new TypeReference<List<AcquiringRetailerModel>>() {});
    }

    /**
     * Get Retailers. Метод возвращает данные ваших торговых точек в интернет-эквайринге: их {@code
     * merchantId}, статус регистрации, доступные способы оплаты и комиссию. {@code merchantId} нужен
     * при создании платёжных ссылок, если торговых точек несколько. Зачем нужен {@code merchantId} — в
     * разделе «Платёжные ссылки (/docs/tochka-api/opisanie-metodov/platyozhnye-ssylki)».
     *
     * <p>The customer code is taken from the client configuration ({@code TochkaClient.builder().customerCode(...)}).
     *
     * <p>Required permissions: {@code ReadAcquiringData}.
     */
    public List<AcquiringRetailerModel> getRetailers() {
        return transport.request("GET", "/acquiring/v1.0/retailers")
                .query("customerCode", requireCustomerCode())
                .unwrap("Data", "Retailer")
                .as(new TypeReference<List<AcquiringRetailerModel>>() {});
    }

    /**
     * Refund Payment Operation. Метод для возврата платежей, созданных через платёжную ссылку Возврат
     * возможен только для платежа со статусом APPROVED
     *
     * <p>Required permissions: {@code MakeAcquiringOperation}.
     *
     * @param operationId Идентификатор платежа
     * @param amount Сумма платежа. Не больше суммы оплаты. Example: "1234.00"
     */
    public AcquiringPaymentOperationRefundModel refundPaymentOperation(String operationId, BigDecimal amount) {
        return transport.request("POST", "/acquiring/v1.0/payments/{operationId}/refund")
                .path("operationId", operationId)
                .body(Envelope.wrap(amount, "Data", "amount"))
                .unwrap("Data")
                .as(AcquiringPaymentOperationRefundModel.class);
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
