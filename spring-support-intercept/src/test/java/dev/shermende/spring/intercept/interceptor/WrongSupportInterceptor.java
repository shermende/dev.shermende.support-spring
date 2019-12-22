package dev.shermende.spring.intercept.interceptor;

import dev.shermende.spring.intercept.component.Interceptor;
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
