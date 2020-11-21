package dev.shermende.support.spring.aop.intercept;

import java.util.Objects;

/**
 * Interceptor behavior
 */
public interface Interceptor {

    boolean supports(
        Class<?> aClass
    );

    default void doIntercept(
        Object argument
    ) {
        if (Objects.nonNull(argument) && !supports(argument.getClass()))
            throw new IllegalArgumentException(
                String.format("Invalid target for Interceptor %s", argument.getClass().getName()));

        intercept(argument);
    }

    void intercept(
        Object argument
    );

}
