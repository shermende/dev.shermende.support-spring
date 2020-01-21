# Support library for spring-boot 2.x.x

Support library for spring boot, frequently used classes for creating applications.

## Include

* Interceptor for method arguments (require `spring-boot-starter-aop`)
* ValidationUtils
* Factory interfaces
* Handler interfaces

## Add to spring-boot 2.x.x

```
<dependency>
  <groupId>dev.shermende</groupId>
  <artifactId>support-spring</artifactId>
  <version>1.0.0</version>
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

    @Override
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
