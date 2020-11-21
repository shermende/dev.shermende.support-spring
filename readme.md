# Support library for spring-boot 2.1.x or higher

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=shermende_dev.shermende.support-spring&metric=alert_status)](https://sonarcloud.io/dashboard?id=shermende_dev.shermende.support-spring)

![Maven pipeline](https://github.com/shermende/dev.shermende.support-spring/workflows/Maven%20pipeline/badge.svg)

### Add to maven project

```
<dependency>
  <groupId>dev.shermende</groupId>
  <artifactId>support-spring</artifactId>
  <version>1.1.0</version>
</dependency>
```

[Other examples here](https://mvnrepository.com/artifact/dev.shermende/support-spring)

## Method argument interception

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
public class PetInterceptor implements Interceptor {
    @Override
    public boolean supports(
        Class<?> aClass
    ) {
        return PetPayload.class.isAssignableFrom(aClass);
    }

    @Override
    public void intercept(
        Object o
    ) {
        // processing
    }
}
```

#### step 3. make interception

```java

@Service
public class PetService {
    @Intercept
    public void handle(
        @InterceptArgument(PetInterceptor.class) PetPayload payload
    ) {
        // processing
    }
}
```

## JMH Benchmarking results

```
Benchmark                                 Mode  Cnt   Score   Error  Units
AbstractFactoryTestBenchmark.benchmark    avgt    3   0.686 ± 0.155  us/op
AbstractStepProcessorBenchmark.benchmark  avgt    3   0.944 ± 0.196  us/op
DirectInvocationBenchmark.benchmark       avgt    3   0.357 ± 0.252  us/op
EmptyAspectBenchmark.benchmark            avgt    3   0.347 ± 0.043  us/op
InterceptAspectBenchmark.benchmark        avgt    3  37.037 ± 5.981  us/op
InterceptResultAspectBenchmark.benchmark  avgt    3   3.274 ± 1.000  us/op
LoggingAspectBenchmark.benchmark          avgt    3   2.238 ± 0.118  us/op
ProfilingAspectBenchmark.benchmark        avgt    3   2.555 ± 0.149  us/op
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
