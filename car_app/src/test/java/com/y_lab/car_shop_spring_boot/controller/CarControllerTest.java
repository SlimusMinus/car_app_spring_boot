package com.y_lab.car_shop_spring_boot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.y_lab.car_shop_spring_boot.dto.CarDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тестовый класс для проверки работы {@link CarController} с использованием Spring Boot Test.
 * <p>
 * Этот класс включает в себя тесты для проверки различных операций с автомобилями через контроллер:
 * - Получение списка всех автомобилей
 * - Фильтрация автомобилей по бренду
 * - Получение автомобиля по идентификатору
 * - Создание нового автомобиля
 * - Обновление существующего автомобиля
 * - Удаление автомобиля
 * </p>
 * <p>
 * Тесты выполняются с использованием {@link MockMvc} для имитации HTTP-запросов и проверок ответов.
 * {@link ObjectMapper} используется для преобразования объектов {@link CarDTO} в JSON и обратно.
 * </p>
 * <p>
 * Тесты проверяют, что ответ имеет ожидаемый тип содержимого, статус HTTP и правильность содержимого,
 * а также проверяют корректность обработки фильтрации, создания, обновления и удаления данных.
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Тестирование CarController")
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Проверка получения всех автомобилей")
        void getAll() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("BMW")))
                .andExpect(content().string(containsString("Audi")))
                .andExpect(content().string(containsString("Volvo")))
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    @DisplayName("Проверка получения автомобилей после фильтрации по бренду")
    void getAllAfterFilter() throws Exception {
        mockMvc.perform(get("/cars/filter/brand/Volvo"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Volvo")))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Проверка получения автомобиля по идентификатору")
    void getById() throws Exception {
        mockMvc.perform(get("/cars/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("BMW")));
    }

    @Test
    @DisplayName("Проверка создания и удаления автомобиля")
    void createAndDeleteCar() throws Exception {
        CarDTO carDTO = new CarDTO("Toyota", "Camry", 2022, 15888, "good");
        String carJson = objectMapper.writeValueAsString(carDTO);
        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Проверка обновления автомобиля")
    void update() throws Exception {
        CarDTO carDTO = new CarDTO("Haval", "3000", 2007, 15888, "new");
        String carJson = objectMapper.writeValueAsString(carDTO);
        mockMvc.perform(put("/cars/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Haval")));
    }

    @Test
    @DisplayName("Проверка удаления автомобиля")
    void deleteCar() throws Exception {
        int idToDelete = 6;
        mockMvc.perform(delete("/cars/{id}", idToDelete))
                .andExpect(status().isNoContent());
    }

}