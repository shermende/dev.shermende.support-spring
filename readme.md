# Argument interceptor for spring-boot 2.x.x

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=shermende_dev.shermende.support-spring&metric=alert_status)](https://sonarcloud.io/dashboard?id=shermende_dev.shermende.support-spring)
[![CircleCI](https://circleci.com/gh/shermende/dev.shermende.support-spring.svg?style=svg)](https://circleci.com/gh/shermende/dev.shermende.support-spring)

Attention, for new project required `spring-boot-starter-aop` dependency
### Add to maven project

```
<dependency>
  <groupId>dev.shermende</groupId>
  <artifactId>support-spring</artifactId>
  <version>1.0.3</version>
</dependency>
```
### Add to gradle project

```
groovy: implementation 'dev.shermende:support-spring:1.0.3'
kotlin: implementation("dev.shermende:support-spring:1.0.3")
```
[Other examples here](https://search.maven.org/artifact/dev.shermende/support-spring)
## Example of usage

#### step 1. create interceptor class
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
#### step 2. make interception
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
