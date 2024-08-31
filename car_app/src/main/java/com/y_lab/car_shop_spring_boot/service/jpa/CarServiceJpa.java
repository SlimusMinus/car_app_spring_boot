package com.y_lab.car_shop_spring_boot.service.jpa;

import com.y_lab.car_shop_spring_boot.dao.CarRepository;
import com.y_lab.car_shop_spring_boot.dto.CarDTO;
import com.y_lab.car_shop_spring_boot.mapper.CarMapper;
import com.y_lab.car_shop_spring_boot.model.Car;
import com.y_lab.car_shop_spring_boot.service.CarService;
import com.y_lab.car_shop_spring_boot.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для управления автомобилями с использованием JPA.
 * <p>
 * Этот сервис предоставляет методы для получения, сохранения, обновления и удаления автомобилей,
 * а также для фильтрации и преобразования данных о автомобилях в объекты {@link CarDTO}.
 * </p>
 *
 * <p>
 * Использует {@link CarRepository} для взаимодействия с базой данных. В классе реализованы методы для:
 * <ul>
 *     <li>Получения списка всех автомобилей {@link #getAll()}</li>
 *     <li>Преобразования списка автомобилей в список объектов {@link CarDTO} {@link #getAllDTO(List)}</li>
 *     <li>Получения автомобиля по его идентификатору {@link #getById(int)}</li>
 *     <li>Сохранения или обновления автомобиля {@link #saveOrUpdate(Car)}</li>
 *     <li>Удаления автомобиля по его идентификатору {@link #delete(int)}</li>
 *     <li>Фильтрации автомобилей по заданному критерию {@link #getFilteredCars(String, String)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * При фильтрации автомобилей, метод {@link #getFilteredCars(String, String)} поддерживает следующие параметры:
 * <ul>
 *     <li><b>brand</b> - фильтрация по бренду</li>
 *     <li><b>condition</b> - фильтрация по состоянию</li>
 *     <li><b>price</b> - фильтрация по цене</li>
 * </ul>
 * </p>
 *
 * <p>
 * В случае если запрашиваемый автомобиль не найден, генерируется исключение {@link NotFoundException}.
 * </p>
 */
@Service
public class CarServiceJpa implements CarService {

    private final CarRepository repository;

    public CarServiceJpa(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Car> getAll() {
        return repository.findAll();
    }

    @Override
    public List<CarDTO> getAllDTO(List<Car> cars) {
        return cars.stream()
                .map(CarMapper.INSTANCE::getCarDTO)
                .toList();
    }

    @Override
    public Car getById(int id) {
        final Optional<Car> optionalCar = repository.findById(id);
        if (optionalCar.isPresent()) {
            return optionalCar.get();
        }
        throw new NotFoundException("Car with id " + id + " not found");
    }

    @Override
    public Car saveOrUpdate(Car car) {
        return repository.save(car);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public List<Car> getFilteredCars(String nameFilter, String params) {
        return switch (nameFilter) {
            case "brand" -> repository.findByBrand(params);
            case "condition" -> repository.findByCondition(params);
            case "price" -> repository.findByPrice(Double.parseDouble(params));
            default -> throw new NotFoundException("Unexpected value: " + nameFilter);
        };
    }
}
