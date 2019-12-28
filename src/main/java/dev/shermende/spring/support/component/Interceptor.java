package dev.shermende.spring.support.component;

public interface Interceptor {

    boolean supports(
            Class<?> aClass
    );

    default void doIntercept(
            Object o
    ) {
        if (!supports(o.getClass()))
            throw new IllegalArgumentException();

        intercept(o);
    }

    void intercept(
            Object o
    );

}
