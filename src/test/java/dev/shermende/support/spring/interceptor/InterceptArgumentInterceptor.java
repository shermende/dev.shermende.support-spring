package dev.shermende.support.spring.interceptor;

import dev.shermende.support.spring.support.Interceptor;
import dev.shermende.support.spring.db.entity.InterceptArgumentEntity;
import dev.shermende.support.spring.db.entity.Payload;
import dev.shermende.support.spring.db.repository.InterceptArgumentEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterceptArgumentInterceptor implements Interceptor {

    private final InterceptArgumentEntityRepository interceptArgumentEntityRepository;

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
