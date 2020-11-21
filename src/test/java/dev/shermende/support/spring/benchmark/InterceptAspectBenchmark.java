package dev.shermende.support.spring.benchmark;

import dev.shermende.support.spring.aop.intercept.InterceptAspect;
import dev.shermende.support.spring.aop.intercept.Interceptor;
import dev.shermende.support.spring.aop.intercept.annotation.Intercept;
import dev.shermende.support.spring.aop.intercept.annotation.InterceptArgument;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class InterceptAspectBenchmark {

    private ConfigurableApplicationContext context;

    @Setup(Level.Trial)
    public synchronized void benchmarkSetup() {
        context = SpringApplication.run(InterceptAspectBenchmarkConfiguration.class);
    }

    @Benchmark
    public void benchmark() {
        context.getBean(InterceptAspectBenchmarkComponent.class).convert(new Object());
    }

    @ComponentScan
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public static class InterceptAspectBenchmarkConfiguration {
        @Bean
        public InterceptAspect interceptAspect(BeanFactory factory) {
            return new InterceptAspect(factory);
        }
    }

    @Slf4j
    @Component
    public static class InterceptAspectBenchmarkComponent {
        @Intercept
        public Object convert(
            @InterceptArgument(InterceptAspectBenchmark.InterceptAspectBenchmarkInterceptor.class) Object payload
        ) {
            return payload;
        }
    }

    @Slf4j
    @Component
    public static class InterceptAspectBenchmarkInterceptor implements Interceptor {
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