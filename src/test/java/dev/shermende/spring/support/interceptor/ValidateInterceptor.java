package dev.shermende.spring.support.interceptor;

import dev.shermende.spring.support.component.Interceptor;
import dev.shermende.spring.support.component.Payload;
import dev.shermende.spring.support.validate.ValidationUtil;
import org.springframework.stereotype.Component;

@Component
public class ValidateInterceptor implements Interceptor {

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
        if (ValidationUtil.validate(o).isEmpty())
            return;
        throw new IllegalArgumentException();
    }

}
