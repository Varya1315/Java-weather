# Приложения для определение восход и заката солнца
**_Приложения для определения положения солнца по времени и месту_**
*** 
## Описание
Приложение позволяет определить время восхода и захода солнце в разных регионах принимая координаты данного места.

Среда разработки: Spring Boot Framework
## Задача
1. Создайте и запустите локально простейший веб/REST сервис, используя любой открытый пример с использованием Java stack: Spring (Spring Boot)/Maven/Gradle/Jersey/Spring MVC.

2. Добавьте GET ендпоинт, принимающий входные параметры в качестве queryParams в URL согласно варианту, и возвращающий любой hard-coded результат в виде JSON согласно варианту.

## Структура

Для выполнения задания необходимы классы:

-   **SpringweaApplication**: Этот класс является точкой входа в ваше приложение Spring Boot. Он содержит метод `main`, который запускает приложение.

-   **Towns**: Этот класс представляет сущность книги в вашей библиотеке. Он содержит поля, такие как `coordinates`, `nameTowns`, `positionSun`, `time`,которые используются для поиска и вывода положения солнца. Класс также содержит геттеры и сеттеры для доступа к этим полям.
-   **WeatherController**: Этот класс является контроллером Spring MVC, который обрабатывает HTTP-запросы,связанные с положением солнца. Он содержит методы для отображения полей , поиска по имени города или позиции солнца и удаления полей.
-   **MemoryWeatherDAO** представляет собой простую реализацию хранилища полей в памяти приложения. Вместо базы данных, книги хранятся в списке `mainTown`.
-   **WeatherService**: Интерфейс, который определяет методы для работы с полями. Обычно содержит методы для выполнения бизнес-логики.
-   **WeatherServiceImplementation**: Реализация интерфейса `WeatherService`. В этом классе содержится реализация методов для работы с полями,а именно `findAllTowes`,`saveTowns`,`findByPositionSun`,`findByName`,`deleteTownsByName`,`deleteTownsByPosition`.
-   **WeatherService**: Реализация интерфейса `WeatherService`. В этом классе содержится реализация методов для работы с полями и их хранения в памяти приложения .

## Запуск
**Использование веб-интерфейса**: После успешного запуска приложения откройте веб-браузер и перейдите по адресу `http://localhost:8080/api/v1/weather`, чтобы получить все поля в формате JSON.