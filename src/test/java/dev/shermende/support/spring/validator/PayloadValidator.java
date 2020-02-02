package dev.shermende.support.spring.validator;

import dev.shermende.support.spring.db.entity.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
        errors.reject("call-error");
    }

}
