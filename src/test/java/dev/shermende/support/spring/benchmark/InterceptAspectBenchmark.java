package dev.shermende.support.spring.benchmark;

import dev.shermende.support.spring.aop.intercept.InterceptAspect;
import dev.shermende.support.spring.aop.intercept.Interceptor;
import dev.shermende.support.spring.aop.intercept.annotation.Intercept;
import dev.shermende.support.spring.aop.intercept.annotation.InterceptArgument;
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
import org.springframework.beans.factory.InitializingBean;
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
public class InterceptAspectBenchmark {
    private static final Logger log = LoggerFactory.getLogger(InterceptAspectBenchmark.class);

    private ConfigurableApplicationContext context;

    @Setup(Level.Trial)
    public synchronized void benchmarkSetup() {
        final SpringApplication application = new SpringApplication(InterceptAspectBenchmarkConfiguration.class);
        context = application.run();
    }

    @Benchmark
    public void benchmark() {
        context.getBean(InterceptAspectBenchmarkComponent.class).convert(new Object());
    }

    @ComponentScan
    @Configuration
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public static class InterceptAspectBenchmarkConfiguration implements InitializingBean {
        private static final Logger LOGGER = LoggerFactory.getLogger(InterceptAspectBenchmark.InterceptAspectBenchmarkConfiguration.class);

        @Bean
        public JmxControl jmxControl() {
            return new ToggleJmxControlImpl(true);
        }

        @Bean
        public InterceptAspect interceptAspect() {
            return new InterceptAspect();
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            LOGGER.info("Load time weaving mode");
        }
    }

    @Component
    public static class InterceptAspectBenchmarkComponent {
        @Intercept
        public Object convert(
            @InterceptArgument(InterceptAspectBenchmark.InterceptAspectBenchmarkInterceptor.class) Object payload
        ) {
            return payload;
        }
    }

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