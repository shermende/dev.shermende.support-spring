package dev.shermende.support.spring.interceptor;

import dev.shermende.support.spring.component.Interceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WrongSupportInterceptor implements Interceptor {

    @Override
    public boolean supports(
        Class<?> aClass
    ) {
        return Exception.class.isAssignableFrom(aClass);
    }

    @Override
    public void intercept(
        Object o
    ) {
        log.debug("unreachable code. exception in supports(...) method.");
    }

}
