package dev.shermende.support.spring.aop.intercept;

import dev.shermende.support.spring.aop.intercept.annotation.InterceptResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class InterceptResultAspect implements InitializingBean {

    private final BeanFactory beanFactory;

    @AfterReturning(pointcut = "@annotation(dev.shermende.support.spring.aop.intercept.annotation.InterceptResult)", returning = "result")
    public void interceptResult(
        JoinPoint joinPoint,
        Object result
    ) {
        beanFactory.getBean(getAnnotation(joinPoint).value()).doIntercept(result);
    }

    private InterceptResult getAnnotation(
        JoinPoint joinPoint
    ) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(InterceptResult.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.warn("Attention!!! @dev.shermende.support.spring.aop.intercept.annotation.InterceptResult annotation enabled");
    }
}
