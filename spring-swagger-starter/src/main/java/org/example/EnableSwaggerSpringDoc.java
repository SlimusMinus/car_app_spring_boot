package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для включения Swagger и SpringDoc в проекте.
 * <p>
 * Эта аннотация используется для автоматического включения поддержки Swagger и SpringDoc
 * в приложениях на базе Spring. Она должна применяться на уровне класса, чтобы
 * обеспечить генерацию документации API.
 * </p>
 *
 * <p>
 * Аннотация {@code @Target(ElementType.TYPE)} указывает, что данная аннотация может быть применена
 * только к типам (например, к классам или интерфейсам). Аннотация {@code @Retention(RetentionPolicy.RUNTIME)}
 * указывает, что аннотация будет доступна во время выполнения, что позволяет использовать её
 * для конфигурации приложения.
 * </p>
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableSwaggerSpringDoc {
}
