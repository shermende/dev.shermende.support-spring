package dev.shermende.spring.support.component.annotation;


import dev.shermende.spring.support.component.Interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptArgument {
    Class<? extends Interceptor> value();
}
