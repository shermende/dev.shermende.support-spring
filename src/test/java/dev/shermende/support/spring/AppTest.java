package dev.shermende.support.spring;

import dev.shermende.support.spring.component.Payload;
import dev.shermende.support.spring.component.annotation.InterceptArgument;
import dev.shermende.support.spring.configuration.TestConfiguration;
import dev.shermende.support.spring.db.entity.AccessLog;
import dev.shermende.support.spring.db.entity.SecuredEntity;
import dev.shermende.support.spring.db.repository.AccessLogRepository;
import dev.shermende.support.spring.db.repository.SecuredEntityRepository;
import dev.shermende.support.spring.factory.HandlerFactory;
import dev.shermende.support.spring.factory.impl.AnnotationHandlerFactory;
import dev.shermende.support.spring.handler.impl.InterceptArgumentHandler;
import dev.shermende.support.spring.interceptor.LogInterceptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class AppTest {

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private SecuredEntityRepository securedEntityRepository;

    @Autowired
    private AnnotationHandlerFactory annotationHandlerFactory;

    @Autowired
    private HandlerFactory factory;

    /**
     * AnnotationHandlerFactory keys test
     */
    @Test
    public void annotationHandlerFactoryKeysTest() {
        Assert.assertTrue(annotationHandlerFactory.containsKey(InterceptArgument.class.getName()));
    }

    /**
     * AnnotationHandlerFactory values test
     */
    @Test
    public void annotationHandlerFactoryValuesTest() {
        Assert.assertTrue(annotationHandlerFactory.getInstance(InterceptArgument.class.getName())
                .getClass().isAssignableFrom(InterceptArgumentHandler.class));
    }

    /**
     * wrong factory key
     */
    @Test(expected = IllegalArgumentException.class)
    public void factoryWrongKeyException() {
        factory.getInstance("wrongKey").handle(new Payload());
    }

    /**
     * interceptor wrong support
     */
    @Test(expected = IllegalArgumentException.class)
    public void interceptorWrongSupportException() {
        factory.getInstance("interceptWrongSupport").handle(new Payload());
    }

    /**
     * validate interceptor
     */
    @Test(expected = IllegalArgumentException.class)
    public void interceptorValidationException() {
        factory.getInstance("interceptValidate").handle(new Payload());
    }

    /**
     * access-control interceptor
     */
    @Test
    public void okIntercept() {
        final Payload payload = new Payload().setId(99L).setUuid(UUID.randomUUID().toString());
        factory.getInstance("intercept").handle(payload);

        Optional<AccessLog> log = accessLogRepository.findFirstByOrderByIdDesc();
        Optional<SecuredEntity> houston = securedEntityRepository.findFirstByOrderByIdDesc();

        Assert.assertTrue(log.isPresent());
        Assert.assertEquals(payload.getId(), log.orElseThrow(RuntimeException::new).getViewedId());
        Assert.assertEquals(LogInterceptor.class.getName(), log.orElseThrow(RuntimeException::new).getInterceptor());
        Assert.assertEquals(Payload.class.getName(), log.orElseThrow(RuntimeException::new).getClassName());

        Assert.assertTrue(houston.isPresent());
        Assert.assertEquals(payload.getUuid(), houston.orElseThrow(RuntimeException::new).getUuid());
    }

}
