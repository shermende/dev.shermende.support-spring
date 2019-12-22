package dev.shermende.spring.intercept.handler;

import dev.shermende.spring.intercept.component.Payload;
import dev.shermende.spring.intercept.component.annotation.Intercept;
import dev.shermende.spring.intercept.component.annotation.InterceptArgument;
import dev.shermende.spring.intercept.db.entity.SecuredEntity;
import dev.shermende.spring.intercept.db.repository.SecuredEntityRepository;
import dev.shermende.spring.intercept.interceptor.LogInterceptor;
import dev.shermende.spring.support.handler.NonReturnHandler;
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
