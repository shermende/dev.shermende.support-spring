package dev.shermende.support.spring.validator;

import dev.shermende.support.spring.db.entity.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class PayloadValidator implements Validator {

    @Override
    public boolean supports(
        Class<?> aClass
    ) {
        return Payload.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        errors.reject("Global error");
    }

}