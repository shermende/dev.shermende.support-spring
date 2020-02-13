package dev.shermende.support.spring.handler;

import dev.shermende.support.spring.component.annotation.Intercept;
import dev.shermende.support.spring.component.annotation.InterceptArgument;
import dev.shermende.support.spring.db.entity.Payload;
import dev.shermende.support.spring.interceptor.ValidateInterceptor;
import org.springframework.stereotype.Component;

@Component
public class InterceptorValidateHandler implements ReturnHandler<Payload, Payload> {

    @Override
    @Intercept
    public Payload handle(
        @InterceptArgument(ValidateInterceptor.class) Payload payload
    ) {
        return payload; // exception in interceptor
    }

}
