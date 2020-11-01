package dev.shermende.support.spring;

import dev.shermende.support.spring.support.Interceptor;
import dev.shermende.support.spring.support.annotation.Intercept;
import dev.shermende.support.spring.support.annotation.InterceptArgument;
import dev.shermende.support.spring.support.annotation.InterceptResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterceptorAutoConfiguration.class)
public class PassInterceptionIT {
    @SpyBean
    private PassInterceptionIT.InterceptArgumentInterceptor interceptor;

    @Autowired
    private InterceptorHandler handler;

    @Test
    public void interceptorValidationException() {
        final Object object = new Object();
        handler.convert(object);
        verify(interceptor, times(2)).doIntercept(object);
        verify(interceptor, times(2)).supports(object.getClass());
        verify(interceptor, times(2)).intercept(object);
    }

    @Slf4j
    @Component
    public static class InterceptorHandler implements Converter<Object, Object> {

        @Override
        @Intercept
        @InterceptResult(InterceptArgumentInterceptor.class)
        public Object convert(
            @InterceptArgument(InterceptArgumentInterceptor.class) Object payload
        ) {
            return payload;
        }

    }

    @Slf4j
    @Component
    public static class InterceptArgumentInterceptor implements Interceptor {

        @Override
        public boolean supports(
            Class<?> aClass
        ) {
            return Object.class.isAssignableFrom(aClass);
        }

        @Override
        public void intercept(
            Object payload
        ) {
            log.debug("interceptor working... {}", payload);
        }

    }

}