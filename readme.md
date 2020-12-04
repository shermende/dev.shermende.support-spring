# Support library for spring-boot 2.1.x or higher

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=shermende_dev.shermende.support-spring&metric=alert_status)](https://sonarcloud.io/dashboard?id=shermende_dev.shermende.support-spring)

![Maven pipeline](https://github.com/shermende/dev.shermende.support-spring/workflows/Maven%20pipeline/badge.svg)

### Add to maven project

```
<dependency>
  <groupId>dev.shermende</groupId>
  <artifactId>support-spring</artifactId>
  <version>1.1.2</version>
</dependency>
```

[Other examples here](https://mvnrepository.com/artifact/dev.shermende/support-spring)

## Argument interception

#### step 1. configuration

```java

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApplicationConfiguration {
    @Bean
    public InterceptAspect interceptAspect() {
        return new InterceptAspect();
    }
}
```

#### step 2. create interceptor

```java

@Component
public class ExampleInterceptor implements Interceptor {
    @Override
    public boolean supports(
        Class<?> aClass
    ) {
        return Object.class.isAssignableFrom(aClass);
    }

    @Override
    public void intercept(
        Object o
    ) {
        // processing
    }
}
```

#### step 3. example of usage

```java

@Service
public class ExampleService {
    @Intercept
    public void handle(
        @InterceptArgument(ExampleInterceptor.class) Object payload
    ) {
        // processing
    }
}
```

## Factory

#### step 1. create factory

```java

@Component
public class ExampleFactory extends AbstractFactory<String, Converter<Object, Object>> {
    public Factory(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected void registration() {
        this.registry("one", ExampleConverterOne.class);
        this.registry("two", ExampleConverterTwo.class);
    }
}
```

#### step 2. create factory items

```java

@Component
public class ExampleConverterOne implements Converter<Object, Object> {
    @Override
    public Object convert(Object o) {
        return o;
    }
}

@Component
public class ExampleConverterTwo implements Converter<Object, Object> {
    @Override
    public Object convert(Object o) {
        return o;
    }
}
```

#### step 3. example of usage

```java

@Service
@RequiredArgsConstructor
public class ExampleService {
    private final ExampleFactory factory;

    public void handle(
        Object o
    ) {
        factory.getInstance("one").convert(o);
    }
}
```

## Step processor as chain of responsibility

#### step 1. create step processor

```java

@Component
public class ExampleStepProcessor extends AbstractStepProcessor<Object, Object> {
    protected ExampleStepProcessor(BeanFactory factory) {
        super(factory);
    }

    @Override
    protected Collection<Class<? extends Step<Object, Object>>> steps() {
        return Arrays.asList(ExampleFirstStep.class, ExampleSecondStep.class);
    }
}
```

#### step 2. create steps

```java

@Component
public class ExampleFirstStep implements Step<Object, Object> {
    @Override
    public Object execute(Object o) {
        return o;
    }
}

@Component
public class ExampleSecondStep implements Step<Object, Object> {
    @Override
    public Object execute(Object o) {
        return o;
    }
}
```

#### step 3. example of usage

```java

@Service
@RequiredArgsConstructor
public class ExampleService {
    private final ExampleStepProcessor processor;

    public void handle(
        Object o
    ) {
        processor.execute(o);
    }
}
```

## Profiling

#### step 1. configuration

```java

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ProfilingAspectTestConfiguration {
    /**
     * jmx control. use mbeans for runtime on/off
     */
    @Bean
    public JmxControl jmxControl() {
        return new ToggleJmxControlImpl(true);
    }

    /**
     * enabling profiling aspect
     */
    @Bean
    public ProfilingAspect interceptAspect(JmxControl jmxControl) {
        return new ProfilingAspect(jmxControl);
    }
}
```

#### step 2. example of usage

```java

@Component
public class ProfilingAspectTestComponent {
    @Profiling
    public Object convert(
        Object payload
    ) {
        return payload;
    }
}
```

#### step 3. example of log

```
[main] DEBUG dev.shermende.support.spring.aop.profiling.ProfilingAspect - [Profiling] [ProfilingAspectTestComponent#convert] [Duration:11]
```

## Logging

#### step 1. configuration

```java

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoggingAspectTestConfiguration {
    /**
     * jmx control. use mbeans for runtime on/off
     */
    @Bean
    public JmxControl jmxControl() {
        return new ToggleJmxControlImpl(true);
    }

    /**
     * enabling profiling aspect
     */
    @Bean
    public LoggingAspect interceptAspect(JmxControl jmxControl) {
        return new LoggingAspect(jmxControl);
    }
}
```

#### step 2. example of usage

```java

@Component
public class LoggingAspectTestComponent {
    @Logging
    public Object convert(
        Object payload
    ) {
        return payload;
    }
}
```

#### step 3. example of log

```
[main] DEBUG dev.shermende.support.spring.aop.logging.LoggingAspect - [Logging before] [LoggingAspectTestComponent#convert] [Args:[java.lang.Object@bcec031]]
[main] DEBUG dev.shermende.support.spring.aop.logging.LoggingAspect - [Logging after] [LoggingAspectTestComponent#convert] [Args:[java.lang.Object@bcec031]] [Result:java.lang.Object@bcec031]
```

## JMH Benchmarking results on GithubActions pipeline

```
# JMH version: 1.26
# VM version: JDK 1.8.0_275, OpenJDK 64-Bit Server VM, 25.275-b01
# VM invoker: /opt/hostedtoolcache/jdk/8.0.275/x64/jre/bin/java
# VM options: <none>
# Warmup: 3 iterations, 10 s each
# Measurement: 3 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 10 threads, will synchronize iterations
# Benchmark mode: Average time, time/op
Benchmark                                   Mode  Cnt   Score   Error  Units
AbstractFactoryTestBenchmark.benchmark      avgt    3   0.797 ± 0.183  us/op
AbstractStepProcessorBenchmark.benchmark    avgt    3   1.098 ± 0.266  us/op
DirectInvocationBenchmark.benchmark         avgt    3   0.350 ± 0.105  us/op
EmptyAspectBenchmark.benchmark              avgt    3   0.398 ± 0.045  us/op
InterceptAspectBenchmark.benchmark          avgt    3  40.699 ± 5.868  us/op
InterceptResultAspectBenchmark.benchmark    avgt    3   3.942 ± 0.641  us/op
LoggingAspectBenchmark.benchmark            avgt    3   5.864 ± 1.611  us/op
LoggingAspectDisabledBenchmark.benchmark    avgt    3   2.466 ± 0.437  us/op
ProfilingAspectBenchmark.benchmark          avgt    3   4.983 ± 0.407  us/op
ProfilingAspectDisabledBenchmark.benchmark  avgt    3   2.430 ± 0.667  us/op
```

## Troubleshooting

##### Problem

```$xslt
java.lang.ClassNotFoundException: org.aspectj.lang.annotation.Pointcut
```

##### Cure. Add `spring-boot-starter-aop` to project

```$xslt
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
