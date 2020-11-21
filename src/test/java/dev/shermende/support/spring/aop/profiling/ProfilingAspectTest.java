package dev.shermende.support.spring.aop.profiling;

import dev.shermende.support.spring.aop.profiling.annotation.Profiling;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProfilingAspectTest.ProfilingAspectTestConfiguration.class})
public class ProfilingAspectTest {

    @SpyBean
    private ProfilingAspect aspect;

    @Autowired
    private ProfilingAspectTest.ProfilingAspectTestComponent component;

    @Test
    public void profiling() {
        component.convert(new Object());
        verify(aspect, times(1)).profiling(any());
    }

    @ComponentScan
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public static class ProfilingAspectTestConfiguration {
        @Bean
        public ProfilingAspect interceptAspect() {
            return new ProfilingAspect();
        }
    }

    @Slf4j
    @Component
    public static class ProfilingAspectTestComponent {
        @Profiling
        public Object convert(
            Object payload
        ) {
            return payload;
        }
    }
}