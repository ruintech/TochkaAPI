/**
 * Java-клиент открытого API Точка Банка.
 *
 * <p>Точка входа — {@link com.tochka.api.TochkaClient}: он собирает HTTP-клиент, авторизацию и
 * даёт доступ к сервисам по разделам API ({@code accounts()}, {@code balances()},
 * {@code acquiring()}, {@code sbpQrCodes()} и другим).
 *
 * <p>Остальные пакеты:
 * <ul>
 *   <li>{@code com.tochka.api.api} — методы API, сгенерированные из спецификации;</li>
 *   <li>{@code com.tochka.api.model} — модели запросов и ответов;</li>
 *   <li>{@code com.tochka.api.auth} — авторизация по JWT и по OAuth 2.0;</li>
 *   <li>{@code com.tochka.api.webhook} — проверка подписи вебхуков и типизированные события;</li>
 *   <li>{@code com.tochka.api.tls} — сертификаты Минцифры для TLS;</li>
 *   <li>{@code com.tochka.api.http} — транспорт: повторы, логирование, конверты, файлы;</li>
 *   <li>{@code com.tochka.api.exception} — исключения клиента.</li>
 * </ul>
 */
package com.tochka.api;
