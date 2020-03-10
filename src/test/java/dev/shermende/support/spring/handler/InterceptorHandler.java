package dev.shermende.support.spring.handler;

import dev.shermende.support.spring.component.annotation.Intercept;
import dev.shermende.support.spring.component.annotation.InterceptArgument;
import dev.shermende.support.spring.component.annotation.InterceptResult;
import dev.shermende.support.spring.db.entity.Payload;
import dev.shermende.support.spring.db.repository.PayloadRepository;
import dev.shermende.support.spring.interceptor.InterceptArgumentInterceptor;
import dev.shermende.support.spring.interceptor.InterceptResultInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterceptorHandler implements ReturnHandler<Payload, Payload> {

    private final PayloadRepository repository;

    @Override
    @Intercept
    @InterceptResult(InterceptResultInterceptor.class)
    public Payload handle(
        @InterceptArgument(InterceptArgumentInterceptor.class) Payload payload
    ) {
        return repository.save(payload);
    }

}
