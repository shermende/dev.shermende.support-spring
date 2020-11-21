package dev.shermende.support.spring.aop.intercept.annotation;


import dev.shermende.support.spring.aop.intercept.Interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Method result interceptor
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptResult {
    Class<? extends Interceptor> value();
}
