package dev.shermende.support.spring.aop;

import dev.shermende.support.spring.component.annotation.InterceptResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AopInterceptResult {

    private final BeanFactory beanFactory;

    @AfterReturning(pointcut = "@annotation(dev.shermende.support.spring.component.annotation.InterceptResult)", returning = "result")
    public void interceptResult(JoinPoint joinPoint, Object result) {
        beanFactory.getBean(getAnnotation(joinPoint).value()).doIntercept(result);
    }

    private InterceptResult getAnnotation(
        JoinPoint joinPoint
    ) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(InterceptResult.class);
    }

}
