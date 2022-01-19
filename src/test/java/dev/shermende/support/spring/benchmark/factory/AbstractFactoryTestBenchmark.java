package dev.shermende.support.spring.benchmark.factory;

import dev.shermende.support.spring.factory.AbstractFactory;
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
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Fork(1)
@Threads(10)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class AbstractFactoryTestBenchmark {

    private ConfigurableApplicationContext context;

    @Setup(Level.Trial)
    public synchronized void benchmarkSetup() {
        context = SpringApplication.run(AbstractFactoryTestBenchmarkConfiguration.class);
    }

    @Benchmark
    public void benchmark() {
        context.getBean(AbstractFactoryTestBenchmarkFactory.class).getInstance(true).convert(new Object());
    }

    @ComponentScan
    public static class AbstractFactoryTestBenchmarkConfiguration {

    }

    @Component
    public static class AbstractFactoryTestBenchmarkConverter implements Converter<Object, Object> {
        @Override
        public Object convert(Object o) {
            return o;
        }
    }

    @Component
    public static class AbstractFactoryTestBenchmarkFactory extends AbstractFactory<Boolean, Converter<Object, Object>> {
        public AbstractFactoryTestBenchmarkFactory(BeanFactory beanFactory) {
            super(beanFactory);
        }

        @Override
        protected void registration() {
            this.registry(true, AbstractFactoryTestBenchmark.AbstractFactoryTestBenchmarkConverter.class);
        }
    }

}