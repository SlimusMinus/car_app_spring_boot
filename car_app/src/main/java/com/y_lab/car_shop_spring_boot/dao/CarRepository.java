package com.y_lab.car_shop_spring_boot.dao;

import com.y_lab.car_shop_spring_boot.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для управления сущностями {@link Car}.
 * <p>
 * Этот интерфейс предоставляет методы для взаимодействия с базой данных для сущностей {@code Car}.
 * Он расширяет интерфейс {@link JpaRepository}, который обеспечивает стандартные CRUD операции,
 * а также дополнительные методы для поиска автомобилей по определённым критериям, таким как бренд, состояние и цена.
 * </p>
 *
 * <p>
 * Метод {@code findByBrand(String brand)} возвращает список автомобилей, которые соответствуют заданному бренду.
 * </p>
 *
 * <p>
 * Метод {@code findByCondition(String condition)} возвращает список автомобилей, которые соответствуют заданному состоянию.
 * </p>
 *
 * <p>
 * Метод {@code findByPrice(double price)} возвращает список автомобилей, которые имеют заданную цену.
 * </p>
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByBrand(String brand);

    List<Car> findByCondition(String condition);

    List<Car> findByPrice(double price);
}
