package com.y_lab.car_shop_spring_boot.dao;

import com.y_lab.car_shop_spring_boot.model.Order;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Репозиторий для управления сущностями {@link Order}.
 * <p>
 * Этот интерфейс предоставляет методы для взаимодействия с базой данных для сущностей {@code Order}.
 * Он расширяет интерфейс {@link JpaRepository}, который обеспечивает стандартные CRUD операции,
 * а также дополнительные методы для поиска заказов по дате и статусу.
 * </p>
 *
 * <p>
 * Метод {@code findByDate(LocalDate date)} возвращает список заказов, которые соответствуют заданной дате.
 * Параметр даты должен быть не позже текущей даты, что обеспечивается аннотацией {@code @PastOrPresent}.
 * </p>
 *
 * <p>
 * Метод {@code findByStatus(String status)} возвращает список заказов, которые соответствуют заданному статусу.
 * </p>
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByDate(@PastOrPresent(message = "Год должен быть не больше текущего года") LocalDate date);

    List<Order> findByStatus(String status);
}
