package dev.shermende.support.spring.util;

import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

public class ValidationUtil {
    private static final String OBJECT_IS_NULL = "The object to be validated cannot be null";

    private ValidationUtil() {
    }

    public static <T> BindingResult validate(
        Validator validator,
        T t
    ) {
        Assert.notNull(t, OBJECT_IS_NULL);
        Assert.notNull(validator, OBJECT_IS_NULL);

        final DataBinder binder = new DataBinder(t);
        binder.addValidators(validator);
        binder.validate();

        if (binder.getBindingResult().hasErrors())
            return binder.getBindingResult();

        return binder.getBindingResult();
    }

}
