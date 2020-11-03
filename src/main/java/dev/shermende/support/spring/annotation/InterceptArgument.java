package dev.shermende.support.spring.annotation;


import dev.shermende.support.spring.utils.Interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptArgument {
    Class<? extends Interceptor> value();
}
