package dev.shermende.support.spring.aop.profiling;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

/**
 *
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class ProfilingAspect {

    @SneakyThrows
    @Around("@annotation(dev.shermende.support.spring.aop.profiling.annotation.Profiling)")
    public Object profiling(ProceedingJoinPoint proceedingJoinPoint) {
        final long start = System.currentTimeMillis();
        final Object proceed = proceedingJoinPoint.proceed();
        final long delta = System.currentTimeMillis() - start;
        try {
            log.debug("[Profiling] [{}#{}] [Duration:{}] [Args:{}] [Result:{}]",
                proceedingJoinPoint.getTarget().getClass(),
                ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod(),
                delta,
                proceedingJoinPoint.getArgs(),
                proceed
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return proceed;
    }

}