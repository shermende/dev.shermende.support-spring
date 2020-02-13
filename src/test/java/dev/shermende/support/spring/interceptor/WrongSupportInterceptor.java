package dev.shermende.support.spring.interceptor;

import dev.shermende.support.spring.component.Interceptor;
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
        // exception before this step
    }

}
