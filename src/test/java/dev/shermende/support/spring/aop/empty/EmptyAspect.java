package dev.shermende.support.spring.aop.empty;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class EmptyAspect {

    @SneakyThrows
    @Around("@annotation(dev.shermende.support.spring.aop.empty.annotation.Empty)")
    public Object empty(ProceedingJoinPoint proceedingJoinPoint) {
        final Object proceed = proceedingJoinPoint.proceed();
        log.trace("{}", proceed);
        return proceed;
    }

}