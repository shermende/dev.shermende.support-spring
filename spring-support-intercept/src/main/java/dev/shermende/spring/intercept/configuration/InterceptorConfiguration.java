package dev.shermende.spring.intercept.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.logging.Logger;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan({
        "dev.shermende.spring.intercept.aop",
        "dev.shermende.spring.intercept.handler",
        "dev.shermende.spring.intercept.factory",
})
public class InterceptorConfiguration implements InitializingBean {
    private static final Logger LOGGER = Logger.getLogger(InterceptorConfiguration.class.getName());

    @Override
    public void afterPropertiesSet() {
        LOGGER.fine("Interceptors successfully enabled");
    }

}