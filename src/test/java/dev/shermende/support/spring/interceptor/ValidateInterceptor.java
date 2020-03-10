package dev.shermende.support.spring.interceptor;

import dev.shermende.support.spring.component.Interceptor;
import dev.shermende.support.spring.db.entity.Payload;
import dev.shermende.support.spring.validate.ValidationUtil;
import dev.shermende.support.spring.validator.PayloadValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateInterceptor implements Interceptor {

    private final PayloadValidator validator;

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
        final BindingResult bindingResult = ValidationUtil.validate(validator, o);
        if (bindingResult.hasErrors())
            throw new IllegalArgumentException(bindingResult.getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining()));
    }

}
