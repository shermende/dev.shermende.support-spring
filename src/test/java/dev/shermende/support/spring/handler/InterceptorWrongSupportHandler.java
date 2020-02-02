package dev.shermende.support.spring.handler;

import dev.shermende.support.spring.component.annotation.Intercept;
import dev.shermende.support.spring.component.annotation.InterceptArgument;
import dev.shermende.support.spring.db.entity.Payload;
import dev.shermende.support.spring.interceptor.WrongSupportInterceptor;
import org.springframework.stereotype.Component;

@Component
public class InterceptorWrongSupportHandler implements ReturnHandler<Payload, Payload> {

    @Override
    @Intercept
    public Payload handle(
            @InterceptArgument(WrongSupportInterceptor.class) Payload payload
    ) {
        return payload; // exception in interceptor
    }

}
