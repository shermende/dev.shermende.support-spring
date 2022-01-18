package dev.shermende.support.spring.benchmark.interceptresult;

import dev.shermende.support.spring.aop.intercept.InterceptResultAspect;
import dev.shermende.support.spring.aop.intercept.Interceptor;
import dev.shermende.support.spring.aop.intercept.annotation.InterceptResult;
import dev.shermende.support.spring.jmx.JmxControl;
import dev.shermende.support.spring.jmx.impl.ToggleJmxControlImpl;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Fork(1)
@Threads(10)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class InterceptResultAspectBenchmark {

    private ConfigurableApplicationContext context;

    @Setup(Level.Trial)
    public synchronized void benchmarkSetup() {
        context = SpringApplication.run(InterceptResultAspectBenchmarkConfiguration.class);
    }

    @Benchmark 
    public void benchmark() {
        context.getBean(InterceptResultAspectBenchmarkComponent.class).convert(new Object());
    }

    @Configuration
    @ComponentScan
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public static class InterceptResultAspectBenchmarkConfiguration {
        @Bean
        public JmxControl interceptResultAspectJmxControl() {
            return new ToggleJmxControlImpl(true);
        }

        @Bean
        public InterceptResultAspect interceptResultAspect() {
            return new InterceptResultAspect();
        }
    }

    @Component
    public static class InterceptResultAspectBenchmarkComponent {
        @InterceptResult(InterceptResultAspectBenchmark.InterceptResultAspectBenchmarkInterceptor.class)
        public Object convert(
            Object payload
        ) {
            return payload;
        }
    }

    @Component
    public static class InterceptResultAspectBenchmarkInterceptor implements Interceptor {
        private static final Logger LOGGER = LoggerFactory.getLogger(InterceptResultAspectBenchmark.InterceptResultAspectBenchmarkInterceptor.class);

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
            LOGGER.debug("interceptor working... {}", payload);
        }
    }

}