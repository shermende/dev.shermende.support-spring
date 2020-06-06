package dev.shermende.support.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.logging.Logger;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class InterceptorAutoConfiguration implements InitializingBean {
    private static final Logger LOGGER = Logger.getLogger(InterceptorAutoConfiguration.class.getName());

    @Override
    public void afterPropertiesSet() {
        LOGGER.fine("Interceptors successfully enabled");
    }

}