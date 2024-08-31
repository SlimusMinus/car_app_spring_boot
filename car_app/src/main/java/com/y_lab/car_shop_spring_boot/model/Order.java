package com.y_lab.car_shop_spring_boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Сущность для представления заказа в системе.
 * <p>
 * Эта сущность отображает таблицу {@code orders} в схеме {@code car_shop} базы данных и содержит информацию о заказе,
 * такую как идентификатор заказа, идентификаторы пользователя и автомобиля, дата заказа и статус заказа.
 * Аннотации {@code @Entity} и {@code @Table} указывают, что этот класс является сущностью JPA и соответствует таблице в базе данных.
 * </p>
 *
 * <p>
 * Аннотация {@code @Data} из библиотеки Lombok автоматически генерирует геттеры и сеттеры, методы {@code toString()},
 * {@code equals()} и {@code hashCode()}. Аннотации {@code @AllArgsConstructor} и {@code @NoArgsConstructor}
 * создают конструкторы с полным набором параметров и конструктор по умолчанию соответственно.
 * </p>
 *
 * <p>
 * Поле {@code orderId} является идентификатором заказа, который автоматически генерируется базой данных при создании новой записи.
 * </p>
 *
 * <p>
 * Поле {@code userId} представляет идентификатор пользователя, который разместил заказ. Значение должно быть положительным числом.
 * </p>
 *
 * <p>
 * Поле {@code carId} представляет идентификатор автомобиля, который был заказан. Значение должно быть положительным числом.
 * </p>
 *
 * <p>
 * Поле {@code date} представляет дату заказа. Значение должно быть датой в прошлом или настоящем времени, не в будущем.
 * </p>
 *
 * <p>
 * Поле {@code status} представляет статус заказа. Оно не может быть пустым и должно содержать текстовое описание текущего состояния заказа.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders", schema = "car_shop")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "user_id")
    @Positive(message = "userId должен быть положительным числом")
    private int userId;

    @Column(name = "car_id")
    @Positive(message = "carId должен быть положительным числом")
    private int carId;

    @Column(name = "date")
    @PastOrPresent(message = "Год должен быть не больше текущего года")
    private LocalDate date;

    @Column(name = "status")
    @NotBlank(message = "поле статус не должно быть пустым")
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
