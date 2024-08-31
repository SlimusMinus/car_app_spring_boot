package com.y_lab.car_shop_spring_boot.service.jpa;

import com.y_lab.car_shop_spring_boot.dao.OrderRepository;
import com.y_lab.car_shop_spring_boot.dto.OrderDTO;
import com.y_lab.car_shop_spring_boot.mapper.OrderMapper;
import com.y_lab.car_shop_spring_boot.model.Order;
import com.y_lab.car_shop_spring_boot.service.OrderService;
import com.y_lab.car_shop_spring_boot.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для управления заказами с использованием JPA.
 * <p>
 * Этот сервис предоставляет методы для создания, обновления, получения и удаления заказов,
 * а также для фильтрации и изменения статуса заказов.
 * </p>
 *
 * <p>
 * Использует {@link OrderRepository} для взаимодействия с базой данных. В классе реализованы методы для:
 * <ul>
 *     <li>Сохранения или обновления заказа {@link #saveOrUpdate(Order)}</li>
 *     <li>Получения списка всех заказов {@link #getAll()}</li>
 *     <li>Преобразования списка заказов в список объектов {@link OrderDTO} {@link #getAllDTO(List)}</li>
 *     <li>Получения заказа по его идентификатору {@link #getById(int)}</li>
 *     <li>Изменения статуса заказа {@link #changeStatus(int, String)}</li>
 *     <li>Отмены заказа {@link #canceled(int)}</li>
 *     <li>Фильтрации заказов по заданному критерию {@link #getFilteredOrder(String, String)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Метод {@link #getFilteredOrder(String, String)} поддерживает следующие параметры фильтрации:
 * <ul>
 *     <li><b>date</b> - фильтрация по дате заказа</li>
 *     <li><b>status</b> - фильтрация по статусу заказа</li>
 * </ul>
 * </p>
 *
 * <p>
 * При изменении статуса заказа метод {@link #changeOrderStatus(int, String)} обновляет статус заказа
 * и сохраняет изменения. Если заказ с указанным идентификатором не найден, генерируется исключение
 * {@link NotFoundException}.
 * </p>
 *
 * <p>
 * При отмене заказа метод {@link #canceled(int)} устанавливает статус заказа в "cancelled" и
 * сохраняет изменения. Если заказ с указанным идентификатором не найден, также генерируется
 * исключение {@link NotFoundException}.
 * </p>
 */
@Service
public class OrderServiceJpa implements OrderService {

    private final OrderRepository repository;

    public OrderServiceJpa(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order saveOrUpdate(Order order) {
        return repository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }

    @Override
    public List<OrderDTO> getAllDTO(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper.INSTANCE::getOdderDTO)
                .toList();
    }

    @Override
    public Order getById(int id) {
        final Optional<Order> optionalOrder = repository.findById(id);
        if(optionalOrder.isPresent()){
            return optionalOrder.get();
        }
        throw new NotFoundException("Order with id " + id + " not found");
    }

    @Override
    public Order changeStatus(int id, String status) {
        return changeOrderStatus(id, status);
    }

    @Override
    public Order canceled(int id) {
        return changeOrderStatus(id, "cancelled");
    }

    @Override
    public List<Order> getFilteredOrder(String nameFilter, String params) {
        return switch (nameFilter) {
            case "date" -> repository.findByDate(LocalDate.parse(params));
            case "status" -> repository.findByStatus(params);
            default -> throw new NotFoundException("Unexpected value: " + nameFilter);
        };
    }

    private Order changeOrderStatus(int id, String status) {
        final Optional<Order> optionalOrder = repository.findById(id);
        if(optionalOrder.isPresent()){
            final Order order = optionalOrder.get();
            order.setStatus(status);
            return saveOrUpdate(order);
        }
        throw new NotFoundException("Order with id " + id + " not found");
    }

}
