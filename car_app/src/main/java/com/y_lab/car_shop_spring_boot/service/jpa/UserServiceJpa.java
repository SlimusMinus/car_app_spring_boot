package com.y_lab.car_shop_spring_boot.service.jpa;

import com.y_lab.car_shop_spring_boot.dao.UserRepository;
import com.y_lab.car_shop_spring_boot.dto.UserDTO;
import com.y_lab.car_shop_spring_boot.mapper.UserMapper;
import com.y_lab.car_shop_spring_boot.model.Car;
import com.y_lab.car_shop_spring_boot.model.User;
import com.y_lab.car_shop_spring_boot.service.UserService;
import com.y_lab.car_shop_spring_boot.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для управления пользователями с использованием JPA.
 * <p>
 * Этот сервис предоставляет методы для получения всех пользователей, преобразования их в {@link UserDTO},
 * получения пользователя по идентификатору, обновления пользователя, а также для сортировки и фильтрации пользователей.
 * </p>
 *
 * <p>
 * Использует {@link UserRepository} для взаимодействия с базой данных. В классе реализованы методы для:
 * <ul>
 *     <li>Получения списка всех пользователей {@link #getAll()}</li>
 *     <li>Преобразования списка пользователей в список объектов {@link UserDTO} {@link #getAllDTO(List)}</li>
 *     <li>Получения пользователя по его идентификатору {@link #getById(int)}</li>
 *     <li>Обновления данных пользователя {@link #update(User)}</li>
 *     <li>Получения отсортированного списка пользователей {@link #getSortedUsers(String)}</li>
 *     <li>Получения отфильтрованного списка пользователей {@link #getFilteredUsers(String, String)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Метод {@link #getSortedUsers(String)} поддерживает следующие параметры сортировки:
 * <ul>
 *     <li><b>name</b> - сортировка по имени пользователя</li>
 *     <li><b>age</b> - сортировка по возрасту пользователя</li>
 *     <li><b>city</b> - сортировка по городу пользователя</li>
 * </ul>
 * </p>
 *
 * <p>
 * Метод {@link #getFilteredUsers(String, String)} поддерживает следующие параметры фильтрации:
 * <ul>
 *     <li><b>name</b> - фильтрация по имени пользователя</li>
 *     <li><b>age</b> - фильтрация по возрасту пользователя</li>
 *     <li><b>city</b> - фильтрация по городу пользователя</li>
 * </ul>
 * </p>
 *
 * <p>
 * При получении пользователя по идентификатору {@link #getById(int)}, если пользователь с указанным идентификатором
 * не найден, генерируется исключение {@link NotFoundException}.
 * </p>
 *
 * <p>
 * Метод {@link #update(User)} сохраняет изменения пользователя в базе данных. Если пользователь не существует в базе
 * данных, метод добавляет нового пользователя.
 * </p>
 */
@Service
public class UserServiceJpa implements UserService {

    private final UserRepository repository;

    public UserServiceJpa(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public List<UserDTO> getAllDTO(List<User> users) {
        return users.stream()
                .map(UserMapper.INSTANCE::getUserDTO)
                .toList();
    }

    @Override
    public User getById(int id) {
        final Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException("User with id " + id + " not found");
    }

    @Override
    public User update(User user) {
        return repository.save(user);
    }

    @Override
    public List<User> getSortedUsers(String paramsSort) {
        return switch (paramsSort) {
            case "name" -> repository.getSortByName();
            case "age" -> repository.getSortByAge();
            case "city" -> repository.getSortByCity();
            default -> throw new NotFoundException("Unexpected value: " + paramsSort);
        };
    }

    @Override
    public List<User> getFilteredUsers(String nameFilter, String params) {
        return switch (nameFilter) {
            case "name" -> repository.getByName(params);
            case "age" -> repository.getByAge(Integer.parseInt(params));
            case "city" -> repository.getByCity(params);
            default -> throw new NotFoundException("Unexpected value: " + nameFilter);
        };
    }
}
