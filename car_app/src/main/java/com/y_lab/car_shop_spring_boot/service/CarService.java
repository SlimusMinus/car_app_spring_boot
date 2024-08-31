package com.y_lab.car_shop_spring_boot.service;

import com.y_lab.car_shop_spring_boot.dto.CarDTO;
import com.y_lab.car_shop_spring_boot.model.Car;

import java.util.List;

/**
 * Сервис для управления данными об автомобилях в приложении.
 * <p>
 * Интерфейс предоставляет методы для выполнения операций с автомобилями, таких как получение всех автомобилей,
 * преобразование их в объекты {@link CarDTO}, получение автомобиля по идентификатору, сохранение или обновление
 * автомобиля, удаление автомобиля и фильтрация автомобилей.
 * </p>
 *
 * <p>
 * Интерфейс взаимодействует с репозиторием для выполнения операций с данными автомобилей. Все методы, предоставляемые
 * этим сервисом, предназначены для работы с данными о автомобилях в приложении.
 * </p>
 */
public interface CarService {

    public List<Car> getAll();

    public List<CarDTO> getAllDTO(List<Car> cars);

    public Car getById(int id);

    public Car saveOrUpdate(Car car);

    public void delete(int id);

    public List<Car> getFilteredCars(String nameFilter, String params);
}
