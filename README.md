# Car Shop Spring Boot Application

## Описание

Это Spring Boot приложение для управления автомобильным магазином. Проект состоит из трёх основных модулей:

1. **aspect-logging-audit**: Модуль для работы с аспектами логирования и аудита.
2. **spring-swagger-starter**: Модуль для создания и использования документации Swagger.
3. **car_app**: Основной модуль приложения, содержащий логику бизнес-процессов и контроллеры.

## Запуск приложения
Для запуска приложения выполните следующие шаги:

1. **Запустите базу данных:**

   Убедитесь, что у вас установлен Docker и Docker Compose. Для создания базы данных в контейнере Docker выполните команду:
   ```sh
   docker-compose up
2. **Запустите приложение:**

  запустите класс CarShopSpringBootApplication, находящийся в src/main/java/com/y_lab/car_shop_spring_boot/CarShopSpringBootApplication.java

3. **Документация Swagger:**

Для получения документации к контроллерам через Swagger необходимо в адресной строке браузера ввести http://localhost:8080/swagger-ui/index.html