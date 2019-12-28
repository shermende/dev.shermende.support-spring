package dev.shermende.spring.support.handler;

import dev.shermende.spring.support.component.Payload;
import dev.shermende.spring.support.component.annotation.Intercept;
import dev.shermende.spring.support.component.annotation.InterceptArgument;
import dev.shermende.spring.support.db.entity.SecuredEntity;
import dev.shermende.spring.support.db.repository.SecuredEntityRepository;
import dev.shermende.spring.support.interceptor.LogInterceptor;
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
