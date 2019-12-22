package dev.shermende.spring.intercept.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(InterceptorConfiguration.class)
public @interface EnableInterceptor {
}
