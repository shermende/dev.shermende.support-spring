# Method argument interceptor for spring-boot 2.x.x

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=shermende_dev.shermende.support-spring&metric=alert_status)](https://sonarcloud.io/dashboard?id=shermende_dev.shermende.support-spring)
[![CircleCI](https://circleci.com/gh/shermende/dev.shermende.support-spring.svg?style=svg)](https://circleci.com/gh/shermende/dev.shermende.support-spring)

## Attention

* Method argument interceptor (require `spring-boot-starter-aop`)
* Additionally provides interfaces for handlers and factories

## Add to spring-boot 2.x.x

```
<dependency>
  <groupId>dev.shermende</groupId>
  <artifactId>support-spring</artifactId>
  <version>1.0.1</version>
</dependency>
```
[Other examples here](https://search.maven.org/artifact/dev.shermende/support-spring)
## Example of usage

#### step 1. enable interceptor
```java
@EnableInterceptor
@SpringBootApplication
public class PetApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetApplication.class, args);
    }

}
```
#### step 2. create interceptor class
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
[More details here](https://github.com/shermende/dev.shermende.pet.dms-backend/blob/develop/src/main/java/dev/shermende/pet/dms/service/unit/impl/UnitServiceImpl.java#L114)
## Notification

Interception works for external method calls like `@Transactional`, `@Cacheable`.

All used libraries are `provided`, This is made for compatibility with a specific version of spring-boot
 
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
