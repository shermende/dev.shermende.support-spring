package dev.shermende.spring.support.interceptor;

import dev.shermende.spring.support.component.Interceptor;
import org.springframework.stereotype.Component;

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
        // exception in support step check
    }

}
