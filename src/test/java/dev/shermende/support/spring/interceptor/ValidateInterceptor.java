package dev.shermende.support.spring.interceptor;

import dev.shermende.support.spring.component.Interceptor;
import dev.shermende.support.spring.component.Payload;
import dev.shermende.support.spring.validate.ValidationUtil;
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
