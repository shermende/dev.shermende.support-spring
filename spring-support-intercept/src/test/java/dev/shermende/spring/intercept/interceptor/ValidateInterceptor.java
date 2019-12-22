package dev.shermende.spring.intercept.interceptor;

import dev.shermende.spring.intercept.component.Interceptor;
import dev.shermende.spring.intercept.component.Payload;
import dev.shermende.spring.validate.ValidationUtil;
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
