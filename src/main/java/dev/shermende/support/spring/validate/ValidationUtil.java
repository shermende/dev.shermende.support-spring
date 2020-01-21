package dev.shermende.support.spring.validate;

import org.springframework.util.Assert;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationUtil {
    private static final String OBJECT_IS_NULL = "The object to be validated cannot be null";

    private ValidationUtil() {
    }

    public static <T> List<ObjectError> validate(
            T t
    ) {
        Assert.notNull(t, OBJECT_IS_NULL);

        final Set<ConstraintViolation<T>> violations = ValidatorHolder.getInstance().validate(t);

        if (!violations.isEmpty())
            return violations.stream()
                    .map(violation -> new ObjectError(
                            violation.getPropertyPath().toString(), // field
                            new String[]{violation.getMessageTemplate()}, // code
                            violation.getExecutableParameters(), // args
                            violation.getMessage() // default message
                    ))
                    .collect(Collectors.toList());

        return new ArrayList<>();
    }

    public static <T> List<ObjectError> validate(
            Validator validator,
            T t
    ) {
        Assert.notNull(t, OBJECT_IS_NULL);
        Assert.notNull(validator, OBJECT_IS_NULL);

        final DataBinder binder = new DataBinder(t);
        binder.addValidators(validator);
        binder.validate();

        if (binder.getBindingResult().hasErrors())
            return binder.getBindingResult().getAllErrors();

        return new ArrayList<>();
    }

}
