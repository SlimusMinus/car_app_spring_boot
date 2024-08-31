package com.y_lab.car_shop_spring_boot.service;

import com.y_lab.car_shop_spring_boot.dto.OrderDTO;
import com.y_lab.car_shop_spring_boot.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления данными о заказах в приложении.
 * <p>
 * Интерфейс предоставляет методы для выполнения операций с заказами, таких как сохранение или обновление заказа,
 * получение всех заказов, преобразование их в объекты {@link OrderDTO}, получение заказа по идентификатору,
 * изменение статуса заказа, отмена заказа и фильтрация заказов.
 * </p>
 *
 * <p>
 * Интерфейс взаимодействует с репозиторием для выполнения операций с данными заказов. Все методы, предоставляемые
 * этим сервисом, предназначены для работы с данными о заказах в приложении.
 * </p>
 */
public interface OrderService {

    public Order saveOrUpdate(Order order);

    public List<Order> getAll();

    public List<OrderDTO> getAllDTO(List<Order> orders);

    public Order getById(int id);

    public Order changeStatus(int id, String status);

    public Order canceled(int id);

    public List<Order> getFilteredOrder(String nameFilter, String params);
}
