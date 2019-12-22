package dev.shermende.spring.intercept.aop;

import dev.shermende.spring.intercept.component.InterceptArgumentHolder;
import dev.shermende.spring.intercept.factory.AnnotationHandlerFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Optional;

@Aspect
@Component
public class AopIntercept {

    private final AnnotationHandlerFactory factory;

    public AopIntercept(
            AnnotationHandlerFactory factory
    ) {
        this.factory = factory;
    }

    @Before("@annotation(dev.shermende.spring.intercept.component.annotation.Intercept)")
    public void intercept(JoinPoint joinPoint) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Annotation[][] annotations;
        try {
            annotations = getAnnotations(joinPoint, signature);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }

        for (int i = 0; i < annotations.length; i++) {
            for (int j = 0; j < annotations[i].length; j++) {
                final Object arg = joinPoint.getArgs()[i];
                Optional.of(annotations[i][j])
                        .filter(this::canBeHandle)
                        .ifPresent(var -> handle(arg, var));
            }
        }
    }

    private Annotation[][] getAnnotations(
            JoinPoint joinPoint,
            MethodSignature signature
    ) throws NoSuchMethodException {
        return joinPoint.getTarget().getClass()
                .getMethod(getMethodName(signature), getParameterTypes(signature)).getParameterAnnotations();
    }


    private String getMethodName(
            MethodSignature signature
    ) {
        return signature.getMethod().getName();
    }


    private Class<?>[] getParameterTypes(
            MethodSignature signature
    ) {
        return signature.getMethod().getParameterTypes();
    }

    private boolean canBeHandle(
            Annotation var
    ) {
        return factory.containsKey(var.annotationType().getName());
    }

    private void handle(
            Object arg,
            Annotation annotation
    ) {
        factory.getInstance(annotation.annotationType().getName())
                .handle(new InterceptArgumentHolder().setAnnotation(annotation).setArgument(arg));
    }

}
