package dev.shermende.support.spring.aop.logging;

import dev.shermende.support.spring.aop.logging.annotation.Logging;
import dev.shermende.support.spring.jmx.JmxControl;
import dev.shermende.support.spring.jmx.impl.ToggleJmxControlImpl;
import org.aspectj.lang.Aspects;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {LoggingAspectTest.LoggingAspectTestConfiguration.class})
public class LoggingAspectTest {

    @SpyBean
    private JmxControl jmxControl;

    @Autowired
    private LoggingAspectTestComponent component;

    @Test
    public void logging() {
        component.convert(new Object());
        then(jmxControl).should(times(1)).isEnabled();
    }

    @ComponentScan(basePackageClasses = {LoggingAspectTest.LoggingAspectTestConfiguration.class})
    public static class LoggingAspectTestConfiguration {

    }

    @Configuration
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    @ConditionalOnMissingBean(LoggingAspectTest.LoggingAspectTestConfigurationCTW.class)
    public static class LoggingAspectTestConfigurationLTW {
        @Bean
        public JmxControl jmxControl() {
            return new ToggleJmxControlImpl(true);
        }

        @Bean
        public LoggingAspect loggingAspect() {
            return new LoggingAspect();
        }
    }

    @Configuration
    @Profile("aspect-ctw")
    public static class LoggingAspectTestConfigurationCTW {
        @Bean
        public JmxControl jmxControl() {
            return new ToggleJmxControlImpl(true);
        }

        @Bean
        public LoggingAspect loggingAspect() {
            return Aspects.aspectOf(LoggingAspect.class);
        }
    }

    @Component
    public static class LoggingAspectTestComponent {
        @Logging
        public Object convert(
            Object payload
        ) {
            return payload;
        }
    }

}