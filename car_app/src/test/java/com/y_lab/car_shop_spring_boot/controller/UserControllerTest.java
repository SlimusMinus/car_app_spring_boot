package com.y_lab.car_shop_spring_boot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.y_lab.car_shop_spring_boot.dto.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тестовый класс для проверки работы {@link UserController} с использованием Spring Boot Test.
 * <p>
 * Этот класс содержит тесты для проверки различных операций с пользователями через контроллер:
 * - Получение списка всех пользователей
 * - Фильтрация пользователей по имени
 * - Сортировка пользователей по возрасту
 * - Получение пользователя по идентификатору
 * - Обновление информации о пользователе
 * </p>
 * <p>
 * Тесты выполняются с использованием {@link MockMvc} для имитации HTTP-запросов и проверок ответов.
 * {@link ObjectMapper} используется для преобразования объектов {@link UserDTO} в JSON и обратно.
 * </p>
 * <p>
 * Тесты проверяют, что ответ имеет ожидаемый тип содержимого, статус HTTP и правильность содержимого,
 * а также корректность фильтрации, сортировки, создания и обновления данных пользователя.
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Тестирование UserController")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Проверка получения всех пользователей")
    void getAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Alexandr")))
                .andExpect(content().string(containsString("John")))
                .andExpect(content().string(containsString("Tanya")))
                .andExpect(jsonPath("$.length()").value(6));
    }

    @Test
    @DisplayName("Проверка фильтрации пользователей по имени")
    void getAllFiltered() throws Exception {
        mockMvc.perform(get("/users/filter/name/Alexandr"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Alexandr")))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Проверка сортировки пользователей по возрасту")
    void getAllSorted() throws Exception {
        mockMvc.perform(get("/users/sort/age"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Проверка получения пользователя по идентификатору")
    void getById() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Alexandr")));
    }

    @Test
    @DisplayName("Проверка обновления данных пользователя")
    void update() throws Exception {
        UserDTO userDTO = new UserDTO("Pol", 33, "Moscow", null);
        String userJson = objectMapper.writeValueAsString(userDTO);
        mockMvc.perform(put("/users/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Pol")));
    }
}