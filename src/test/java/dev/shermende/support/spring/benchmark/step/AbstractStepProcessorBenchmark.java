package dev.shermende.support.spring.benchmark.step;

import dev.shermende.support.spring.processor.AbstractStepProcessor;
import dev.shermende.support.spring.processor.Step;
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
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Fork(1)
@Threads(10)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class AbstractStepProcessorBenchmark {

    private ConfigurableApplicationContext context;

    @Setup(Level.Trial)
    public synchronized void benchmarkSetup() {
        context = SpringApplication.run(AbstractStepProcessorBenchmarkConfiguration.class);
    }

    @Benchmark
    public void benchmark() {
        context.getBean(AbstractStepProcessorBenchmarkStepProcessor.class).execute(new Object());
    }

    @ComponentScan
    public static class AbstractStepProcessorBenchmarkConfiguration {

    }

    @Component
    public static class AbstractStepProcessorBenchmarkStepProcessor extends AbstractStepProcessor<Object, Object> {
        protected AbstractStepProcessorBenchmarkStepProcessor(BeanFactory factory) {
            super(factory);
        }

        @Override
        protected Collection<Class<? extends Step<Object, Object>>> steps() {
            return Arrays.asList(AbstractStepProcessorBenchmarkStepOne.class, AbstractStepProcessorBenchmarkStepTwo.class);
        }
    }

    @Component
    public static class AbstractStepProcessorBenchmarkStepOne implements Step<Object, Object> {
        @Override
        public Object execute(Object o) {
            return o;
        }
    }

    @Component
    public static class AbstractStepProcessorBenchmarkStepTwo implements Step<Object, Object> {
        @Override
        public Object execute(Object o) {
            return o;
        }
    }

}