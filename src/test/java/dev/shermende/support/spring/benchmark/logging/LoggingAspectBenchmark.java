package dev.shermende.support.spring.benchmark.logging;

import dev.shermende.support.spring.aop.logging.LoggingAspect;
import dev.shermende.support.spring.aop.logging.annotation.Logging;
import dev.shermende.support.spring.jmx.JmxControl;
import dev.shermende.support.spring.jmx.impl.ToggleJmxControlImpl;
import org.aspectj.lang.Aspects;
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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Fork(1)
@Threads(10)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class LoggingAspectBenchmark {

    private ConfigurableApplicationContext context;

    @Setup(Level.Trial)
    public synchronized void benchmarkSetup() {
        final SpringApplication application = new SpringApplication(LoggingAspectBenchmark.LoggingAspectBenchmarkConfiguration.class);
        Optional.ofNullable(System.getenv("SPRING_PROFILE")).ifPresent(application::setAdditionalProfiles);
        context = application.run();
    }

    @Benchmark
    public void benchmark() {
        context.getBean(LoggingAspectBenchmarkComponent.class).action();
    }

    @ComponentScan
    public static class LoggingAspectBenchmarkConfiguration {
    }

    @Configuration
    @Profile("!aspect-ctw")
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public static class LoggingAspectBenchmarkConfigurationLTW {
        @Bean
        public JmxControl loggingAspectJmxControl() {
            return new ToggleJmxControlImpl(true);
        }

        @Bean
        public LoggingAspect loggingAspect() {
            return new LoggingAspect();
        }
    }

    @Configuration
    @Profile("aspect-ctw")
    public static class LoggingAspectBenchmarkConfigurationCTW {
        @Bean
        public JmxControl loggingAspectJmxControl() {
            return new ToggleJmxControlImpl(true);
        }

        @Bean
        public LoggingAspect loggingAspect() {
            return Aspects.aspectOf(LoggingAspect.class);
        }
    }

    @Component
    public static class LoggingAspectBenchmarkComponent {
        private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspectBenchmark.LoggingAspectBenchmarkComponent.class);

        @Logging
        public void action() {
            LOGGER.debug("LoggingAspectBenchmarkComponent");
        }
    }

}