package dev.shermende.spring.support.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.logging.Logger;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan({
        "dev.shermende.spring.support.aop",
        "dev.shermende.spring.support.handler",
        "dev.shermende.spring.support.factory",
})
public class InterceptorConfiguration implements InitializingBean {
    private static final Logger LOGGER = Logger.getLogger(InterceptorConfiguration.class.getName());

    @Override
    public void afterPropertiesSet() {
        LOGGER.fine("Interceptors successfully enabled");
    }

}