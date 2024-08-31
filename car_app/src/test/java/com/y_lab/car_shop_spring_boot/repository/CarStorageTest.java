package com.y_lab.car_shop_spring_boot.repository;

import com.y_lab.car_shop_spring_boot.model.Car;
import com.y_lab.car_shop_spring_boot.service.jpa.CarServiceJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.y_lab.car_shop_spring_boot.dataTest.Cars.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Тестовый класс для проверки работы сервиса {@link CarServiceJpa} с использованием контейнера PostgreSQL.
 * <p>
 * Этот класс использует {@link Testcontainers} для запуска экземпляра контейнера PostgreSQL и {@link SpringBootTest}
 * для интеграционного тестирования с реальной базой данных. Перед выполнением тестов выполняется скрипт SQL
 * {@code populateCarTable.sql}, который заполняет таблицу автомобилей начальными данными.
 * </p>
 * <p>
 * В классе проводятся тесты для следующих операций:
 * - Получение всех автомобилей из базы данных
 * - Получение автомобиля по идентификатору
 * - Обновление существующего автомобиля
 * - Удаление автомобиля из базы данных
 * - Фильтрация автомобилей по различным критериям
 * </p>
 * <p>
 * Тесты используют аннотации {@link DisplayName} для предоставления читаемых описаний тестов и {@link Transactional}
 * для обеспечения транзакционности тестов. Аннотация {@link DynamicPropertySource} конфигурирует свойства базы данных
 * для подключения к контейнеру PostgreSQL.
 * </p>
 */
@Testcontainers
@SpringBootTest
@Sql(scripts = "/populateCarTable.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Transactional
@SuppressWarnings("resource")
@DisplayName("Тестирование класса CarStorageJdbc")
public class CarStorageTest {

    @Autowired
    private CarServiceJpa service;

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureTestDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgresContainer::getDriverClassName);
        registry.add("spring.jpa.generate-ddl", () -> true);
        registry.add("spring.liquibase.enabled", () -> false);
    }

    @Test
    @DisplayName("Тестирование метода getAll() для получения всех автомобилей")
    void testGetAll() {
        List<Car> cars = service.getAll();
        assertAll(
                () -> assertThat(cars).hasSize(5),
                () -> assertThat(service.getAll()).isEqualTo(CAR_LIST)
        );
    }

    @Test
    @DisplayName("Тестирование метода getById(int) для получения автомобиля по идентификатору")
    void getById() {
        Car car = service.getById(GET_CAR_ID);
        assertThat(car2).isEqualTo(car);
    }

    @Test
    @DisplayName("Проверка обновления существующего автомобиля")
    void update() {
        service.saveOrUpdate(carUpdate);
        Car carUpdate = service.getAll().get(car4.getCarId());
        assertThat(carUpdate).isEqualTo(carUpdate);

    }

    @Test
    @DisplayName("Проверка удаления автомобиля")
    void delete() {
        service.delete(car4.getCarId());
        List<Car> cars = service.getAll();
        assertThat(cars).doesNotContain(car4);
    }


    @Test
    @DisplayName("Проверка фильтрации автомобилей")
    void filter() {
        final List<Car> listVolvo = service.getFilteredCars("brand", "Volvo");
        final List<Car> listNewCar = service.getFilteredCars("condition", "new");
        final List<Car> filterPriceCar = service.getFilteredCars("price", "25000");
        assertAll(
                () -> assertThat(listVolvo).containsAll(brandFilteredCars),
                () -> assertThat(listNewCar).containsAll(conditionFilteredCars),
                () -> assertThat(filterPriceCar).containsAll(priceFilteredCars)
        );
    }
}
