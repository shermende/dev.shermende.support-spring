package dev.shermende.support.spring.aop;

import dev.shermende.support.spring.support.Interceptor;
import dev.shermende.support.spring.support.annotation.InterceptArgument;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class AopIntercept {

    private final BeanFactory beanFactory;

    @Before("@annotation(dev.shermende.support.spring.support.annotation.Intercept)")
    public void intercept(JoinPoint joinPoint) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Annotation[][] annotations;
        try {
            annotations = getAnnotations(joinPoint, signature);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }

        for (int i = 0; i < annotations.length; i++) {
            for (int j = 0; j < annotations[i].length; j++) {
                final Object arg = joinPoint.getArgs()[i];
                Optional.of(annotations[i][j])
                    .filter(annotation -> annotation.annotationType().equals(InterceptArgument.class))
                    .ifPresent(annotation -> ((Interceptor) beanFactory.getBean(getClass(annotation))).doIntercept(arg));
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

    private Class<?> getClass(Annotation annotation) {
        Class<?> value;
        try {
            value = (Class<?>) annotation.getClass().getDeclaredMethod("value").invoke(annotation, (Object[]) null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
        return value;
    }

}
