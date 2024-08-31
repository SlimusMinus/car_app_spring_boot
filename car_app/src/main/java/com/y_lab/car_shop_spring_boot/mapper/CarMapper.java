package com.y_lab.car_shop_spring_boot.mapper;

import com.y_lab.car_shop_spring_boot.dto.CarDTO;
import com.y_lab.car_shop_spring_boot.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для преобразования (маппинга) объектов типа {@link Car} в объекты типа {@link CarDTO} и обратно.
 * <p>
 * Этот интерфейс используется для автоматического создания маппера с помощью библиотеки MapStruct.
 * </p>
 *
 * <p>
 * Аннотации и поля:
 * <ul>
 *     <li>{@code @Mapper} — аннотация, указывающая, что данный интерфейс является маппером для MapStruct.</li>
 *     <li>{@code INSTANCE} — статическое поле, содержащее экземпляр автоматически сгенерированного класса-мэппера.</li>
 * </ul>
 * </p>
 */
@Mapper(componentModel = "spring")
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDTO getCarDTO(Car car);

    Car getCar(CarDTO carDTO);
}
