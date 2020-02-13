package dev.shermende.support.spring.interceptor;

import dev.shermende.support.spring.component.Interceptor;
import dev.shermende.support.spring.db.entity.InterceptArgumentEntity;
import dev.shermende.support.spring.db.entity.Payload;
import dev.shermende.support.spring.db.repository.InterceptArgumentEntityRepository;
import org.springframework.stereotype.Component;

@Component
public class InterceptArgumentInterceptor implements Interceptor {

    private final InterceptArgumentEntityRepository interceptArgumentEntityRepository;

    public InterceptArgumentInterceptor(
        InterceptArgumentEntityRepository interceptArgumentEntityRepository
    ) {
        this.interceptArgumentEntityRepository = interceptArgumentEntityRepository;
    }

    @Override
    public boolean supports(
        Class<?> aClass
    ) {
        return Payload.class.isAssignableFrom(aClass);
    }

    @Override
    public void intercept(
        Object payload
    ) {
        interceptArgumentEntityRepository.save(
            InterceptArgumentEntity.builder()
                .interceptor(getClass().getName())
                .object(payload.getClass().getName())
                .build()
        );
    }

}
