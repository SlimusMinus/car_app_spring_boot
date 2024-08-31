package com.y_lab.car_shop_spring_boot.repository;

import com.y_lab.car_shop_spring_boot.model.User;
import com.y_lab.car_shop_spring_boot.service.UserService;
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

import static com.y_lab.car_shop_spring_boot.dataTest.Users.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Интеграционные тесты для проверки работы {@link UserService} с использованием контейнера PostgreSQL.
 * <p>
 * Этот класс использует {@link Testcontainers} для запуска экземпляра контейнера PostgreSQL и {@link SpringBootTest}
 * для интеграционного тестирования с реальной базой данных. Перед выполнением тестов используется скрипт SQL
 * {@code populateUserTable.sql} для начальной загрузки данных в таблицу пользователей.
 * </p>
 * <p>
 * В классе тестируются следующие функциональности:
 * - Получение всех пользователей
 * - Получение пользователя по идентификатору
 * - Обработка случая, когда пользователь с указанным идентификатором не существует
 * - Фильтрация пользователей по различным критериям (имя, город, возраст)
 * - Сортировка пользователей по различным критериям (имя, возраст)
 * - Обновление данных пользователя
 * </p>
 * <p>
 * Тесты используют аннотации {@link DisplayName} для предоставления понятных описаний тестов и {@link Transactional}
 * для обеспечения изолированности тестов. Аннотация {@link DynamicPropertySource} настраивает свойства подключения
 * к базе данных для контейнера PostgreSQL.
 * </p>
 */
@Testcontainers
@SpringBootTest
@Sql(scripts = "/populateUserTable.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Transactional
@SuppressWarnings("resource")
@DisplayName("Тестирование класса UserService")
public class UserStorageTest {

    @Autowired
    private UserService service;

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
    @DisplayName("Проверка получения всех пользователей")
    void getAll() {
        assertAll(
                () -> assertThat(service.getAll()).isNotNull(),
                () -> assertThat(USER_LIST).isEqualTo(service.getAll())
        );
    }

    @Test
    @DisplayName("Тест на получение пользователя по идентификатору")
    void getById() {
        User user = service.getById(USER_GET_ID);
        assertThat(manager1).isEqualTo(user);
    }

    @Test
    @DisplayName("Тест на выброс NotFoundException при попытке получить несуществующего пользователя")
    void getByIdNotFound() {
        assertThatThrownBy(() -> service.getById(NOT_EXIST_ID)).isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Проверка фильтрации пользователей по критериям")
    void filter() {
        assertAll(
                () -> assertThat(service.getFilteredUsers("name", "Alexandr")).isEqualTo(NAME_PHILTER),
                () -> assertThat(service.getFilteredUsers("city", "Moscow")).isEqualTo(CITY_PHILTER),
                () -> assertThat(service.getFilteredUsers("age", "33")).isEqualTo(AGE_PHILTER)
        );
    }

    @Test
    @DisplayName("Проверка сортировки пользователей по критериям")
    void sort() {
        assertAll(
                () -> assertThat(service.getSortedUsers("name")).containsAll(USERS_NAME_SORT),
                () -> assertThat(service.getSortedUsers("age")).containsAll(USERS_AGE_SORT)
        );
    }

    @Test
    @DisplayName("Проверка обновления данных пользователя")
    void update() {
        service.update(editClient2);
        assertThat(service.getById(client2.getUserId())).isEqualTo(editClient2);
    }

}
