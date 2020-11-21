package dev.shermende.support.spring.aop.intercept.annotation;


import dev.shermende.support.spring.aop.intercept.Interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method argument interceptor
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptArgument {
    Class<? extends Interceptor> value();
}
