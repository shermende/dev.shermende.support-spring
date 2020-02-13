package dev.shermende.support.spring.factory.impl;

import dev.shermende.support.spring.component.InterceptArgumentHolder;
import dev.shermende.support.spring.component.annotation.InterceptArgument;
import dev.shermende.support.spring.factory.AbstractFactory;
import dev.shermende.support.spring.handler.NonReturnHandler;
import dev.shermende.support.spring.handler.impl.InterceptArgumentHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

@Component
public class AnnotationHandlerFactory extends AbstractFactory<String, NonReturnHandler<InterceptArgumentHolder>> {

    public AnnotationHandlerFactory(
        BeanFactory beanFactory
    ) {
        super(beanFactory);
    }

    @Override
    protected void registration() {
        registry(InterceptArgument.class.getName(), InterceptArgumentHandler.class);
    }

}
