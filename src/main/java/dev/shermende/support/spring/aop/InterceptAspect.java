package dev.shermende.support.spring.aop;

import dev.shermende.support.spring.annotation.InterceptArgument;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class InterceptAspect {

    private final BeanFactory beanFactory;

    @SneakyThrows
    @Before("@annotation(dev.shermende.support.spring.annotation.Intercept)")
    public void intercept(
        JoinPoint joinPoint
    ) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Annotation[][] annotations = getAnnotations(joinPoint, signature);

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

    private Annotation[][] getAnnotations(
        JoinPoint joinPoint,
        MethodSignature signature
    ) throws NoSuchMethodException {
        return joinPoint.getTarget().getClass()
            .getMethod(getMethodName(signature), getParameterTypes(signature)).getParameterAnnotations();
    }

    private String getMethodName(
        MethodSignature signature
    ) {
        return signature.getMethod().getName();
    }

    private Class<?>[] getParameterTypes(
        MethodSignature signature
    ) {
        return signature.getMethod().getParameterTypes();
    }

}
