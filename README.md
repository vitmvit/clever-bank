# Clever-Bank

## Общее описание проекта

Данный проект представляет собой консольное приложение банка. Приложение реализует операции пополнения и снятия средств
со счета, возможность перевода средств другому клиенту, а так же формирование чека по каждой операции.

### Основные сущности:

- Банк
- Счёт
- Пользователь
- Транзакция

### Стек:

- Java 17
- Gradle
- PostgreSQL
- JDBC
- Lombok
- Servlets

## Инструкция по запуску проекта

Проект работает под управлением Apache Tomcat. На стандартных настройках необходимо добавление ниже перечисленных
библиотек и файлов в папку apache/lib:

- application.yml
- jackson-core:2.15.2.jar
- jackson-databind:2.15.2.jar
- tomcat-servlet-api:10.1.7.jar
- postgresql:42.6.0

## CRUD операции

Для примера возьмем сущность User.

### GET-запрос:

    http://localhost:8080/api/users?id=1

Результат в формате JSON:

```json

{
  "id": 1,
  "name": "user_1"
}

```

### POST-запрос:

    http://localhost:8080/api/users/

```json

{
  "name": "user_3"
}

```

Результат в формате JSON:

```json

{
  "id": 4,
  "name": "user_3"
}

```

### PUT-запрос:

    http://localhost:8080/api/users?id=1

```json

{
  "name": "user_1"
}

```

Результат в формате JSON:

```json

{
  "id": 1,
  "name": "user_1"
}

```

### DELETE-запрос:

    http://localhost:8080/api/users?id=4

Результат:

``` text
User is deleted
```
