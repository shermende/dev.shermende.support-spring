package dev.shermende.support.spring.benchmark;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class JmhRunnerTest {

    @Test
    public void benchmarkRunner() throws RunnerException {
        Options opt = new OptionsBuilder()
            // set the class name regex for benchmarks to search for to the current class
            .include("^.*Benchmark.*$")
            .forks(1)
            .threads(10)
            .warmupIterations(3)
            .measurementIterations(3)
            .shouldDoGC(true)
            .shouldFailOnError(true)
            .jvmArgs("-server", "-Xmx1g")
            .build();

        new Runner(opt).run();
    }

}