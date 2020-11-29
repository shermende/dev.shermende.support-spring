package dev.shermende.support.spring.aop.profiling;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 */
@Slf4j
@Aspect
@ManagedResource
public class ProfilingAspect implements InitializingBean {

    private final AtomicBoolean enabled;

    public ProfilingAspect(boolean enabled) {
        this.enabled = new AtomicBoolean(enabled);
    }

    @SneakyThrows
    @Around("@annotation(dev.shermende.support.spring.aop.profiling.annotation.Profiling)")
    public Object profiling(ProceedingJoinPoint proceedingJoinPoint) {
        // do nothing if disabled
        if (!enabled.get()) return proceedingJoinPoint.proceed();
        // logging if enabled
        final long start = System.currentTimeMillis();
        final Object proceed = proceedingJoinPoint.proceed();
        final long delta = System.currentTimeMillis() - start;
        try {
            final Class<?> aClass = proceedingJoinPoint.getTarget().getClass();
            final MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            final Method method = signature.getMethod();
            log.debug("[Profiling] [{}#{}] [Duration:{}]",
                aClass.getSimpleName(),
                method.getName(),
                delta
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return proceed;
    }

    @ManagedOperation
    public boolean isEnabled() {
        return enabled.get();
    }

    @ManagedOperation
    public boolean toggle() {
        enabled.set(!enabled.get());
        return enabled.get();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.warn("Attention!!! @dev.shermende.support.spring.aop.profiling.annotation.Profiling annotation enabled");
    }

}