# RESTful API (21 School)

REST-сервис для учёта клиентов, поставщиков и товаров магазина: CRUD по четырём сущностям, адреса с дедупликацией, управление остатками на складе, загрузка изображений товаров и интерактивная документация Swagger UI. Схема БД версионируется миграциями Flyway, а весь стенд (API + PostgreSQL) поднимается одной командой через Docker Compose.

Код проекта лежит в каталоге [`RESTfulAPI`](RESTfulAPI), Docker-файлы — рядом с ним.

## Стек

| Область | Технология | Версия (из `build.gradle.kts` / конфигов) |
|---|---|---|
| Язык / JDK | Java | 21 (`sourceCompatibility = 21`) |
| Сборка | Gradle (wrapper) | 8.14 |
| Фреймворк | Spring Boot (plugin) | 4.1.0 |
| Web | `spring-boot-starter-web` | из BOM Spring Boot 4.1.0 |
| Валидация | `spring-boot-starter-validation` | из BOM Spring Boot 4.1.0 |
| Persistence | `spring-boot-starter-data-jpa` (Hibernate, Spring Data JPA) | из BOM Spring Boot 4.1.0 |
| Миграции | `spring-boot-starter-flyway` + `flyway-database-postgresql` | BOM / 13.2.0 |
| Документация API | `springdoc-openapi-starter-webmvc-ui` (Swagger UI) | 3.1.0 |
| БД и драйвер | PostgreSQL JDBC | из BOM Spring Boot 4.1.0 |
| Boilerplate | Lombok | 1.18.42 |
| Управление зависимостями | `io.spring.dependency-management` | 1.1.7 |
| Тесты (зависимости) | JUnit BOM / Jupiter | 5.10.0 |
| Инфраструктура (Docker) | PostgreSQL | 16-alpine |

## Быстрый старт (one-command run)

Нужны Docker с Compose v2. Сборка происходит внутри контейнера, локальные JDK и Gradle не требуются.

```bash
cd RESTfulAPI
docker compose up --build
```

После запуска:

| Что | Адрес |
|---|---|
| REST API | http://localhost:8080/api/v1 |
| Swagger UI | http://localhost:8080/swagger/index.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| PostgreSQL (для psql / IDE) | `localhost:15432`, БД `restful_api`, пользователь `restful` |

Миграции Flyway применяются автоматически при старте приложения. Значения по умолчанию подходят для локального запуска. Чтобы изменить порты и пароль БД, скопируйте шаблон и отредактируйте его:

```bash
cp .env.example .env
docker compose up --build
```

Остановка с сохранением данных: `docker compose down`. Полный сброс вместе с БД и загруженными изображениями: `docker compose down -v`.


### Переменные окружения

| Переменная | Назначение |
|---|---|
| `DB_URL` | JDBC URL (в Compose собирается автоматически: `jdbc:postgresql://db:5432/${DB_NAME}`) |
| `DB_USER`, `DB_PASSWORD` | Учётные данные PostgreSQL (те же значения получает контейнер `db`) |
| `DB_NAME`, `DB_HOST_PORT`, `APP_PORT`, `JAVA_OPTS` | Параметры Compose: имя БД, порты на хосте, опции JVM |

Полный список в [`RESTfulAPI/.env.example`](RESTfulAPI/.env.example).

## Проверка функционала

Интерактивная документация со всеми запросами и схемами: **http://localhost:8080/swagger/index.html**. Авторизации в сервисе нет. Базовый путь — `/api/v1`.

Важно: пути с `@PostMapping("/")` и `@GetMapping("/")` нужно вызывать **со слэшем на конце** (`/api/v1/clients/`), так как в Spring 6+ сопоставление с хвостовым слэшем по умолчанию отключено.

| Ресурс | Метод и путь | Описание |
|---|---|---|
| Клиенты | `POST /api/v1/clients/` | Добавить клиента (`201`) |
| | `GET /api/v1/clients/?name=..&surname=..` | Найти клиентов (`name` обязателен, `surname` необязателен) |
| | `GET /api/v1/clients/all?limit=..&offset=..` | Все клиенты; постранично, если заданы оба параметра |
| | `PATCH /api/v1/clients/{clientUuid}` | Сменить адрес клиента |
| | `DELETE /api/v1/clients/{clientUuid}` | Удалить клиента |
| Поставщики | `POST /api/v1/suppliers/` | Добавить поставщика (`201`) |
| | `GET /api/v1/suppliers/all`, `GET /api/v1/suppliers/{supplierUuid}` | Список и получение по UUID |
| | `PATCH /api/v1/suppliers/{supplierUuid}` | Сменить адрес поставщика |
| | `DELETE /api/v1/suppliers/{supplierUuid}` | Удалить поставщика (вместе с его товарами) |
| Товары | `POST /api/v1/products/` | Добавить товар (`201`) |
| | `GET /api/v1/products/all` | Товары с остатком больше нуля |
| | `GET /api/v1/products/{productUuid}` | Товар по UUID |
| | `PATCH /api/v1/products/{productUuid}/stock` | Пополнить остаток (тело — целое число ≥ 1) |
| | `PATCH /api/v1/products/{productUuid}` | Списать остаток (тело — целое число) |
| | `DELETE /api/v1/products/{productUuid}` | Удалить товар |
| Изображения | `POST /api/v1/images/{productUuid}` | Загрузить изображение товара (`multipart/form-data`, поле `file`) |
| | `PUT /api/v1/images/{imageUuid}` | Заменить изображение |
| | `DELETE /api/v1/images/{imageUuid}` | Удалить изображение |
| | `GET /api/v1/images/products/{productUuid}/image` | Скачать изображение по UUID товара |
| | `GET /api/v1/images/{imageUuid}` | Скачать изображение по UUID |

