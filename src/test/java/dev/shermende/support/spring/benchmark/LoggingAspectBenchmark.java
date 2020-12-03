package dev.shermende.support.spring.benchmark;

import dev.shermende.support.spring.aop.logging.LoggingAspect;
import dev.shermende.support.spring.aop.logging.annotation.Logging;
import dev.shermende.support.spring.jmx.JmxControl;
import dev.shermende.support.spring.jmx.impl.ToggleJmxControlImpl;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Fork(1)
@Threads(10)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class LoggingAspectBenchmark {

    private ConfigurableApplicationContext context;

    @Setup(Level.Trial)
    public synchronized void benchmarkSetup() {
        context = SpringApplication.run(LoggingAspectBenchmarkConfiguration.class);
    }

    @Benchmark
    public void benchmark() {
        context.getBean(LoggingAspectBenchmarkComponent.class).action();
    }

    @ComponentScan
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    public static class LoggingAspectBenchmarkConfiguration {
        @Bean
        public JmxControl jmxControl() {
            return new ToggleJmxControlImpl(true);
        }
        @Bean
        public LoggingAspect loggingAspect(JmxControl jmxControl) {
            return new LoggingAspect(jmxControl);
        }
    }

    @Component
    public static class LoggingAspectBenchmarkComponent {
        @Logging
        void action() {
        }
    }

}