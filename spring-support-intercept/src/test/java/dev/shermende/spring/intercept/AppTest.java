package dev.shermende.spring.intercept;

import dev.shermende.spring.intercept.component.Payload;
import dev.shermende.spring.intercept.component.annotation.InterceptArgument;
import dev.shermende.spring.intercept.configuration.TestConfiguration;
import dev.shermende.spring.intercept.db.entity.AccessLog;
import dev.shermende.spring.intercept.db.entity.SecuredEntity;
import dev.shermende.spring.intercept.db.repository.AccessLogRepository;
import dev.shermende.spring.intercept.db.repository.SecuredEntityRepository;
import dev.shermende.spring.intercept.factory.AnnotationHandlerFactory;
import dev.shermende.spring.intercept.factory.HandlerFactory;
import dev.shermende.spring.intercept.handler.InterceptArgumentHandler;
import dev.shermende.spring.intercept.interceptor.LogInterceptor;
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
        Assert.assertEquals(payload.getId(), log.get().getViewedId());
        Assert.assertEquals(LogInterceptor.class.getName(), log.get().getInterceptor());
        Assert.assertEquals(Payload.class.getName(), log.get().getClassName());

        Assert.assertTrue(houston.isPresent());
        Assert.assertEquals(payload.getUuid(), houston.get().getUuid());
    }

}
