package com.y_lab.car_shop_spring_boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Сущность для представления пользователя в системе.
 * <p>
 * Эта сущность отображает таблицу {@code user} в схеме {@code car_shop} базы данных и содержит информацию о пользователе,
 * такую как идентификатор пользователя, логин, пароль, имя, возраст, город и роли. Аннотации {@code @Entity} и {@code @Table}
 * указывают, что этот класс является сущностью JPA и соответствует таблице в базе данных.
 * </p>
 *
 * <p>
 * Аннотации {@code @Setter}, {@code @AllArgsConstructor} и {@code @NoArgsConstructor} создают методы установки (сеттеры),
 * конструктор с полным набором параметров и конструктор по умолчанию соответственно.
 * </p>
 *
 * <p>
 * Поле {@code userId} является идентификатором пользователя, который автоматически генерируется базой данных при создании новой записи.
 * </p>
 *
 * <p>
 * Поле {@code login} представляет логин пользователя. Оно должно содержать от 4 до 20 символов и не может быть пустым или содержать только пробелы.
 * Логин также должен быть уникальным в базе данных.
 * </p>
 *
 * <p>
 * Поле {@code password} представляет пароль пользователя. Оно должно содержать не менее 4 символов и не может быть пустым или содержать только пробелы.
 * </p>
 *
 * <p>
 * Поле {@code name} представляет имя пользователя. Оно может содержать до 50 символов и не может быть пустым или содержать только пробелы.
 * </p>
 *
 * <p>
 * Поле {@code age} представляет возраст пользователя. Оно должно быть положительным числом и не может быть {@code null}.
 * </p>
 *
 * <p>
 * Поле {@code city} представляет город пользователя. Оно может содержать до 100 символов и не может быть пустым или содержать только пробелы.
 * </p>
 *
 * <p>
 * Поле {@code role} представляет набор ролей пользователя. Роли хранятся в отдельной таблице {@code user_roles} и загружаются немедленно (EAGER).
 * </p>
 *
 * <p>
 * Метод {@code equals()} переопределяет стандартное поведение для сравнения объектов. Сравнение осуществляется по идентификатору пользователя {@code userId}.
 * </p>
 *
 * <p>
 * Метод {@code hashCode()} переопределяет стандартное поведение для вычисления хэш-кода объекта. Хэш-код вычисляется на основе идентификатора пользователя {@code userId}.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "car_shop")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @NotBlank(message = "Login cannot be null, empty or contain only spaces")
    @Size(min = 4, max = 20, message = "Login must be between 4 and 20 characters")
    @Column(unique = true, nullable = false)
    private String login;

    @NotBlank(message = "Password cannot be null, empty or contain only spaces")
    @Size(min = 4, message = "Password must be at least 4 characters long")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Name cannot be null, empty or contain only spaces")
    @Size(max = 50, message = "Name must be up to 50 characters long")
    private String name;

    @NotNull(message = "Age cannot be null")
    @Min(value = 0, message = "Age must be a positive number")
    private int age;

    @NotBlank(message = "City cannot be null, empty or contain only spaces")
    @Size(max = 100, message = "City must be up to 100 characters long")
    private String city;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(schema = "car_shop", name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Roles> role = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
