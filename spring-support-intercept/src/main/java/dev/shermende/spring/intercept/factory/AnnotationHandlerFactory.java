package dev.shermende.spring.intercept.factory;

import dev.shermende.spring.intercept.component.InterceptArgumentHolder;
import dev.shermende.spring.intercept.component.annotation.InterceptArgument;
import dev.shermende.spring.intercept.handler.InterceptArgumentHandler;
import dev.shermende.spring.support.factory.AbstractFactory;
import dev.shermende.spring.support.handler.NonReturnHandler;
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
