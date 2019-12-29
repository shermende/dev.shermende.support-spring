package dev.shermende.support.spring.handler;

import dev.shermende.support.spring.component.Payload;
import dev.shermende.support.spring.component.annotation.Intercept;
import dev.shermende.support.spring.component.annotation.InterceptArgument;
import dev.shermende.support.spring.interceptor.WrongSupportInterceptor;
import org.springframework.stereotype.Component;

@Component
public class InterceptorWrongSupportHandler implements NonReturnHandler<Payload> {

    @Override
    @Intercept
    public void handle(
             @InterceptArgument(WrongSupportInterceptor.class) Payload payload
    ) {
        // exception in interceptor
    }

}
