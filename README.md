# Приложения для поиска информации о регионах
**_Приложения для работы с регионами и входящие в них города_**
*** 
## Описание
Приложение позволяет определить время восхода и захода солнце в разных регионах принимая координаты данного места.
Оно включает в себя сложные связи между сущностями, такие как "один ко многим", "многие к одному"
- Среда разработки: Spring Boot Framework
## Задача 1
1. Создайте и запустите локально простейший веб/REST сервис, используя любой открытый пример с использованием Java stack: Spring (Spring Boot)/Maven/Gradle/Jersey/Spring MVC.

2. Добавьте GET ендпоинт, принимающий входные параметры в качестве queryParams в URL согласно варианту, и возвращающий любой hard-coded результат в виде JSON согласно варианту.

## Задача 2
1. Подключите к проекту базу данных (PostgreSQL/MySQL/и т.д.). Реализуйте связи между сущностями:
    - (0 - 7 баллов) - Реализация связи один ко многим `@OneToMany`
    - (8 - 10 баллов) - Реализация связи многие ко многим `@ManyToMany`
2. Реализуйте CRUD-операции со всеми сущностями.

## Задача 3
1.  Добавить в проект GET ендпоинт (он должен быть полезный) с параметром(-ами). Данные должны быть получены из БД с помощью "кастомного" запроса (@Query) с параметром(-ами) ко вложенной сущности.
2.  Добавить простейший кэш в виде in-memory Map (в виде отдельного бина).

## Структура

- `Town` и `Region` имеют одностороннюю связь,так один регион может иметь несколько городов.
- Для управления данными используются репозитории Spring Data JPA, которые предоставляют методы для основных CRUD-операций. 
- Контроллеры обрабатывают веб-запросы и делегируют выполнение бизнес-логики сервисам.
- Для обеспечения безопасности и эффективности приложения происходит работа с логированием и кэшем `Cache`

## Запуск
1. **Подключение к базе данных**: Для полной функциональности проекта необходимо подключиться к базе данных Postgresql.

2. **Запуск приложения**: После настройки подключения к базе данных, запустите приложение. Можно использовать средства сборки Maven или Gradle для сборки и запуска проекта.

3. **Использование веб-интерфейса**: После успешного запуска приложения откройте веб-браузер и перейдите по адресу `http://localhost:8080/api/v1/weather`, чтобы получить все поля в формате JSON.
