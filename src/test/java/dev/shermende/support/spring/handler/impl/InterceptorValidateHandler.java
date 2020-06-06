package dev.shermende.support.spring.handler.impl;

import dev.shermende.support.spring.db.entity.Payload;
import dev.shermende.support.spring.handler.ReturnHandler;
import dev.shermende.support.spring.interceptor.ValidateInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import dev.shermende.support.spring.support.annotation.*;

@Slf4j
@Component
public class InterceptorValidateHandler implements ReturnHandler<Payload, Payload> {

    @Override
    @Intercept
    public Payload handle(
        @InterceptArgument(ValidateInterceptor.class) Payload payload
    ) {
        log.debug("unreachable code. exception in interceptor.");
        return payload;
    }

}
