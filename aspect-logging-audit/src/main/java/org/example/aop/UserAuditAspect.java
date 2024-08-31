package org.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Аспект для аудита действий пользователей и логирования времени выполнения методов.
 * <p>
 * Этот класс использует аспекты для перехвата вызовов методов, помеченных аннотацией {@code @Service},
 * и выполняет аудит пользовательских действий, логирует время выполнения методов,
 * а также обрабатывает исключения, возникшие во время выполнения этих методов.
 * </p>
 *
 * <p>
 * Аннотация {@code @Aspect} указывает, что этот класс является аспектом,
 * который можно использовать для внедрения дополнительной функциональности
 * в методы других классов. Аннотация {@code @Slf4j} добавляет логгер для ведения логов.
 * </p>
 *
 * <p>
 * Метод {@code auditUserAction(JoinPoint joinPoint)} помечен аннотацией {@code @Before},
 * что означает, что он будет выполняться перед каждым вызовом метода, помеченного аннотацией {@code @Service}.
 * Этот метод логирует имя метода и его аргументы, чтобы фиксировать действия пользователей.
 * </p>
 *
 * <p>
 * Метод {@code logExecutionTime(ProceedingJoinPoint joinPoint)} помечен аннотацией {@code @Around},
 * что означает, что он будет выполняться вокруг вызова метода, помеченного аннотацией {@code @Service}.
 * Этот метод измеряет и логирует время выполнения метода, а затем возвращает результат выполнения метода.
 * </p>
 *
 * <p>
 * Метод {@code logException(JoinPoint joinPoint, Exception ex)} помечен аннотацией {@code @AfterThrowing},
 * что означает, что он будет выполняться после выброса исключения в методе, помеченном аннотацией {@code @Service}.
 * Этот метод логирует информацию о методе, его аргументах и исключении, которое было выброшено.
 * </p>
 */

@Aspect
@Slf4j
public class UserAuditAspect {
    @Before("within(@org.springframework.stereotype.Service *)")
    public void auditUserAction(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("User action: {} with arguments {}", methodName, args);
    }

    @Around("within(@org.springframework.stereotype.Service *)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        log.info("Method {} executed in {} ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }

    @AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error("Exception in method {} with arguments {}. Exception: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs(),
                ex.getMessage(), ex);
    }

}
