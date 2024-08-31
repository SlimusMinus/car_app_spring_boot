package com.y_lab.car_shop_spring_boot.service;

import com.y_lab.car_shop_spring_boot.dto.UserDTO;
import com.y_lab.car_shop_spring_boot.model.User;

import java.util.List;

/**
 * Сервис для управления данными о пользователях в приложении.
 * <p>
 * Интерфейс предоставляет методы для выполнения операций с пользователями, таких как получение всех пользователей,
 * преобразование их в объекты {@link UserDTO}, получение пользователя по идентификатору, обновление пользователя,
 * получение отсортированных и отфильтрованных списков пользователей.
 * </p>
 *
 * <p>
 * Интерфейс взаимодействует с репозиторием для выполнения операций с данными пользователей. Все методы, предоставляемые
 * этим сервисом, предназначены для работы с данными о пользователях в приложении.
 * </p>
 */
public interface UserService {
    public List<User> getAll();

    public List<UserDTO> getAllDTO(List<User> users);

    public User getById(int id);

    public User update(User user);

    public List<User> getSortedUsers(String paramsSort);

    public List<User> getFilteredUsers(String nameFilter, String params);
}
