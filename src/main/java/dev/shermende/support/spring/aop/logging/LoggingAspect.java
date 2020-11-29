package dev.shermende.support.spring.aop.logging;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 */
@Slf4j
@Aspect
@ManagedResource
public class LoggingAspect implements InitializingBean {

    private final AtomicBoolean enabled;

    public LoggingAspect(boolean enabled) {
        this.enabled = new AtomicBoolean(enabled);
    }

    @SneakyThrows
    @Around("@annotation(dev.shermende.support.spring.aop.logging.annotation.Logging)")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) {
        // do nothing if disabled
        if (!enabled.get()) return proceedingJoinPoint.proceed();
        // logging if enabled
        try {
            log.debug("[Logging before] [{}#{}] [Args:{}]",
                proceedingJoinPoint.getTarget().getClass().getSimpleName(),
                ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getName(),
                proceedingJoinPoint.getArgs()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        final Object proceed = proceedingJoinPoint.proceed();
        try {
            log.debug("[Logging after] [{}#{}] [Args:{}] [Result:{}]",
                proceedingJoinPoint.getTarget().getClass().getSimpleName(),
                ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getName(),
                proceedingJoinPoint.getArgs(),
                proceed
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
        log.warn("Attention!!! @dev.shermende.support.spring.aop.logging.annotation.Logging annotation enabled");
    }

}