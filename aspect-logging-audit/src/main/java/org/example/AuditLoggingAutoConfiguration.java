package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.aop.UserAuditAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для автоматического включения аудита пользователей.
 * <p>
 * Этот класс используется для настройки и регистрации аспектов аудита пользователей,
 * которые могут быть применены к различным методам в приложении. Он автоматически
 * сканирует компоненты в указанном пакете и инициализирует необходимые бины для работы аудита.
 * </p>
 *
 * <p>
 * Аннотация {@code @Configuration} указывает, что данный класс является конфигурационным
 * и содержит определения бинов, которые будут управляться контейнером Spring.
 * Аннотация {@code @Slf4j} добавляет логгер, который можно использовать для ведения логов
 * внутри этого класса. Аннотация {@code @ComponentScan} указывает на пакет, который необходимо
 * сканировать на наличие компонентов.
 * </p>
 *
 * <p>
 * Метод {@code userAuditAspect()} помечен аннотацией {@code @Bean} и возвращает экземпляр
 * {@link UserAuditAspect}. Это означает, что данный метод создает и настраивает бин,
 * который будет управляться контейнером Spring.
 * </p>
 */
@Configuration
@Slf4j
@ComponentScan(basePackages = "org.example")
public class AuditLoggingAutoConfiguration {
    @Bean
    public UserAuditAspect userAuditAspect() {
        return new UserAuditAspect();
    }
}
