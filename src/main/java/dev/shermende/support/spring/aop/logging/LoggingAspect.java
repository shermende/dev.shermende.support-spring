package dev.shermende.support.spring.aop.logging;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;

/**
 *
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect implements InitializingBean {

    @SneakyThrows
    @Around("@annotation(dev.shermende.support.spring.aop.logging.annotation.Logging)")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            final Class<?> aClass = proceedingJoinPoint.getTarget().getClass();
            final MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            final Method method = signature.getMethod();
            final Object[] args = proceedingJoinPoint.getArgs();
            log.debug("[Logging before] [{}#{}] [Args:{}]",
                aClass.getSimpleName(),
                method.getName(),
                args
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        final Object proceed = proceedingJoinPoint.proceed();
        try {
            final Class<?> aClass = proceedingJoinPoint.getTarget().getClass();
            final MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            final Method method = signature.getMethod();
            final Object[] args = proceedingJoinPoint.getArgs();
            log.debug("[Logging after] [{}#{}] [Args:{}] [Result:{}]",
                aClass.getSimpleName(),
                method.getName(),
                args,
                proceed
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return proceed;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.warn("Attention!!! @dev.shermende.support.spring.aop.logging.annotation.Logging annotation enabled");
    }

}