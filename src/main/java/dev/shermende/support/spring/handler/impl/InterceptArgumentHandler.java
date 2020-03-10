package dev.shermende.support.spring.handler.impl;

import dev.shermende.support.spring.component.InterceptArgumentHolder;
import dev.shermende.support.spring.component.Interceptor;
import dev.shermende.support.spring.handler.NonReturnHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterceptArgumentHandler implements NonReturnHandler<InterceptArgumentHolder> {

    private final BeanFactory beanFactory;

    @Override
    public void handle(
        InterceptArgumentHolder payload
    ) {
        final Object arg = payload.getArgument();
        final Annotation annotation = payload.getAnnotation();
        ((Interceptor) beanFactory.getBean(getClass(annotation))).doIntercept(arg);
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
