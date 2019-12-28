package dev.shermende.spring.support.handler.impl;

import dev.shermende.spring.support.component.InterceptArgumentHolder;
import dev.shermende.spring.support.component.Interceptor;
import dev.shermende.spring.support.handler.NonReturnHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

@Component
public class InterceptArgumentHandler implements NonReturnHandler<InterceptArgumentHolder> {

    private final BeanFactory beanFactory;

    public InterceptArgumentHandler(
            BeanFactory beanFactory
    ) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void handle(
            InterceptArgumentHolder payload
    ) {
        final Object arg = payload.getArgument();
        final Annotation annotation = payload.getAnnotation();

        Class<?> value;
        try {
            value = (Class<?>) annotation.getClass().getDeclaredMethod("value").invoke(annotation, (Object[]) null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }

        ((Interceptor) beanFactory.getBean(value)).doIntercept(arg);
    }

}
