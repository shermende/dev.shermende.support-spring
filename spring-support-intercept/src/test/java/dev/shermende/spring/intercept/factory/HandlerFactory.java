package dev.shermende.spring.intercept.factory;

import dev.shermende.spring.intercept.component.Payload;
import dev.shermende.spring.intercept.handler.InterceptorHandler;
import dev.shermende.spring.intercept.handler.InterceptorValidateHandler;
import dev.shermende.spring.intercept.handler.InterceptorWrongSupportHandler;
import dev.shermende.spring.support.factory.AbstractFactory;
import dev.shermende.spring.support.handler.NonReturnHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

@Component
public class HandlerFactory extends AbstractFactory<String, NonReturnHandler<Payload>> {

    public HandlerFactory(
             BeanFactory beanFactory
    ) {
        super(beanFactory);
    }

    @Override
    protected void registration() {
        this.registry("interceptWrongSupport", InterceptorWrongSupportHandler.class);
        this.registry("interceptValidate", InterceptorValidateHandler.class);
        this.registry("intercept", InterceptorHandler.class);
    }

}
