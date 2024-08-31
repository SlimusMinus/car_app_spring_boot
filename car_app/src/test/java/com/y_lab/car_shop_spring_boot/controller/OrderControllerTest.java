package com.y_lab.car_shop_spring_boot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.y_lab.car_shop_spring_boot.dto.OrderDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тестовый класс для проверки работы {@link OrderController} с использованием Spring Boot Test.
 * <p>
 * Этот класс содержит тесты для проверки различных операций с заказами через контроллер:
 * - Получение списка всех заказов
 * - Фильтрация заказов по статусу
 * - Получение заказа по идентификатору
 * - Создание нового заказа
 * - Изменение статуса заказа
 * - Установка статуса заказа в "отменён"
 * </p>
 * <p>
 * Тесты выполняются с использованием {@link MockMvc} для имитации HTTP-запросов и проверок ответов.
 * {@link ObjectMapper} используется для преобразования объектов {@link OrderDTO} в JSON и обратно.
 * </p>
 * <p>
 * Тесты проверяют, что ответ имеет ожидаемый тип содержимого, статус HTTP и правильность содержимого,
 * а также корректность обработки фильтрации, создания и изменения данных заказа.
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Тестирование OrderController")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Проверка получения всех заказов")
    void getAll() throws Exception {
        mockMvc.perform(get("/orders")
                        .characterEncoding("UTF-8"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2024-08-13")))
                .andExpect(content().string(containsString("2024-07-08")))
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    @DisplayName("Проверка получения заказов после фильтрации по статусу")
    void getAllAfterFilter() throws Exception {
        mockMvc.perform(get("/orders/filter/status/заказ оформлен"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2024-08-12")))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Проверка получения заказа по идентификатору")
    void getById() throws Exception {
        mockMvc.perform(get("/orders/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("2024-08-12")));
    }

    @Test
    @DisplayName("Проверка создания нового заказа")
    void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO(4, 1, LocalDate.parse("2024-08-12"), "заказ оформлен");
        String orderJson = objectMapper.writeValueAsString(orderDTO);
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Проверка изменения статуса заказа на 'отменен'")
    void canceled() throws Exception {
        mockMvc.perform(put("/orders/canceled")
                        .param("id", "2"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Проверка изменения статуса заказа")
    void changeStatus() throws Exception {
        mockMvc.perform(put("/orders/change-status")
                        .param("id", "2")
                        .param("status", "выдано"))
                .andExpect(status().isCreated());
    }
}