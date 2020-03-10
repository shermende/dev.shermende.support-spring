package dev.shermende.support.spring.configuration;

import dev.shermende.support.spring.aop.AopIntercept;
import dev.shermende.support.spring.aop.AopInterceptResult;
import dev.shermende.support.spring.factory.impl.AnnotationHandlerFactory;
import dev.shermende.support.spring.handler.impl.InterceptArgumentHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.logging.Logger;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class InterceptorAutoConfiguration implements InitializingBean {
    private static final Logger LOGGER = Logger.getLogger(InterceptorAutoConfiguration.class.getName());

    @Bean
    public AnnotationHandlerFactory annotationHandlerFactory(BeanFactory factory) {
        return new AnnotationHandlerFactory(factory);
    }

    @Bean
    public InterceptArgumentHandler interceptArgumentHandler(BeanFactory factory) {
        return new InterceptArgumentHandler(factory);
    }

    @Bean
    public AopInterceptResult aopInterceptResult(BeanFactory factory) {
        return new AopInterceptResult(factory);
    }

    @Bean
    public AopIntercept aopIntercept(AnnotationHandlerFactory factory) {
        return new AopIntercept(factory);
    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.fine("Interceptors successfully enabled");
    }

}