# Tochka API — Java-клиент

Клиент открытого API [Точка Банка](https://enter.tochka.com/doc/v2/redoc) для Java 17+.
Покрывает все 72 метода спецификации `v1.98.1-stable`: счета, балансы, выписки, платёжные
поручения, интернет-эквайринг, подписки, СБП и цифровой рубль, счета на оплату, закрывающие
документы, вебхуки и разрешения OAuth 2.0.

Из зависимостей — только Jackson. HTTP-слой построен на `java.net.http.HttpClient` из JDK.

```java
TochkaClient client = TochkaClient.builder()
        .jwt(System.getenv("TOCHKA_TOKEN"))
        .customerCode("300123123")
        .build();

for (AccountModel account : client.accounts().getAccountsList()) {
    System.out.println(account.accountId() + " — " + account.accountType());
}
```

## Что внутри

| Возможность | Где |
| --- | --- |
| Все методы API, разбитые по сервисам | `client.accounts()`, `client.sbpQrCodes()`, … |
| Модели запросов и ответов (268 штук) | `com.tochka.api.model` |
| Авторизация по JWT и по OAuth 2.0 с автообновлением токена | `com.tochka.api.auth` |
| Проверка подписи вебхуков ключом банка и типизированные события | `com.tochka.api.webhook` |
| Сертификаты Минцифры для TLS — подключены по умолчанию | `com.tochka.api.tls.RussianTrustedCa` |
| Повторы, логирование, песочница | `com.tochka.api.http` |

## Установка

Maven:

```xml
<dependency>
  <groupId>com.tochka</groupId>
  <artifactId>tochka-api</artifactId>
  <version>1.98.1-SNAPSHOT</version>
</dependency>
```

Gradle:

```kotlin
implementation("com.tochka:tochka-api:1.98.1-SNAPSHOT")
```

Требуется Java 17 и Jackson 2.17+ (подтягивается транзитивно).

## Сертификаты Минцифры

`enter.tochka.com` работает на сертификатах Национального удостоверяющего центра Минцифры. JVM
использует собственное хранилище доверенных сертификатов и не читает системное, поэтому у
большинства клиентов API соединение обрывается на `PKIX path building failed` ещё до отправки
запроса.

**Настраивать ничего не нужно:** корневой и выпускающий сертификаты (RSA-варианты — единственные,
которые JVM понимает без ГОСТ-провайдера) лежат в ресурсах библиотеки и подключаются
автоматически — и в `TochkaClient`, и в `OAuth2Client`, и в `WebhookVerifier`. Доверие
**добавляется** к стандартному `cacerts`, публичные центры продолжают работать.

Тот же контекст доступен отдельно, если нужен в своём HTTP-клиенте:

```java
SSLContext ssl = RussianTrustedCa.sslContext();
```

Если сертификаты уже добавлены в `cacerts` или во внешний truststore
(`-Djavax.net.ssl.trustStore=...`), встроенное доверие можно отключить:

```java
TochkaClient client = TochkaClient.builder().jwt(token).trustRussianCa(false).build();
```

Проверить окружение можно тестовым доменом банка: `https://tls-test.tochka.com/api` отвечает
`{"status":"ok"}`.

## Песочница

```java
TochkaClient sandbox = TochkaClient.sandbox();
List<CustomerModel> customers = sandbox.customers().getCustomersList();
```

Песочница повторяет структуру боевых методов, но отвечает фиксированными тестовыми данными:
`customerCode = 1234567ab`, `accountId = 12345810901234567890/044525104`,
`merchantId = 200000000001097`. Оплатить сформированную там платёжную ссылку нельзя.

## Авторизация

### JWT-ключ

Ключ создаётся в интернет-банке: «Сервисы» → «Интеграции и API» → «Создать JWT-ключ».
Вместе с ключом сохраните `client_id` — он нужен для вебхуков.

```java
TochkaClient client = TochkaClient.builder().jwt(token).build();
```

### OAuth 2.0

Нужен, когда вы обращаетесь к API от имени других компаний. Полный путь проходится один раз,
дальше клиент сам обновляет токены.

```java
OAuth2Client oauth = OAuth2Client.builder()
        .clientId("test_app")
        .clientSecret(secret)
        .build();

// 1. токен для работы с разрешениями
TokenResponse appToken = oauth.clientCredentials(Scopes.ALL);

// 2. список разрешений, который подтвердит клиент
TochkaClient consentClient = TochkaClient.builder()
        .authorization(Authorization.jwt(appToken.accessToken()))
        .build();
List<ConsentResponseModel> consents = consentClient.consents().createNewConsent(
        new ConsentCreateRequest(
                ConsentCreateRequestModel.builder()
                        .permissions(List.of(ExternalConsentTypeEnum.READ_BALANCES,
                                             ExternalConsentTypeEnum.READ_STATEMENTS))
                        .build(),
                null));

// 3. отправляем клиента подтверждать разрешения
URI approvalUrl = oauth.authorizationUrl(
        consents.get(0).consentId(), "https://my-app.example/callback", Scopes.ALL, "state-123");

// 4. меняем пришедший на redirect_uri код на пару токенов
TokenResponse tokens = oauth.exchangeCode(code, "https://my-app.example/callback", Scopes.ALL);

// 5. клиент, который сам обновляет access_token по refresh_token
OAuth2Authorization authorization = OAuth2Authorization.builder()
        .client(oauth)
        .token(tokens)
        .onTokenRefreshed(refreshed -> store.save(refreshed.refreshToken()))
        .build();

TochkaClient client = TochkaClient.builder().authorization(authorization).build();
```

`access_token` живёт 24 часа, `refresh_token` — 30 дней, и при каждом обновлении приходит новый.
Сохраняйте его в `onTokenRefreshed`, иначе после перезапуска придётся заново проходить
подтверждение разрешений.

## Типовые сценарии

### Баланс и выписка

```java
String accountId = client.accounts().getAccountsList().get(0).accountId();

List<BalanceModel> balances = client.balances().getBalanceInfo(accountId);

InitStatementModel init = client.statements().initStatement(StatementInitReqModel.builder()
        .accountId(accountId)
        .startDateTime(LocalDate.now().minusDays(7))
        .endDateTime(LocalDate.now())
        .build());

// выписка формируется асинхронно — забираем, когда статус станет Ready
List<StatementModel> statement = client.statements().getStatement(accountId, init.statementId());
```

### Платёжная ссылка

```java
AcquiringCreatePaymentOperationResponseModel link = client.acquiring().createPaymentOperation(
        AcquiringCreatePaymentOperationRequestModel.builder()
                .customerCode("300123123")
                .amount(new BigDecimal("1500.00"))
                .purpose("Оплата заказа № 42")
                .paymentMode(List.of(AcquiringPaymentMode.CARD, AcquiringPaymentMode.SBP))
                .paymentLinkId("order-42")
                .redirectUrl("https://shop.example/success")
                .build());

System.out.println(link.paymentLink());
```

### QR-код СБП

Суммы в методах QR-кодов указываются **в копейках** — в отличие от платёжных ссылок и возвратов
СБП, где сумма в рублях.

```java
RegisteredQrCode qr = client.sbpQrCodes().registerQrCode(merchantId, accountId,
        RegisterQRCode.builder()
                .amount(150_00L)                      // 150 рублей
                .paymentPurpose("Оплата заказа № 42")
                .qrcType(QrTypeEnum.V02)              // 02 — динамический
                .imageParams(QRCodeRequestParams.builder().width(300).height(300).build())
                .build());

System.out.println(qr.payload());   // ссылка для оплаты
System.out.println(qr.image());     // изображение QR-кода
```

### Платёж на подпись

API не отправляет деньги сам: он создаёт платёжку, а подписывает её сотрудник в интернет-банке.

```java
PaymentForSignResponseModel payment = client.payments().createPaymentForSign(
        PaymentForSignRequestModel.builder()
                .accountCode("40802810000000000002")
                .bankCode("044525104")
                .counterpartyAccountNumber("40702810000000000001")
                .counterpartyBankBic("044525104")
                .counterpartyINN("0000000000")
                .counterpartyName("ООО «Контрагент»")
                .paymentAmount(new BigDecimal("700.33"))
                .paymentDate(LocalDate.now())
                .paymentNumber(9195)
                .paymentPurpose("Оплата по счёту № 42. Без НДС")
                .supplierBillId(null)
                .build());

System.out.println(payment.redirectURL());   // страница подписания
```

### Документы в PDF

```java
BinaryContent pdf = client.invoices().getInvoice(customerCode, documentId);
pdf.writeTo(Path.of("invoice.pdf"));
```

### Пагинация

Для списков операций и подписок есть вариант `...Page`, который отдаёт `Links` и `Meta`:

```java
Page<AcquiringGetPaymentOperationListItemModel> page = client.acquiring()
        .getPaymentOperationListPage(AcquiringApi.GetPaymentOperationListOptions.builder()
                .fromDate("2025-01-01")
                .perPage(100)
                .build());

System.out.println(page.totalPages() + " страниц, есть ещё: " + page.hasNext());
```

## Вебхуки

В теле вебхука приходит не JSON, а строка JWT, подписанная алгоритмом RS256. Обрабатывать данные
можно только после проверки подписи публичным ключом банка.

```java
WebhookVerifier verifier = WebhookVerifier.usingTochkaPublicKey();

// в контроллере: тело запроса — строка целиком, Content-Type: text/plain
WebhookEvent event = verifier.verify(requestBody);

if (event instanceof IncomingPaymentEvent e) {
    log.info("приход {} по платежу {}", e.amount(), e.paymentId());
} else if (event instanceof AcquiringInternetPaymentEvent e) {
    if (e.isAuthorized()) {
        client.acquiring().capturePayment(e.operationId());   // двухэтапная оплата
    } else {
        orders.markPaid(e.paymentLinkId());
    }
} else if (event instanceof IncomingSbpPaymentEvent e) {
    log.info("оплата по QR {} на {}", e.qrcId(), e.amount());
}
```

`WebhookEvent` — запечатанный (sealed) интерфейс, так что на Java 21+ то же самое пишется
через `switch` с сопоставлением по образцу и проверкой полноты ветвей.

Ключ берётся с `https://enter.tochka.com/doc/openapi/static/keys/public`, кешируется на 6 часов
и перечитывается автоматически, когда подпись перестаёт сходиться, — так интеграция переживёт
смену ключа банком. Если приложение не должно ходить в сеть в рантайме, задайте ключ явно:

```java
WebhookVerifier verifier = WebhookVerifier.builder()
        .publicKeyPem(Files.readString(Path.of("tochka_webhook_key.json")))   // JWK или PEM
        .build();
```

Тогда за сменой ключа придётся следить самостоятельно: подписи просто перестанут сходиться.

Отвечайте банку кодом 200: на любой другой ответ вебхук повторяется 30 раз с интервалом
10 секунд.

Подписка на события настраивается только через API, в интернет-банке этого раздела нет:

```java
client.webhooks().createWebhook(clientId, Webhook.builder()
        .url("https://my-app.example/tochka/webhook")
        .webhooksList(List.of(WebhookTypeEnum.INCOMING_PAYMENT,
                              WebhookTypeEnum.ACQUIRING_INTERNET_PAYMENT))
        .build());
```

На один `client_id` приходится один вебхук с одним URL — чтобы разводить события по разным
адресам, выпустите несколько JWT-ключей.

## Ошибки

Все ответы с кодом ошибки превращаются в исключения с разобранным телом:

```java
try {
    client.balances().getBalanceInfo(accountId);
} catch (TochkaForbiddenException e) {
    // не хватает разрешения или указан чужой customerCode
    log.warn("{} — {}", e.firstErrorCode().orElse(""), e.getMessage());
} catch (TochkaApiException e) {
    log.error("HTTP {} (id {})", e.statusCode(), e.errorId().orElse("—"));
} catch (TochkaTransportException e) {
    // сеть или TLS: запрос не дошёл до банка
}
```

| Исключение | Когда |
| --- | --- |
| `TochkaBadRequestException` | 400, 422 — запрос не прошёл валидацию |
| `TochkaUnauthorizedException` | 401 — токен истёк или отозван |
| `TochkaForbiddenException` | 403 — нет разрешения, неверный `customerCode` |
| `TochkaNotFoundException` | 404 — объект не найден или неверный адрес метода |
| `TochkaDependencyException` | 424 — смежный сервис банка не ответил |
| `TochkaServerException` | 5xx |
| `TochkaTransportException` | сеть, таймаут, TLS |

## Повторы и логирование

```java
TochkaClient client = TochkaClient.builder()
        .jwt(token)
        .retryPolicy(RetryPolicy.builder()
                .maxAttempts(4)
                .initialBackoff(Duration.ofMillis(300))
                .build())
        .logLevel(LogLevel.BASIC)
        .logger(message -> log.debug(message))
        .build();
```

По умолчанию повторяются только идемпотентные методы (`GET`, `PUT`, `DELETE`) на кодах
429/500/502/503/504 и сетевых сбоях: повтор `POST` мог бы создать второй платёж, если первый
запрос дошёл до банка, а потерялся только ответ. Код 429 повторяется при любом методе, а
`Retry-After` учитывается. Разрешить повтор `POST` можно явно — `retryNonIdempotent(true)`.

`LogLevel.BODY` пишет тела запросов и ответов, заголовок `Authorization` маскируется всегда.

## Метод, которого ещё нет в библиотеке

```java
JsonNode raw = client.transport()
        .request("GET", "/open-banking/v1.0/accounts/{accountId}/balances")
        .path("accountId", accountId)
        .unwrap("Data", "Balance")
        .as(JsonNode.class);
```

## Что стоит знать про API

- `customerCode` — код компании, нужен большинству методов. Получить: `client.customers()
  .getCustomersList()`, брать объект с `customerType = Business`. Заданный через
  `.customerCode(...)` код подставляется автоматически в методы, где он обязателен.
- `accountId` — номер счёта и БИК через слеш (`40817810802000000008/044525104`). Слеш в адресе
  запроса не экранируется — так его ждёт банк.
- Суммы: в QR-кодах СБП — в копейках, в платёжных ссылках и возвратах СБП — в рублях. В моделях
  копейки это `Long`, рубли — `BigDecimal`.
- Выписка формируется асинхронно: `initStatement` только ставит её в очередь.
- Неизвестные поля в ответах игнорируются, а неизвестные значения перечислений разбираются в
  `null` — банк добавляет и то и другое без смены версии API.

## Регенерация из спецификации

Модели и классы сервисов генерируются из `swagger.json`; рукописное ядро (`http`, `auth`,
`webhook`, `tls`) генератор не трогает. Сгенерированный код коммитится в репозиторий — сборка
любого коммита воспроизводима, а изменения API видны в ревью.

Следить за обновлениями вручную не нужно: раз в неделю CI скачивает свежую спецификацию и, если
она изменилась, открывает PR с перегенерированным кодом и поднятой версией. Тот же процесс
руками:

```bash
# enter.tochka.com отдаёт только цепочку Минцифры — её нет в системных хранилищах
curl -fsSL --cacert src/main/resources/com/tochka/api/tls/russian_trusted_root_ca.pem \
  -o swagger.json https://enter.tochka.com/doc/openapi/swagger.json
python3 codegen/generate.py
./mvnw verify
```

Правки в `src/main/java/com/tochka/api/{model,api}` не сохраняются: эти пакеты пересоздаются
целиком, менять нужно `codegen/generate.py`. CI проверяет это отдельным шагом.

## Сборка и тесты

```bash
./mvnw verify   # 42 теста + jar, sources-jar и javadoc-jar
./mvnw test -Dtest=WebhookVerifierTest
```

Нужен только JDK 17+ — Maven приезжает через wrapper.

Тесты не ходят в сеть: HTTP-обмен проверяется на локальном `HttpServer`, а подпись вебхуков — на
сгенерированной паре RSA-ключей и на зафиксированной паре «ключ банка + пример вебхука из
документации» (`src/test/resources`).

## Лицензия

MIT.
