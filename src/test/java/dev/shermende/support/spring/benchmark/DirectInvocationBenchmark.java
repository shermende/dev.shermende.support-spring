package dev.shermende.support.spring.benchmark;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class DirectInvocationBenchmark {

    private ConfigurableApplicationContext context;

    @Setup(Level.Trial)
    public synchronized void benchmarkSetup() {
        context = SpringApplication.run(DirectInvocationBenchmarkConfiguration.class);
    }

    @Benchmark
    public void benchmark() {
        context.getBean(DirectInvocationBenchmarkComponent.class).action();
    }

    @ComponentScan
    public static class DirectInvocationBenchmarkConfiguration {

    }

    @Component
    public static class DirectInvocationBenchmarkComponent {
        void action() {
            IntStream.range(0, 10000).forEach(i -> {
                double res = i / 10000.0;
            });
        }
    }

}