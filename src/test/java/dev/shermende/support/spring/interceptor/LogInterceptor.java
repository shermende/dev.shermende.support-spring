package dev.shermende.support.spring.interceptor;

import dev.shermende.support.spring.component.Interceptor;
import dev.shermende.support.spring.component.Payload;
import dev.shermende.support.spring.db.entity.AccessLog;
import dev.shermende.support.spring.db.repository.AccessLogRepository;
import org.springframework.stereotype.Component;

@Component
public class LogInterceptor implements Interceptor {

    private final AccessLogRepository logRepository;

    public LogInterceptor(
            AccessLogRepository logRepository
    ) {
        this.logRepository = logRepository;
    }

    @Override
    public boolean supports(
            Class<?> aClass
    ) {
        return Payload.class.isAssignableFrom(aClass);
    }

    @Override
    public void intercept(
            Object o
    ) {
        final Payload payload = (Payload) o;
        logRepository.save(get(payload));
    }

    private AccessLog get(Payload payload) {
        return new AccessLog()
                .setInterceptor(getClass().getName())
                .setViewerId(1L)
                .setViewedId(payload.getId())
                .setClassName(payload.getClass().getName())
                .setToString(payload.toString());
    }

}