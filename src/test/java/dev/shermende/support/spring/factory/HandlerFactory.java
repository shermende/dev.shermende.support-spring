package dev.shermende.support.spring.factory;

import dev.shermende.support.spring.component.Payload;
import dev.shermende.support.spring.handler.InterceptorHandler;
import dev.shermende.support.spring.handler.InterceptorValidateHandler;
import dev.shermende.support.spring.handler.InterceptorWrongSupportHandler;
import dev.shermende.support.spring.handler.NonReturnHandler;
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
