package dev.shermende.support.spring.aop.intercept;

import dev.shermende.support.spring.aop.intercept.annotation.Intercept;
import dev.shermende.support.spring.aop.intercept.annotation.InterceptArgument;
import dev.shermende.support.spring.aop.intercept.annotation.InterceptResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {InterceptAspectTest.InterceptAspectTestConfiguration.class})
public class InterceptAspectTest {

    @SpyBean
    private InterceptAspectTest.InterceptArgumentInterceptor interceptor;

    @Autowired
    private InterceptorHandler handler;

    @Test
    public void intercept() {
        final Object object = new Object();
        handler.convert(object);
        verify(interceptor, times(2)).doIntercept(object);
        verify(interceptor, times(2)).supports(object.getClass());
        verify(interceptor, times(2)).intercept(object);
    }

    @ComponentScan
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public static class InterceptAspectTestConfiguration {
        @Bean
        public InterceptAspect interceptAspect(BeanFactory factory) {
            return new InterceptAspect(factory);
        }

        @Bean
        public InterceptResultAspect interceptResultAspect(BeanFactory factory) {
            return new InterceptResultAspect(factory);
        }
    }

    @Slf4j
    @Component
    public static class InterceptorHandler {
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