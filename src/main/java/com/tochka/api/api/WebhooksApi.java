package com.tochka.api.api;

import com.tochka.api.http.Envelope;
import com.tochka.api.http.Transport;
import com.tochka.api.model.Webhook;
import com.tochka.api.model.WebhookEditRequest;
import com.tochka.api.model.WebhookTypeEnum;

/**
 * Подписка на события по счетам и платежам.
 *
 * <p>Экземпляр доступен через {@link com.tochka.api.TochkaClient}.
 */
public final class WebhooksApi {

    private final Transport transport;

    public WebhooksApi(Transport transport) {
        this.transport = transport;
    }

    /**
     * Create Webhook. Метод подключает вебхук — уведомление, которое банк присылает на ваш URL, когда
     * происходит событие: поступил или ушёл платёж, прошла оплата по QR-коду. Вы указываете адрес и
     * список событий, на которые хотите подписаться. Какие бывают события и как подписаться — в
     * разделе «Вебхуки (/docs/tochka-api/opisanie-metodov/vebhuki)».
     *
     * @param clientId Уникальный идентификатор приложения
     * @param request тело запроса
     */
    public Webhook createWebhook(String clientId, Webhook request) {
        return transport.request("PUT", "/webhook/v1.0/{client_id}")
                .path("client_id", clientId)
                .body(request)
                .unwrap("Data")
                .as(Webhook.class);
    }

    /**
     * Delete Webhook. Метод отключает вебхук. После удаления банк перестаёт присылать уведомления на
     * указанный в нём адрес. Подробнее о работе с вебхуками — в разделе «Вебхуки
     * (/docs/tochka-api/opisanie-metodov/vebhuki)».
     *
     * @param clientId Уникальный идентификатор приложения
     */
    public Boolean deleteWebhook(String clientId) {
        return transport.request("DELETE", "/webhook/v1.0/{client_id}")
                .path("client_id", clientId)
                .unwrap("Data", "result")
                .as(Boolean.class);
    }

    /**
     * Edit Webhook. Метод меняет настройки уже подключённого вебхука — адрес, на который приходят
     * уведомления, и список событий. Подробнее о работе с вебхуками — в разделе «Вебхуки
     * (/docs/tochka-api/opisanie-metodov/vebhuki)».
     *
     * @param clientId Уникальный идентификатор приложения
     * @param request тело запроса
     */
    public Webhook editWebhook(String clientId, WebhookEditRequest request) {
        return transport.request("POST", "/webhook/v1.0/{client_id}")
                .path("client_id", clientId)
                .body(request)
                .unwrap("Data")
                .as(Webhook.class);
    }

    /**
     * Get Webhooks. Метод возвращает список вебхуков, подключённых к вашему приложению, и события, на
     * которые они подписаны. Помогает проверить, какие уведомления настроены. Подробнее о работе с
     * вебхуками — в разделе «Вебхуки (/docs/tochka-api/opisanie-metodov/vebhuki)».
     *
     * @param clientId Уникальный идентификатор приложения
     */
    public Webhook getWebhooks(String clientId) {
        return transport.request("GET", "/webhook/v1.0/{client_id}")
                .path("client_id", clientId)
                .unwrap("Data")
                .as(Webhook.class);
    }

    /**
     * Send Webhook. Метод отправляет тестовое уведомление на ваш URL, чтобы проверить, что сервер
     * принимает вебхуки и правильно на них отвечает. Удобно использовать при настройке интеграции.
     * Подробнее о работе с вебхуками — в разделе «Вебхуки
     * (/docs/tochka-api/opisanie-metodov/vebhuki)».
     *
     * @param clientId Уникальный идентификатор приложения
     * @param webhookType Тип вебхука
     */
    public Boolean sendWebhook(String clientId, WebhookTypeEnum webhookType) {
        return transport.request("POST", "/webhook/v1.0/{client_id}/test_send")
                .path("client_id", clientId)
                .body(Envelope.wrap(webhookType, "webhookType"))
                .unwrap("Data", "result")
                .as(Boolean.class);
    }

}
