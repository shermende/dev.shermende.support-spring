package dev.shermende.support.spring.support.annotation;


import dev.shermende.support.spring.support.Interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptResult {
    Class<? extends Interceptor> value();
}
