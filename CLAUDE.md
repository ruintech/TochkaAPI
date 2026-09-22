# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

Java-клиент открытого API Точка Банка. Язык кода, комментариев и документации — русский.

## Команды

`mvn` нет в PATH — в репозитории есть Maven Wrapper, им и пользуйтесь (та же версия, что в CI):

```bash
./mvnw verify                                       # тесты + jar + sources + javadoc
./mvnw test -Dtest=WebhookVerifierTest              # один класс
./mvnw test -Dtest='TransportTest#retriesIdempotentRequestAfterServerError'   # один тест
./mvnw -o verify                                    # офлайн, когда зависимости уже скачаны

python3 codegen/generate.py                         # перегенерация моделей и сервисов
```

Javadoc собирается с `failOnWarnings` и `doclint=all,-missing`, поэтому сломанная документация
валит `verify` — отдельная проверка не нужна.

Тесты не ходят в сеть и должны оставаться такими: HTTP проверяется на локальном
`com.sun.net.httpserver.HttpServer`, подписи вебхуков — на сгенерированной паре RSA-ключей и на
зафиксированных фикстурах в `src/test/resources`.

## Обновление API

Спецификация лежит в `swagger.json` (сейчас `v1.98.1-stable`).

Обычно обновлять её вручную не нужно: workflow `.github/workflows/update-spec.yml` раз в неделю
скачивает спеку, перегенерирует код, поднимает версию и открывает PR. Вручную то же самое:

```bash
# enter.tochka.com отдаёт только цепочку Минцифры, в системных хранилищах её нет
curl -fsSL --cacert src/main/resources/com/tochka/api/tls/russian_trusted_root_ca.pem \
  -o swagger.json https://enter.tochka.com/doc/openapi/swagger.json
python3 codegen/generate.py     # печатает версию спеки и количество файлов
./mvnw verify
```

Генератор **полностью стирает и пересоздаёт** `src/main/java/com/tochka/api/model` и
`.../api`. Всё остальное — рукописное ядро, его он не трогает. Никогда не правьте файлы в этих
двух пакетах: изменения нужно вносить в `codegen/generate.py`.

Что проверить после регенерации:

- **Генератор упал с `SystemExit`** — в спеке появился новый тег (добавьте его в `TAG_TO_API`)
  или два разных имени схемы дали одинаковый Java-класс (добавьте запись в
  `SCHEMA_NAME_OVERRIDES`). Оба случая намеренно останавливают генерацию, а не молча портят код.
- **`git diff --stat` по `api/`** — новые методы видно по изменению сигнатур; если метод исчез,
  это ломающее изменение API, а не баг генератора.
- **Версия в `pom.xml`** повторяет версию спеки: `v1.98.1-stable` → `1.98.1-SNAPSHOT`. Ставится
  через `./mvnw versions:set -DnewVersion=... -DgenerateBackupPoms=false`.
- **`JsonNode` в сигнатурах моделей** — признак конструкции, которую генератор не умеет. В
  текущей спеке нет ни одного `allOf`/`oneOf`/`anyOf`, и `java_type()` на них не рассчитан: такая
  схема молча превратится в `JsonNode` или `Map<String, Object>`. Если появились — учить
  генератор, а не править результат.
- **Примеры в README** написаны под реальные сигнатуры; при изменении моделей их стоит
  прогнать через компилятор (собрать временный файл с вызовами и скомпилировать по `target/classes`).

## Архитектура

### Граница «рукописное / генерируемое»

```
com/tochka/api/
├── TochkaClient.java     рукописный фасад: 17 сервисов + сборка HttpClient
├── api/      (17)        ГЕНЕРИРУЕТСЯ — по одному классу на тег OpenAPI
├── model/   (268)        ГЕНЕРИРУЕТСЯ — record + Builder на каждую схему
├── http/                 рукописное ядро: Transport, ApiRequest, Envelope, RetryPolicy, Page, Json
├── auth/                 JWT и OAuth 2.0 (client_credentials / authorization_code / refresh)
├── webhook/              проверка RS256-подписи + sealed-иерархия событий
├── tls/                  сертификаты Минцифры (PEM лежат в src/main/resources)
└── exception/            иерархия исключений по HTTP-статусам
```

### Конверты разворачиваются на границе генератора

Все ответы API приходят как `{Data, Links, Meta}`, тела запросов вложены в `Data`, иногда глубже.
Генератор разворачивает эти обёртки, поэтому сервисные методы принимают и возвращают
содержательные модели:

```java
// ответ: unwrap("Data", "AccountList") -> List<AccountModel>
// запрос: Envelope.wrap(statement, "Data", "Statement")
public InitStatementModel initStatement(StatementInitReqModel statement)
```

Правило разворачивания одно и то же для запроса и ответа: спускаться внутрь, **пока объект имеет
ровно одно свойство и оно перечислено в `required`**. Условие `required` принципиально — иначе
`Transport.navigate()` упадёт в рантайме на отсутствующем поле. Схемы вроде `ConsentCreateRequest`
(два свойства: `Data` + необязательный `Risks`) намеренно не разворачиваются.

