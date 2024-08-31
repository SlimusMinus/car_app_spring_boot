package com.y_lab.car_shop_spring_boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;


/**
 * Сущность для представления автомобиля в системе.
 * <p>
 * Эта сущность отображает таблицу {@code car} в схеме {@code car_shop} базы данных и содержит информацию об автомобиле,
 * такую как идентификатор, бренд, модель, год выпуска, цена и состояние. Аннотации {@code @Entity} и {@code @Table}
 * указывают, что этот класс является сущностью JPA и соответствует таблице в базе данных.
 * </p>
 *
 * <p>
 * Аннотация {@code @Data} из библиотеки Lombok автоматически генерирует геттеры и сеттеры, методы {@code toString()},
 * {@code equals()} и {@code hashCode()}. Аннотации {@code @AllArgsConstructor} и {@code @NoArgsConstructor}
 * создают конструкторы с полным набором параметров и конструктор по умолчанию соответственно.
 * </p>
 *
 * <p>
 * Поле {@code carId} является идентификатором сущности, который автоматически генерируется базой данных при создании новой записи.
 * </p>
 *
 * <p>
 * Поле {@code brand} представляет бренд автомобиля. Оно не может быть {@code null} или пустым.
 * </p>
 *
 * <p>
 * Поле {@code model} представляет модель автомобиля. Оно не может быть {@code null} или пустым.
 * </p>
 *
 * <p>
 * Поле {@code year} представляет год выпуска автомобиля. Оно должно быть положительным числом и не может быть больше текущего года.
 * Значение текущего года определяется константой {@code CURRENT_YEAR}.
 * </p>
 *
 * <p>
 * Поле {@code price} представляет цену автомобиля. Оно должно быть положительным числом и не может быть {@code null}.
 * </p>
 *
 * <p>
 * Поле {@code condition} представляет состояние автомобиля. Оно не может быть {@code null} или пустым.
 * </p>
 *
 * <p>
 * Константа {@code CURRENT_YEAR} используется для проверки, что год выпуска автомобиля не превышает текущий год.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car", schema = "car_shop")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private int carId;

    @Column(name = "brand")
    @NotNull
    @NotBlank(message = "Brand cannot be null, empty or contain only spaces")
    private String brand;

    @Column(name = "model")
    @NotNull
    @NotBlank(message = "Model cannot be null, empty or contain only spaces")
    private String model;

    @Column(name = "year")
    @NotNull
    @Min(value = 0, message = "Year must be a positive number")
    @Max(value = CURRENT_YEAR, message = "Year cannot be greater than the current year")
    private int year;

    @Column(name = "price")
    @NotNull
    @Min(value = 0, message = "Price must be a positive number")
    private double price;

    @Column(name = "condition")
    @NotNull
    @NotBlank(message = "Condition cannot be null, empty or contain only spaces")
    private String condition;

    private static final int CURRENT_YEAR = 2024;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carId == car.carId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId);
    }
}
