package dev.shermende.support.spring.validate;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidatorHolder {

    private static Validator instance = null;

    public static Validator getInstance() {
        if (instance == null)
            instance = Validation.buildDefaultValidatorFactory().getValidator();
        return instance;
    }

    private ValidatorHolder() {
    }

}
