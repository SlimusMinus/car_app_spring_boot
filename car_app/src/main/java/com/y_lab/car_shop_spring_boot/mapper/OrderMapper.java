package com.y_lab.car_shop_spring_boot.mapper;

import com.y_lab.car_shop_spring_boot.dto.OrderDTO;
import com.y_lab.car_shop_spring_boot.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для преобразования (маппинга) объектов типа {@link Order} в объекты типа {@link OrderDTO} и обратно.
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
@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO getOdderDTO(Order order);

    Order getOrder(OrderDTO orderDTO);
}
