package com.y_lab.car_shop_spring_boot.dao;

import com.y_lab.car_shop_spring_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для управления сущностями {@link User}.
 * <p>
 * Этот интерфейс предоставляет методы для взаимодействия с базой данных для сущностей {@code User}.
 * Он расширяет интерфейс {@link JpaRepository}, который обеспечивает стандартные CRUD операции,
 * а также дополнительные методы для поиска и сортировки пользователей по возрасту, имени и городу.
 * </p>
 *
 * <p>
 * Метод {@code getByAge(int age)} возвращает список пользователей, которые соответствуют заданному возрасту.
 * </p>
 *
 * <p>
 * Метод {@code getByName(String name)} возвращает список пользователей, которые соответствуют заданному имени.
 * </p>
 *
 * <p>
 * Метод {@code getByCity(String city)} возвращает список пользователей, которые соответствуют заданному городу.
 * </p>
 *
 * <p>
 * Метод {@code getSortByName()} выполняет запрос и возвращает список всех пользователей, отсортированных по имени в порядке возрастания.
 * </p>
 *
 * <p>
 * Метод {@code getSortByAge()} выполняет запрос и возвращает список всех пользователей, отсортированных по возрасту в порядке возрастания.
 * </p>
 *
 * <p>
 * Метод {@code getSortByCity()} выполняет запрос и возвращает список всех пользователей, отсортированных по городу в порядке возрастания.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> getByAge(int age);

    List<User> getByName(String name);

    List<User> getByCity(String city);

    @Query("SELECT u FROM User u ORDER BY u.name ASC")
    List<User> getSortByName();

    @Query("SELECT u FROM User u ORDER BY u.age ASC")
    List<User> getSortByAge();

    @Query("SELECT u FROM User u ORDER BY u.city ASC")
    List<User> getSortByCity();

}