## Структура проекта

```text
.
├── README.md
└── RESTfulAPI/
    ├── Dockerfile                     # multi-stage: Gradle 8.14 + JDK 21 → JRE 21 (alpine)
    ├── docker-compose.yml             # db (PostgreSQL) + app (API)
    ├── .env.example                   # шаблон переменных окружения
    ├── build.gradle.kts, settings.gradle.kts, gradlew, gradle/wrapper/
    └── src/main/
        ├── java/api/
        │   ├── Application.java
        │   ├── address/ client/ supplier/ product/ image/   # модули предметной области
        │   │   ├── web/         # controller, DTO (Request*/Response*), mapper
        │   │   ├── domain/      # model, service (бизнес-логика, транзакции)
        │   │   └── datasource/  # JPA-сущность, Spring Data репозиторий, mapper
        │   ├── common/domain/enums/   # Gender
        │   └── exceptions/            # доменные исключения, ErrorDTO, GlobalExceptionHandler
        └── resources/
            ├── application.properties
            └── db/migration/          # V1 (address, client), V2 (supplier), V3 (image, product)
```

Код организован по модулям предметной области, и внутри каждого модуля повторяются три слоя:

- **web** принимает HTTP, валидирует DTO (`@Valid`, `@Validated`) и документирует эндпоинты через аннотации springdoc.
- **domain** содержит модели и сервисы с бизнес-логикой и транзакциями (`@Transactional`).
- **datasource** отвечает за хранение: JPA-сущности, репозитории и мапперы между сущностями и доменными моделями.

Исключения превращаются в единый JSON-ответ в `GlobalExceptionHandler` (`@RestControllerAdvice`).

## Особенности и ограничения

**Данные**

- Схему БД создаёт Flyway (`V1`–`V3`), Hibernate только проверяет её (`ddl-auto=validate`). Миграции используют `gen_random_uuid()` и тип `ENUM`, поэтому нужен PostgreSQL 13+ (в Compose — 16).
- Адреса хранятся отдельной таблицей с уникальностью `(country, city, street)`. При создании или смене адреса клиента и поставщика сервис переиспользует существующую запись.
- Изображения хранятся в БД как `BYTEA`, а не в файловой системе. Лимиты загрузки: 1 МБ на файл и 2 МБ на запрос. Скачивание отдаёт `application/octet-stream` с именем `image_<uuid>.png` независимо от реального формата файла.
- Каскады на уровне БД: удаление адреса удаляет клиентов и поставщиков с этим адресом, удаление поставщика удаляет его товары, удаление изображения обнуляет `image_id` у товара.
- `GET /api/v1/products/all` возвращает только товары с остатком больше нуля. Пагинация есть только у списка клиентов.

**Валидация и поведение API**

- Страна, город и имя поставщика проверяются regex `^[a-zA-Zа-яА-Я]+$`, то есть только буквы: значения с пробелами («New York») и цифрами отклоняются.
- Списание остатка (`PATCH /products/{uuid}`) не ограничивает отрицательное значение в теле запроса: отрицательное число увеличит остаток. Дата последнего обновления при списании не меняется, при пополнении — меняется.
- Любое нарушение уникальности возвращает `409` с одним и тем же текстом «Duplicate entity combination name + supplier», в том числе для клиентов и поставщиков.
- Авторизации и аутентификации нет, все эндпоинты открыты.

**Сборка и эксплуатация**

- У контейнера `app` есть `healthcheck` по `GET /v3/api-docs` (OpenAPI-описание springdoc доступно без авторизации). Готовность БД контролируется `pg_isready`, `app` стартует только после неё.
- Автоматических тестов в проекте нет (каталога `src/test` нет), хотя зависимости JUnit подключены.
- В `ProductController` есть неиспользуемый импорт `org.osgi.annotation.bundle.Header`: он компилируется только благодаря транзитивной зависимости и его стоит удалить.
- Ветка по умолчанию в репозитории — `develop`.
- Данные PostgreSQL (включая изображения) хранятся в именованном томе `pgdata` и переживают `docker compose down`.
- Порт БД проброшен только на `127.0.0.1`, а пароль БД в шаблоне — dev-значение: перед любым развёртыванием вне локальной машины замените его через `.env`.
