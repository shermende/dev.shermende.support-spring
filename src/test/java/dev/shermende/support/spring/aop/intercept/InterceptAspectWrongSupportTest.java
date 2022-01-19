package dev.shermende.support.spring.aop.intercept;

import dev.shermende.support.spring.aop.intercept.annotation.Intercept;
import dev.shermende.support.spring.aop.intercept.annotation.InterceptArgument;
import dev.shermende.support.spring.jmx.JmxControl;
import dev.shermende.support.spring.jmx.impl.ToggleJmxControlImpl;
import org.aspectj.lang.Aspects;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {InterceptAspectWrongSupportTest.InterceptAspectWrongSupportTestConfiguration.class})
public class InterceptAspectWrongSupportTest {

    @Autowired
    private InterceptAspectWrongSupportTestComponent component;

    /**
     * wrong support
     */
    @Test(expected = IllegalArgumentException.class)
    public void interceptWrongSupportTest() {
        component.convert(new Object());
    }

    @ComponentScan(basePackageClasses = {InterceptAspectWrongSupportTest.InterceptAspectWrongSupportTestConfiguration.class})
    public static class InterceptAspectWrongSupportTestConfiguration {

    }

    @ComponentScan
    @Profile("!aspect-ctw")
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public static class InterceptAspectWrongSupportTestConfigurationLTW {
        @Bean
        public JmxControl jmxControl() {
            return new ToggleJmxControlImpl(true);
        }

        @Bean
        public InterceptAspect interceptAspect() {
            return new InterceptAspect();
        }

        @Bean
        public InterceptResultAspect interceptResultAspect() {
            return new InterceptResultAspect();
        }
    }

    @Configuration
    @Profile("aspect-ctw")
    public static class InterceptAspectWrongSupportTestConfigurationCTW {
        @Bean
        public JmxControl jmxControl() {
            return new ToggleJmxControlImpl(true);
        }

        @Bean
        public InterceptAspect interceptAspect() {
            return Aspects.aspectOf(InterceptAspect.class);
        }

        @Bean
        public InterceptResultAspect interceptResultAspect() {
            return Aspects.aspectOf(InterceptResultAspect.class);
        }
    }

    @Component
    public static class InterceptAspectWrongSupportTestComponent {
        @Intercept
        public Object convert(
            @InterceptArgument(InterceptAspectWrongSupportTestInterceptor.class) Object payload
        ) {
            return payload;
        }
    }

    @Component
    public static class InterceptAspectWrongSupportTestInterceptor implements Interceptor {
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
        }
    }
}