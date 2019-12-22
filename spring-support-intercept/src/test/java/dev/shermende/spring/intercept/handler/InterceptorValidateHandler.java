package dev.shermende.spring.intercept.handler;

import dev.shermende.spring.intercept.component.Payload;
import dev.shermende.spring.intercept.component.annotation.Intercept;
import dev.shermende.spring.intercept.component.annotation.InterceptArgument;
import dev.shermende.spring.intercept.interceptor.ValidateInterceptor;
import dev.shermende.spring.support.handler.NonReturnHandler;
import org.springframework.stereotype.Component;

@Component
public class InterceptorValidateHandler implements NonReturnHandler<Payload> {

    @Override
    @Intercept
    public void handle(
            @InterceptArgument(ValidateInterceptor.class) Payload payload
    ) {
        // exception in interceptor
    }

}
