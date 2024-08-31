package com.y_lab.car_shop_spring_boot.controller;

import com.y_lab.car_shop_spring_boot.dto.CarDTO;
import com.y_lab.car_shop_spring_boot.mapper.CarMapper;
import com.y_lab.car_shop_spring_boot.model.Car;
import com.y_lab.car_shop_spring_boot.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления сущностями {@link Car}.
 * <p>
 * Этот контроллер предоставляет REST API для выполнения CRUD операций над сущностями {@code Car}.
 * Поддерживается получение списка всех автомобилей, фильтрация автомобилей по заданным критериям,
 * получение автомобиля по его идентификатору, создание нового автомобиля, обновление существующего
 * автомобиля и удаление автомобиля.
 * </p>
 *
 * <p>
 * Аннотация {@code @RestController} указывает, что данный класс является REST-контроллером,
 * который будет обрабатывать HTTP-запросы и возвращать данные в формате JSON.
 * Аннотация {@code @RequestMapping} с указанием пути {@code /cars} и медиатипа
 * {@code MediaType.APPLICATION_JSON_VALUE} определяет базовый URL и формат данных для всех методов контроллера.
 * </p>
 *
 * <p>
 * Конструктор {@code CarController(CarService service)} принимает в качестве параметра сервисный слой
 * {@link CarService}, который используется для выполнения операций над данными автомобилей.
 * </p>
 *
 * <p>
 * Метод {@code getAll()} обрабатывает GET-запросы на {@code /cars} и возвращает список всех автомобилей
 * в виде DTO объектов {@link CarDTO}.
 * </p>
 *
 * <p>
 * Метод {@code getAllAfterFilter(String name_filter, String params)} обрабатывает GET-запросы на
 * {@code /cars/filter/{name_filter}/{params}} и возвращает отфильтрованный список автомобилей в виде DTO объектов
 * {@link CarDTO}, основываясь на заданных фильтрах и параметрах.
 * </p>
 *
 * <p>
 * Метод {@code getById(int id)} обрабатывает GET-запросы на {@code /cars/{id}} и возвращает автомобиль
 * по его идентификатору в виде DTO объекта {@link CarDTO}.
 * </p>
 *
 * <p>
 * Метод {@code create(CarDTO carDTO)} обрабатывает POST-запросы на {@code /cars} и создает новый автомобиль
 * на основе переданных данных {@link CarDTO}. Возвращает созданный объект автомобиля с присвоенным идентификатором.
 * </p>
 *
 * <p>
 * Метод {@code update(CarDTO carDTO, int id)} обрабатывает PUT-запросы на {@code /cars/{id}} и обновляет
 * существующий автомобиль на основе переданных данных {@link CarDTO} и идентификатора. Возвращает обновленный объект автомобиля.
 * </p>
 *
 * <p>
 * Метод {@code delete(int id)} обрабатывает DELETE-запросы на {@code /cars/{id}} и удаляет автомобиль по его идентификатору.
 * Возвращает ответ без содержания с кодом состояния 204 (No Content).
 * </p>
 */
@RestController
@RequestMapping(value = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {
    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAll() {
        final List<Car> cars = service.getAll();
        final List<CarDTO> carsDTO = service.getAllDTO(cars);
        return ResponseEntity.ok(carsDTO);
    }

    @GetMapping("/filter/{name_filter}/{params}")
    public ResponseEntity<List<CarDTO>> getAllAfterFilter(@PathVariable String name_filter, @PathVariable String params) {
        List<Car> cars = service.getFilteredCars(name_filter, params);
        return ResponseEntity.ok(service.getAllDTO(cars));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getById(@PathVariable int id) {
        Car carById = service.getById(id);
        return ResponseEntity.ok(CarMapper.INSTANCE.getCarDTO(carById));
    }

    @PostMapping
    public ResponseEntity<Car> create(@RequestBody CarDTO carDTO) {
        Car car = CarMapper.INSTANCE.getCar(carDTO);
        Car savedCar = service.saveOrUpdate(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Car> update(@RequestBody CarDTO carDTO, @PathVariable int id) {
        Car car = CarMapper.INSTANCE.getCar(carDTO);
        car.setCarId(id);
        Car updatedCar = service.saveOrUpdate(car);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
