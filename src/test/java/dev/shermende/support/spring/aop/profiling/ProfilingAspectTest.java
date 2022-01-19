package dev.shermende.support.spring.aop.profiling;

import dev.shermende.support.spring.aop.profiling.annotation.Profiling;
import dev.shermende.support.spring.jmx.JmxControl;
import dev.shermende.support.spring.jmx.impl.ToggleJmxControlImpl;
import org.aspectj.lang.Aspects;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes = {ProfilingAspectTest.ProfilingAspectTestConfiguration.class})
public class ProfilingAspectTest {

    @SpyBean
    private JmxControl jmxControl;

    @Autowired
    private ProfilingAspectTest.ProfilingAspectTestComponent component;

    @Test
    public void profiling() throws Throwable {
        component.convert(new Object());
        then(jmxControl).should(times(1)).isEnabled();
    }

    @ComponentScan(basePackageClasses = {ProfilingAspectTest.ProfilingAspectTestConfiguration.class})
    public static class ProfilingAspectTestConfiguration {
    }

    @Configuration
    @Profile("!aspect-ctw")
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public static class ProfilingAspectTestConfigurationLTW {
        @Bean
        public JmxControl profilingAspectJmxControl() {
            return new ToggleJmxControlImpl(true);
        }

        @Bean
        public ProfilingAspect profilingAspect() {
            return new ProfilingAspect();
        }
    }

    @Configuration
    @Profile("aspect-ctw")
    public static class ProfilingAspectTestConfigurationCTW {
        @Bean
        public JmxControl profilingAspectJmxControl() {
            return new ToggleJmxControlImpl(true);
        }

        @Bean
        public ProfilingAspect profilingAspect() {
            return Aspects.aspectOf(ProfilingAspect.class);
        }
    }

    @Component
    public static class ProfilingAspectTestComponent {
        private static final Logger LOGGER = LoggerFactory.getLogger(ProfilingAspectTestComponent.class);

        @Profiling
        public Object convert(
            Object payload
        ) {
            return payload;
        }
    }
}