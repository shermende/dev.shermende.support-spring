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

## Factory pattern

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

## JMH Benchmarking results

```
Benchmark                                   Mode  Cnt   Score    Error  Units
AbstractFactoryTestBenchmark.benchmark      avgt    3   0.786 ±  0.441  us/op
AbstractStepProcessorBenchmark.benchmark    avgt    3   1.117 ±  0.308  us/op
DirectInvocationBenchmark.benchmark         avgt    3   0.333 ±  0.117  us/op
EmptyAspectBenchmark.benchmark              avgt    3   0.330 ±  0.104  us/op
InterceptAspectBenchmark.benchmark          avgt    3  42.310 ± 14.357  us/op
InterceptResultAspectBenchmark.benchmark    avgt    3   3.471 ±  0.551  us/op
LoggingAspectBenchmark.benchmark            avgt    3   6.041 ±  1.252  us/op
LoggingAspectDisabledBenchmark.benchmark    avgt    3   2.399 ±  0.482  us/op
ProfilingAspectBenchmark.benchmark          avgt    3   4.152 ±  0.553  us/op
ProfilingAspectDisabledBenchmark.benchmark  avgt    3   2.360 ±  0.716  us/op
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
