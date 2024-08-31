package com.y_lab.car_shop_spring_boot.controller;

import com.y_lab.car_shop_spring_boot.dto.UserDTO;
import com.y_lab.car_shop_spring_boot.mapper.UserMapper;
import com.y_lab.car_shop_spring_boot.model.User;
import com.y_lab.car_shop_spring_boot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления сущностями {@link User}.
 * <p>
 * Этот контроллер предоставляет REST API для выполнения операций над сущностями {@code User},
 * таких как получение списка всех пользователей, фильтрация и сортировка пользователей,
 * получение пользователя по его идентификатору и обновление данных пользователя.
 * </p>
 *
 * <p>
 * Аннотация {@code @RestController} указывает, что данный класс является REST-контроллером,
 * который будет обрабатывать HTTP-запросы и возвращать данные в формате JSON.
 * Аннотация {@code @RequestMapping} с указанием пути {@code /users} и медиатипа
 * {@code MediaType.APPLICATION_JSON_VALUE} определяет базовый URL и формат данных для всех методов контроллера.
 * </p>
 *
 * <p>
 * Конструктор {@code UserController(UserService service)} принимает в качестве параметра сервисный слой
 * {@link UserService}, который используется для выполнения операций над данными пользователей.
 * </p>
 *
 * <p>
 * Метод {@code getAll()} обрабатывает GET-запросы на {@code /users} и возвращает список всех пользователей
 * в виде DTO объектов {@link UserDTO}.
 * </p>
 *
 * <p>
 * Метод {@code getAllFiltered(String name_filter, String params)} обрабатывает GET-запросы на
 * {@code /users/filter/{name_filter}/{params}} и возвращает отфильтрованный список пользователей в виде DTO объектов
 * {@link UserDTO}, основываясь на заданных фильтрах и параметрах.
 * </p>
 *
 * <p>
 * Метод {@code getAllSorted(String params)} обрабатывает GET-запросы на {@code /users/sort/{params}} и возвращает
 * отсортированный список пользователей в виде DTO объектов {@link UserDTO}, основываясь на заданных параметрах сортировки.
 * </p>
 *
 * <p>
 * Метод {@code getById(int id)} обрабатывает GET-запросы на {@code /users/{id}} и возвращает пользователя
 * по его идентификатору в виде DTO объекта {@link UserDTO}.
 * </p>
 *
 * <p>
 * Метод {@code update(UserDTO userDTO, int id)} обрабатывает PUT-запросы на {@code /users/{id}} и обновляет
 * данные пользователя на основе переданных данных {@link UserDTO} и идентификатора. Обновленный объект пользователя
 * возвращается в виде DTO объекта.
 * </p>
 */
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = service.getAll();
        return ResponseEntity.ok(service.getAllDTO(users));
    }

    @GetMapping("/filter/{name_filter}/{params}")
    public ResponseEntity<List<UserDTO>> getAllFiltered(@PathVariable String name_filter, @PathVariable String params) {
        List<User> users = service.getFilteredUsers(name_filter, params);
        return ResponseEntity.ok(service.getAllDTO(users));
    }

    @GetMapping("/sort/{params}")
    public ResponseEntity<List<UserDTO>> getAllSorted(@PathVariable String params) {
        List<User> users = service.getSortedUsers(params);
        return ResponseEntity.ok(service.getAllDTO(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable int id) {
        User user = service.getById(id);
        return ResponseEntity.ok(UserMapper.INSTANCE.getUserDTO(user));
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable int id) {
        User user = service.getById(id);
        User updateUser = UserMapper.INSTANCE.getUser(userDTO);
        updateUser.setUserId(id);
        updateUser.setLogin(user.getLogin());
        updateUser.setPassword(user.getPassword());
        User updatedUser = service.update(updateUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.INSTANCE.getUserDTO(updatedUser));
    }
}
