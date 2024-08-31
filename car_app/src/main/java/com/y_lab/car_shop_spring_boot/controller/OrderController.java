package com.y_lab.car_shop_spring_boot.controller;

import com.y_lab.car_shop_spring_boot.dto.OrderDTO;
import com.y_lab.car_shop_spring_boot.mapper.OrderMapper;
import com.y_lab.car_shop_spring_boot.model.Order;
import com.y_lab.car_shop_spring_boot.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления сущностями {@link Order}.
 * <p>
 * Этот контроллер предоставляет REST API для выполнения операций над сущностями {@code Order},
 * таких как получение списка всех заказов, фильтрация заказов, получение заказа по его идентификатору,
 * создание нового заказа, отмена заказа и изменение статуса заказа.
 * </p>
 *
 * <p>
 * Аннотация {@code @RestController} указывает, что данный класс является REST-контроллером,
 * который будет обрабатывать HTTP-запросы и возвращать данные в формате JSON.
 * Аннотация {@code @RequestMapping} с указанием пути {@code /orders} и медиатипа
 * {@code MediaType.APPLICATION_JSON_VALUE} определяет базовый URL и формат данных для всех методов контроллера.
 * </p>
 *
 * <p>
 * Конструктор {@code OrderController(OrderService service)} принимает в качестве параметра сервисный слой
 * {@link OrderService}, который используется для выполнения операций над данными заказов.
 * </p>
 *
 * <p>
 * Метод {@code getAll()} обрабатывает GET-запросы на {@code /orders} и возвращает список всех заказов
 * в виде DTO объектов {@link OrderDTO}.
 * </p>
 *
 * <p>
 * Метод {@code getAllAfterFilter(String name_filter, String params)} обрабатывает GET-запросы на
 * {@code /orders/filter/{name_filter}/{params}} и возвращает отфильтрованный список заказов в виде DTO объектов
 * {@link OrderDTO}, основываясь на заданных фильтрах и параметрах.
 * </p>
 *
 * <p>
 * Метод {@code getById(int id)} обрабатывает GET-запросы на {@code /orders/{id}} и возвращает заказ
 * по его идентификатору в виде DTO объекта {@link OrderDTO}.
 * </p>
 *
 * <p>
 * Метод {@code create(OrderDTO orderDTO)} обрабатывает POST-запросы на {@code /orders} и создает новый заказ
 * на основе переданных данных {@link OrderDTO}. Возвращает созданный объект заказа в виде DTO с присвоенным идентификатором.
 * </p>
 *
 * <p>
 * Метод {@code canceled(int id)} обрабатывает PUT-запросы на {@code /orders/canceled} и отменяет заказ
 * на основе переданного идентификатора. Возвращает отмененный объект заказа в виде DTO.
 * </p>
 *
 * <p>
 * Метод {@code changeStatus(int id, String status)} обрабатывает PUT-запросы на {@code /orders/change-status}
 * и изменяет статус заказа на основе переданного идентификатора и нового статуса. Возвращает обновленный объект заказа в виде DTO.
 * </p>
 */
@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAll() {
        List<Order> list = service.getAll();
        return ResponseEntity.ok(service.getAllDTO(list));
    }

    @GetMapping("/filter/{name_filter}/{params}")
    public ResponseEntity<List<OrderDTO>> getAllAfterFilter(@PathVariable String name_filter, @PathVariable String params) {
        List<Order> orders = service.getFilteredOrder(name_filter, params);
        return ResponseEntity.ok(service.getAllDTO(orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable int id) {
        Order orderById = service.getById(id);
        return ResponseEntity.ok(OrderMapper.INSTANCE.getOdderDTO(orderById));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        Order order = OrderMapper.INSTANCE.getOrder(orderDTO);
        Order savedOrder = service.saveOrUpdate(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.INSTANCE.getOdderDTO(savedOrder));
    }

    @PutMapping("/canceled")
    public ResponseEntity<OrderDTO> canceled(@RequestParam(value = "id", required = false) int id) {
        final Order canceledOrder = service.canceled(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.INSTANCE.getOdderDTO(canceledOrder));
    }

    @PutMapping("/change-status")
    public ResponseEntity<OrderDTO> changeStatus(@RequestParam(value = "id", required = false) int id,
                                                 @RequestParam(value = "status", required = false) String status) {
        final Order changeStatusOrder = service.changeStatus(id, status);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.INSTANCE.getOdderDTO(changeStatusOrder));
    }

}
