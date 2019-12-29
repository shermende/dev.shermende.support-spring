package dev.shermende.support.spring.handler;

import dev.shermende.support.spring.component.Payload;
import dev.shermende.support.spring.component.annotation.Intercept;
import dev.shermende.support.spring.component.annotation.InterceptArgument;
import dev.shermende.support.spring.db.entity.SecuredEntity;
import dev.shermende.support.spring.db.repository.SecuredEntityRepository;
import dev.shermende.support.spring.interceptor.LogInterceptor;
import org.springframework.stereotype.Component;

@Component
public class InterceptorHandler implements NonReturnHandler<Payload> {

    private final SecuredEntityRepository repository;

    public InterceptorHandler(
            SecuredEntityRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    @Intercept
    public void handle(
             @InterceptArgument(LogInterceptor.class) Payload s
    ) {
        repository.save(new SecuredEntity().setUuid(s.getUuid()));
    }

}
