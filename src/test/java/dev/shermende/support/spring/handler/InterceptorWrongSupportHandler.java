package dev.shermende.support.spring.handler;

import dev.shermende.support.spring.component.annotation.Intercept;
import dev.shermende.support.spring.component.annotation.InterceptArgument;
import dev.shermende.support.spring.db.entity.Payload;
import dev.shermende.support.spring.interceptor.WrongSupportInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InterceptorWrongSupportHandler implements ReturnHandler<Payload, Payload> {

    @Override
    @Intercept
    public Payload handle(
        @InterceptArgument(WrongSupportInterceptor.class) Payload payload
    ) {
        log.debug("unreachable code. exception in interceptor.");
        return payload;
    }

}
