package dev.shermende.support.spring.handler;

import dev.shermende.support.spring.component.Payload;
import dev.shermende.support.spring.component.annotation.Intercept;
import dev.shermende.support.spring.component.annotation.InterceptArgument;
import dev.shermende.support.spring.interceptor.ValidateInterceptor;
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
