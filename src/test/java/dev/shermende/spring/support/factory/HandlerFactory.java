package dev.shermende.spring.support.factory;

import dev.shermende.spring.support.component.Payload;
import dev.shermende.spring.support.handler.InterceptorHandler;
import dev.shermende.spring.support.handler.InterceptorValidateHandler;
import dev.shermende.spring.support.handler.InterceptorWrongSupportHandler;
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
