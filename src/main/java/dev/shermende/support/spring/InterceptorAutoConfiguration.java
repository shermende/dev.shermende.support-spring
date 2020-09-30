package dev.shermende.support.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@Configuration
@ComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class InterceptorAutoConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        log.info("Interceptors successfully enabled");
    }

}