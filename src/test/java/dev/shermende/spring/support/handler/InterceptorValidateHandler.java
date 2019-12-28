package dev.shermende.spring.support.handler;

import dev.shermende.spring.support.component.Payload;
import dev.shermende.spring.support.component.annotation.Intercept;
import dev.shermende.spring.support.component.annotation.InterceptArgument;
import dev.shermende.spring.support.interceptor.ValidateInterceptor;
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
