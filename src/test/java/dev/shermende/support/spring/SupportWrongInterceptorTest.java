package dev.shermende.support.spring;

import dev.shermende.support.spring.annotation.Intercept;
import dev.shermende.support.spring.annotation.InterceptArgument;
import dev.shermende.support.spring.utils.Interceptor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InterceptorAutoConfiguration.class)
public class SupportWrongInterceptorTest {

    @Autowired
    private InterceptorWrongSupportHandler interceptorWrongSupportHandler;

    /**
     * interceptor wrong support
     */
    @Test(expected = IllegalArgumentException.class)
    public void interceptorWrongSupportException() {
        interceptorWrongSupportHandler.convert(new Object());
    }

    @Slf4j
    @Component
    public static class InterceptorWrongSupportHandler implements Converter<Object, Object> {

        @Override
        @Intercept
        public Object convert(
            @InterceptArgument(WrongSupportInterceptor.class) Object payload
        ) {
            log.debug("unreachable code. exception in interceptor.");
            return payload;
        }

    }

    @Slf4j
    @Component
    public static class WrongSupportInterceptor implements Interceptor {

        @Override
        public boolean supports(
            Class<?> aClass
        ) {
            return Exception.class.isAssignableFrom(aClass);
        }

        @Override
        public void intercept(
            Object o
        ) {
            log.debug("unreachable code. exception in supports(...) method.");
        }

    }

}