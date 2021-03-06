package dev.shermende.support.spring.aop.intercept;

import dev.shermende.support.spring.aop.intercept.annotation.InterceptArgument;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 *
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class InterceptAspect implements InitializingBean {

    private final BeanFactory beanFactory;

    @SneakyThrows
    @Before("@annotation(dev.shermende.support.spring.aop.intercept.annotation.Intercept)")
    public void intercept(
        JoinPoint joinPoint
    ) {
        final MethodSignature signature =
            (MethodSignature) joinPoint.getSignature();
        final Annotation[][] annotations = joinPoint.getTarget().getClass()
            .getMethod(signature.getMethod().getName(), signature.getMethod().getParameterTypes())
            .getParameterAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            for (int j = 0; j < annotations[i].length; j++) {
                final Object arg = joinPoint.getArgs()[i];
                Optional.of(annotations[i][j])
                    .filter(annotation -> annotation.annotationType().equals(InterceptArgument.class))
                    .map(annotation -> (InterceptArgument) annotation)
                    .map(interceptArgument -> beanFactory.getBean(interceptArgument.value()))
                    .ifPresent(interceptor -> interceptor.doIntercept(arg));
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.warn("Attention!!! @dev.shermende.support.spring.aop.intercept.annotation.Intercept annotation enabled");
    }
}