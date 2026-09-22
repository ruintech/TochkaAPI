package com.tochka.api.api;

import com.tochka.api.http.BinaryContent;
import com.tochka.api.http.Envelope;
import com.tochka.api.http.Transport;
import com.tochka.api.model.ClosingDocumentCreateRequestModel;

/**
 * Closing documents: acts, packing lists, invoices and UPD.
 *
 * <p>An instance is available from {@link com.tochka.api.TochkaClient}.
 */
public final class ClosingDocumentsApi {

    private final Transport transport;

    public ClosingDocumentsApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Create Closing Document. Метод создаёт закрывающий документ — акт, счёт-фактуру, накладную
     * ТОРГ-12 или УПД. Он подтверждает, что товар передан, а услуга оказана. Вид документа
     * определяется тем, какой объект вы передадите в {@code Content}. Какие бывают документы и что
     * передать — в разделе «Закрывающие документы
     * (/docs/tochka-api/opisanie-metodov/vystavlenie-schetov-i-sozdanie-zakryvayushih-dokumentov/zakryvayushie-dokumenty)».
     *
     * <p>Required permissions: {@code ManageInvoiceData}.
     *
     * @param request request body
     */
    public String createClosingDocument(ClosingDocumentCreateRequestModel request) {
        return transport.request("POST", "/invoice/v1.0/closing-documents")
                .body(Envelope.wrap(request, "Data"))
                .unwrap("Data", "documentId")
                .as(String.class);
    }

    /**
     * Delete Closing Documents. Метод удаляет закрывающий документ по его {@code documentId}.
     * Пригодится, если в документе ошибка: отредактировать его нельзя, поэтому документ удаляют и
     * создают заново. Подробнее о закрывающих документах — в разделе «Закрывающие документы
     * (/docs/tochka-api/opisanie-metodov/vystavlenie-schetov-i-sozdanie-zakryvayushih-dokumentov/zakryvayushie-dokumenty)».
     *
     * <p>Required permissions: {@code ManageInvoiceData}.
     *
     * @param customerCode Уникальный код клиента
     * @param documentId Уникальный идентификатор документа
     */
    public Boolean deleteClosingDocuments(String customerCode, String documentId) {
        return transport.request("DELETE", "/invoice/v1.0/closing-documents/{customerCode}/{documentId}")
                .path("customerCode", customerCode)
                .path("documentId", documentId)
                .unwrap("Data", "result")
                .as(Boolean.class);
    }

    /**
     * Get Closing Document. Метод возвращает закрывающий документ в формате PDF — готовый файл для
     * сохранения или отправки. Подробнее о закрывающих документах — в разделе «Закрывающие документы
     * (/docs/tochka-api/opisanie-metodov/vystavlenie-schetov-i-sozdanie-zakryvayushih-dokumentov/zakryvayushie-dokumenty)».
     *
     * <p>Required permissions: {@code ManageInvoiceData}.
     *
     * @param customerCode Уникальный код клиента
     * @param documentId Уникальный идентификатор документа
     */
    public BinaryContent getClosingDocument(String customerCode, String documentId) {
        return transport.request("GET", "/invoice/v1.0/closing-documents/{customerCode}/{documentId}/file")
                .path("customerCode", customerCode)
                .path("documentId", documentId)
                .asBinary();
    }

    /**
     * Send Closing Documents To Email. Метод отправляет закрывающий документ на электронную почту —
     * например, контрагента. Подробнее о закрывающих документах — в разделе «Закрывающие документы
     * (/docs/tochka-api/opisanie-metodov/vystavlenie-schetov-i-sozdanie-zakryvayushih-dokumentov/zakryvayushie-dokumenty)».
     *
     * <p>Required permissions: {@code ManageInvoiceData}.
     *
     * @param customerCode Уникальный код клиента
     * @param documentId Уникальный идентификатор документа
     * @param email Электронная почта, на которую нужно отправить
     */
    public Boolean sendClosingDocumentsToEmail(String customerCode, String documentId, String email) {
        return transport.request("POST", "/invoice/v1.0/closing-documents/{customerCode}/{documentId}/email")
                .path("customerCode", customerCode)
                .path("documentId", documentId)
                .body(Envelope.wrap(email, "Data", "email"))
                .unwrap("Data", "result")
                .as(Boolean.class);
    }

}
