package com.tochka.api.api;

import com.tochka.api.http.BinaryContent;
import com.tochka.api.http.Envelope;
import com.tochka.api.http.Transport;
import com.tochka.api.model.InvoiceCreateRequestModel;
import com.tochka.api.model.InvoicePaymentStatusEnum;

/**
 * Счета на оплату для юрлиц и ИП.
 *
 * <p>Экземпляр доступен через {@link com.tochka.api.TochkaClient}.
 */
public final class InvoicesApi {

    private final Transport transport;

    public InvoicesApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Create Invoice. Метод создаёт счёт на оплату — документ, который продавец выставляет покупателю.
     * В нём указываются товары или услуги, сумма и реквизиты покупателя. В ответ приходит {@code
     * documentId}, по которому со счётом можно работать дальше. Что передать в счёте и как отследить
     * оплату — в разделе «Счёт на оплату
     * (/docs/tochka-api/opisanie-metodov/vystavlenie-schetov-i-sozdanie-zakryvayushih-dokumentov/schet-na-oplatu)».
     *
     * <p>Требуемые разрешения: {@code ManageInvoiceData}.
     *
     * @param request тело запроса
     */
    public String createInvoice(InvoiceCreateRequestModel request) {
        return transport.request("POST", "/invoice/v1.0/bills")
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data", "documentId")
                .as(String.class);
    }

    /**
     * Delete Invoice. Метод удаляет счёт на оплату по его documentId. Пригодится, если в счёте ошибка:
     * отредактировать счёт нельзя, поэтому его удаляют и создают заново. Подробнее о счетах на оплату
     * — в разделе «Счёт на оплату
     * (/docs/tochka-api/opisanie-metodov/vystavlenie-schetov-i-sozdanie-zakryvayushih-dokumentov/schet-na-oplatu)».
     *
     * <p>Требуемые разрешения: {@code ManageInvoiceData}.
     *
     * @param customerCode Уникальный код клиента
     * @param documentId Уникальный идентификатор документа
     */
    public Boolean deleteInvoice(String customerCode, String documentId) {
        return transport.request("DELETE", "/invoice/v1.0/bills/{customerCode}/{documentId}")
                .path("customerCode", customerCode)
                .path("documentId", documentId)
                .unwrap("Data", "result")
                .as(Boolean.class);
    }

    /**
     * Get Invoice. Метод возвращает выставленный счёт в формате PDF — готовый файл, который можно
     * сохранить или отправить покупателю. Подробнее о счетах на оплату — в разделе «Счёт на оплату
     * (/docs/tochka-api/opisanie-metodov/vystavlenie-schetov-i-sozdanie-zakryvayushih-dokumentov/schet-na-oplatu)».
     *
     * <p>Требуемые разрешения: {@code ManageInvoiceData}.
     *
     * @param customerCode Уникальный код клиента
     * @param documentId Уникальный идентификатор документа
     */
    public BinaryContent getInvoice(String customerCode, String documentId) {
        return transport.request("GET", "/invoice/v1.0/bills/{customerCode}/{documentId}/file")
                .path("customerCode", customerCode)
                .path("documentId", documentId)
                .asBinary();
    }

    /**
     * Get Invoice Payment Status. Метод показывает, оплачен ли счёт: ожидает оплаты, оплачен или истёк
     * срок. Статус меняется автоматически, когда мы сопоставляем входящий платёж со счётом. Как
     * отслеживается оплата счёта — в разделе «Счёт на оплату
     * (/docs/tochka-api/opisanie-metodov/vystavlenie-schetov-i-sozdanie-zakryvayushih-dokumentov/schet-na-oplatu)».
     *
     * <p>Требуемые разрешения: {@code ManageInvoiceData}.
     *
     * @param customerCode Уникальный код клиента
     * @param documentId Уникальный идентификатор документа
     */
    public InvoicePaymentStatusEnum getInvoicePaymentStatus(String customerCode, String documentId) {
        return transport.request("GET", "/invoice/v1.0/bills/{customerCode}/{documentId}/payment-status")
                .path("customerCode", customerCode)
                .path("documentId", documentId)
                .unwrap("Data", "paymentStatus")
                .as(InvoicePaymentStatusEnum.class);
    }

    /**
     * Send Invoice To Email. Метод отправляет счёт на оплату на электронную почту — например,
     * покупателю. Подробнее о счетах на оплату — в разделе «Счёт на оплату
     * (/docs/tochka-api/opisanie-metodov/vystavlenie-schetov-i-sozdanie-zakryvayushih-dokumentov/schet-na-oplatu)».
     *
     * <p>Требуемые разрешения: {@code ManageInvoiceData}.
     *
     * @param customerCode Уникальный код клиента
     * @param documentId Уникальный идентификатор документа
     * @param email Электронная почта, на которую нужно отправить
     */
    public Boolean sendInvoiceToEmail(String customerCode, String documentId, String email) {
        return transport.request("POST", "/invoice/v1.0/bills/{customerCode}/{documentId}/email")
                .path("customerCode", customerCode)
                .path("documentId", documentId)
                .body(Envelope.wrap(email, "Data", "email"))
                .unwrap("Data", "result")
                .as(Boolean.class);
    }

}