### Что ещё делает генератор

- **Перегрузки без `customerCode`**: если метод требует query-параметр `customerCode`, рядом
  генерируется вариант без него, берущий код из `TochkaClient.builder().customerCode(...)` через
  `requireCustomerCode()`. Аналогично для необязательного заголовка `customer-code`.
- **`...Options`**: необязательные query-параметры выносятся в record-строитель внутри класса
  сервиса (таких методов 6).
- **`...Page`**: если конверт ответа использует `PaginatedLinkModel`, добавляется метод,
  возвращающий `Page<T>` с `Links`/`Meta` (сейчас — операции эквайринга и подписки).
- **Типы**: `number` → `BigDecimal` (деньги), `integer` → `Long`, если в имени есть `amount`
  (копейки), иначе `Integer`; `format: date` → `LocalDate`, `date-time` → `OffsetDateTime`.
- **Перечисления**: константы транслитерируются из значения (`"Мемориальный ордер"` →
  `MEMORIALNYY_ORDER`, `"001"` → `V001`). Неизвестное значение разбирается в `null`, а не в
  исключение — банк добавляет значения без смены версии; строгий разбор есть в `parse()`.
- **Параметры пути** упорядочены по позиции в шаблоне пути, а не по порядку в спеке.

### Инварианты транспорта

- `accountId` — это «номер счёта/БИК», и слеш в пути **не экранируется**:
  `Transport.encodePathSegment()` намеренно пропускает `/`, `,` и `:`. Банк ждёт путь в таком виде.
- Повторы по умолчанию только для идемпотентных методов. `POST` не повторяется: первый запрос мог
  дойти до банка и создать платёж, а потеряться мог только ответ. Исключение — 429.
  Включается явно через `retryNonIdempotent(true)`.
- Ответы с кодом ошибки разбираются в типизированные исключения по статусу; тело (`code`, `id`,
  `Errors`) сохраняется в `TochkaApiException`.
- `Json.mapper()` настроен на игнорирование неизвестных полей, `NON_NULL` при сериализации и
  терпимый разбор `date-time` (часть полей API отдаёт чистой датой).

### TLS и вебхуки

`enter.tochka.com` работает на сертификатах Минцифры, которых нет в `cacerts`. Библиотека
подключает их **по умолчанию** — в `TochkaClient`, `OAuth2Client` и `WebhookVerifier`; контекст
кешируется в `RussianTrustedCa.sslContext()`. Отключение — `trustRussianCa(false)`. Если убрать
этот дефолт, перестанет работать и загрузка ключа вебхуков: он лежит на том же домене.

Публичный ключ вебхуков — `https://enter.tochka.com/doc/openapi/static/keys/public` (JWK,
RSA 3072). Скачивается лениво, кешируется на 6 часов и перечитывается, если подпись перестала
сходиться. Явно заданный ключ в сеть не ходит вообще.

Тест `WebhookVerifierTest#verifiesRealWebhookWithKeyPublishedByTheBank` проверяет подпись
реального примера вебхука из документации зафиксированным ключом банка
(`src/test/resources`). Если банк сменит ключ, этот тест упадёт — тогда нужно обновить обе
фикстуры, а не ослаблять проверку.

## CI

`.github/workflows/`:

| Файл | Когда | Что делает |
| --- | --- | --- |
| `ci.yml` | push в main, PR | `./mvnw verify` на JDK 17 и 21 + проверка, что генерация соответствует спеке |
| `update-spec.yml` | по понедельникам, вручную | скачивает спеку, регенерирует, поднимает версию, открывает PR |
| `release.yml` | тег `v*.*.*`, вручную | `./mvnw deploy` во внутренний Nexus |

Сгенерированный код **коммитится**, а не собирается на лету: иначе один и тот же коммит собирался
бы по-разному в разные дни, а изменение API проезжало бы в артефакт без ревью. Шаг `codegen`
в `ci.yml` запускает генератор и падает, если `git diff` непустой.

Публикация берёт адреса из переменных репозитория `NEXUS_RELEASES_URL` и `NEXUS_SNAPSHOTS_URL`,
учётные данные — из секретов `NEXUS_USERNAME` и `NEXUS_PASSWORD`. Они подставляются в
`.mvn/ci-settings.xml` через `${env.*}`, на диск не пишутся.

## Ручная проверка на песочнице

Песочница отвечает фиксированными данными, реальные списания невозможны:

```java
TochkaClient client = TochkaClient.sandbox();   // enter.tochka.com/sandbox/v2, токен sandbox.jwt.token
```

Тестовые идентификаторы: `customerCode = 1234567ab`,
`accountId = 12345810901234567890/044525104`, `merchantId = 200000000001097`,
`merchantId` для СБП — из `sbpMerchants().getMerchantsList(...)`.
