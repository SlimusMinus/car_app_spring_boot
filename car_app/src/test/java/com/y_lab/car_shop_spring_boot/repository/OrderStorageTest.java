package com.y_lab.car_shop_spring_boot.repository;

import com.y_lab.car_shop_spring_boot.model.Order;
import com.y_lab.car_shop_spring_boot.service.OrderService;
import com.y_lab.car_shop_spring_boot.util.NotFoundException;
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

import static com.y_lab.car_shop_spring_boot.dataTest.Orders.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Тестовый класс для проверки работы сервиса {@link OrderService} с использованием контейнера PostgreSQL.
 * <p>
 * Этот класс использует {@link Testcontainers} для запуска экземпляра контейнера PostgreSQL и {@link SpringBootTest}
 * для интеграционного тестирования с реальной базой данных. Перед выполнением тестов выполняется скрипт SQL
 * {@code populateOrderTable.sql}, который заполняет таблицу заказов начальными данными.
 * </p>
 * <p>
 * В классе проводятся тесты для следующих операций:
 * - Создание нового заказа
 * - Получение всех заказов
 * - Получение заказа по идентификатору
 * - Проверка обработки запроса на несуществующий заказ
 * - Изменение статуса заказа
 * - Проверка обработки изменения статуса для несуществующего заказа
 * - Отмена заказа
 * - Фильтрация заказов по дате
 * </p>
 * <p>
 * Тесты используют аннотации {@link DisplayName} для предоставления читаемых описаний тестов и {@link Transactional}
 * для обеспечения транзакционности тестов. Аннотация {@link DynamicPropertySource} конфигурирует свойства базы данных
 * для подключения к контейнеру PostgreSQL.
 * </p>
 */
@Testcontainers
@SpringBootTest
@Sql(scripts = "/populateOrderTable.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Transactional
@SuppressWarnings("resource")
@DisplayName("Тестирование класса OrderStorage")
class OrderStorageTest {

    @Autowired
    private OrderService service;

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
    @DisplayName("Проверка создания нового заказа")
    void create() {
        service.saveOrUpdate(newOrder);
        assertAll(
                () -> assertThat(service.getAll().size()).isEqualTo(NEW_SIZE),
                () -> assertThat(newOrder).isEqualTo(service.getById(service.getAll().size()))
        );
    }

    @Test
    @DisplayName("Проверка получения всех заказов")
    void getAll() {
        assertAll(
                () -> assertThat(service.getAll()).isNotNull(),
                () -> assertThat(service.getAll()).containsAll(allListOrder)
        );
    }

    @Test
    @DisplayName("Проверка получения заказа по ID")
    void getById() {
        Order orderById = service.getById(order1.getOrderId());
        assertThat(orderById).isEqualTo(orderById);
    }

    @Test
    @DisplayName("Проверка получения несуществующего заказа по ID")
    void getByIdNotFound() {
        assertThatThrownBy(() -> service.getById(NOT_EXIST_ID)).isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Проверка изменения статуса заказа")
    void changeStatus() {
        service.changeStatus(order1.getOrderId(), newStatus);
        assertThat(service.getById(order1.getOrderId()).getStatus()).isEqualTo(newStatus);
    }

    @Test
    @DisplayName("Проверка изменения статуса несуществующего заказа")
    void changeStatusNotFound() {
        assertThatThrownBy(() -> service.changeStatus(NOT_EXIST_ID, newStatus)).isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Проверка отмены заказа")
    void canceled() {
        service.canceled(order2.getOrderId());
        assertThat(service.getById(order2.getOrderId()).getStatus()).isEqualTo(canceledStatus);
    }

    @Test
    @DisplayName("Проверка фильтрации заказов по дате")
    void filter() {
        final List<Order> filterOrders = service.getFilteredOrder("date", order1.getDate().toString());
        assertThat(filterOrders).isEqualTo(List.of(filterOrder));
    }
}