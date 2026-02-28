Property View (Java 21)
Требования

Java 21

Maven

Запуск

mvn spring-boot:run

Приложение стартует на порту 8092.

Базовый префикс API

Все методы доступны с префиксом: /property-view

Эндпоинты

GET /property-view/hotels — список отелей (кратко)

GET /property-view/hotels/{id} — отель по id (подробно)

POST /property-view/hotels — создать отель

POST /property-view/hotels/{id}/amenities — добавить amenities

GET /property-view/search — поиск (name, brand, city, country, amenities)

GET /property-view/histogram/{param} — histogram (brand/city/country/amenities)