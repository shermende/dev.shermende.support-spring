package dev.shermende.support.spring.interceptor;

import dev.shermende.support.spring.component.Interceptor;
import dev.shermende.support.spring.db.entity.InterceptResultEntity;
import dev.shermende.support.spring.db.entity.Payload;
import dev.shermende.support.spring.db.repository.InterceptResultEntityRepository;
import org.springframework.stereotype.Component;

@Component
public class InterceptResultInterceptor implements Interceptor {

    private final InterceptResultEntityRepository interceptResultEntityRepository;

    public InterceptResultInterceptor(
        InterceptResultEntityRepository interceptResultEntityRepository
    ) {
        this.interceptResultEntityRepository = interceptResultEntityRepository;
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
        interceptResultEntityRepository.save(
            InterceptResultEntity.builder()
                .interceptor(getClass().getName())
                .object(payload.getClass().getName())
                .build()
        );
    }

}
