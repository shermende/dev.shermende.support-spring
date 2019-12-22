package dev.shermende.spring.intercept.component.annotation;


import dev.shermende.spring.intercept.component.Interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptResult {
    Class<? extends Interceptor> value();
}
