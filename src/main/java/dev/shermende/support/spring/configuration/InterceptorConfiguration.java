package dev.shermende.support.spring.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.logging.Logger;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan({
        "dev.shermende.support.spring.aop",
        "dev.shermende.support.spring.handler",
        "dev.shermende.support.spring.factory",
})
public class InterceptorConfiguration implements InitializingBean {
    private static final Logger LOGGER = Logger.getLogger(InterceptorConfiguration.class.getName());

    @Override
    public void afterPropertiesSet() {
        LOGGER.fine("Interceptors successfully enabled");
    }

}