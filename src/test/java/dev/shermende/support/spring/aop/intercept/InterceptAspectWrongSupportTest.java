package dev.shermende.support.spring.aop.intercept;

import dev.shermende.support.spring.aop.intercept.annotation.Intercept;
import dev.shermende.support.spring.aop.intercept.annotation.InterceptArgument;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {InterceptAspectWrongSupportTest.SupportWrongInterceptorTestConfiguration.class})
public class InterceptAspectWrongSupportTest {

    @Autowired
    private InterceptorWrongSupportHandler interceptorWrongSupportHandler;

    /**
     * wrong support
     */
    @Test(expected = IllegalArgumentException.class)
    public void interceptWrongSupportTest() {
        interceptorWrongSupportHandler.convert(new Object());
    }

    @ComponentScan
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public static class SupportWrongInterceptorTestConfiguration {
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
    public static class InterceptorWrongSupportHandler {
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