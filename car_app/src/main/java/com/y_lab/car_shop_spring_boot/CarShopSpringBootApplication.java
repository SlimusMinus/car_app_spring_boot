package com.y_lab.car_shop_spring_boot;

import org.example.EnableSwaggerSpringDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Главный класс Spring Boot приложения для управления магазином автомобилей.
 * <p>
 * Этот класс содержит метод {@link #main(String[])} — точку входа приложения, которая запускает Spring Boot приложение.
 * </p>
 * <p>
 * Аннотация {@link SpringBootApplication} указывает, что это Spring Boot приложение, которое включает в себя
 * автоматическую конфигурацию, сканирование компонентов и настройки конфигурации.
 * Аннотация {@link EnableAspectJAutoProxy} активирует поддержку аспектно-ориентированного программирования (AOP),
 * позволяя использовать аспекты в приложении.
 * Аннотация {@link EnableSwaggerSpringDoc} включает поддержку документации API с помощью Swagger SpringDoc.
 * </p>
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableSwaggerSpringDoc
public class CarShopSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarShopSpringBootApplication.class, args);
    }
}
