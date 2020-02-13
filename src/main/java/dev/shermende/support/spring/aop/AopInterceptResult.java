package dev.shermende.support.spring.aop;

import dev.shermende.support.spring.component.annotation.InterceptResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopInterceptResult {

    private final BeanFactory beanFactory;

    public AopInterceptResult(
        BeanFactory beanFactory
    ) {
        this.beanFactory = beanFactory;
    }

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
