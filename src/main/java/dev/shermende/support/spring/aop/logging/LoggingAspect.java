package dev.shermende.support.spring.aop.logging;

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
public class LoggingAspect {

    @SneakyThrows
    @Around("@annotation(dev.shermende.support.spring.aop.logging.annotation.Logging)")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            log.debug("[Logging before] [{}#{}] [Args:{}]",
                proceedingJoinPoint.getTarget().getClass(),
                ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod(),
                proceedingJoinPoint.getArgs()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        final Object proceed = proceedingJoinPoint.proceed();
        try {
            log.debug("[Logging after] [{}#{}] [Args:{}] [Result:{}]",
                proceedingJoinPoint.getTarget().getClass(),
                ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod(),
                proceedingJoinPoint.getArgs(),
                proceed
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return proceed;
    }

}