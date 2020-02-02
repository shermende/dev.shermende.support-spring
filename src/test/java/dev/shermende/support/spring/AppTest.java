package dev.shermende.support.spring;

import dev.shermende.support.spring.component.annotation.InterceptArgument;
import dev.shermende.support.spring.configuration.TestConfiguration;
import dev.shermende.support.spring.db.entity.InterceptArgumentEntity;
import dev.shermende.support.spring.db.entity.InterceptResultEntity;
import dev.shermende.support.spring.db.entity.Payload;
import dev.shermende.support.spring.db.repository.InterceptArgumentEntityRepository;
import dev.shermende.support.spring.db.repository.InterceptResultEntityRepository;
import dev.shermende.support.spring.db.repository.PayloadRepository;
import dev.shermende.support.spring.factory.HandlerFactory;
import dev.shermende.support.spring.factory.impl.AnnotationHandlerFactory;
import dev.shermende.support.spring.handler.impl.InterceptArgumentHandler;
import dev.shermende.support.spring.interceptor.InterceptArgumentInterceptor;
import dev.shermende.support.spring.interceptor.InterceptResultInterceptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class AppTest {

    @Autowired
    private HandlerFactory factory;

    @Autowired
    private AnnotationHandlerFactory annotationHandlerFactory;

    @Autowired
    private PayloadRepository payloadRepository;

    @Autowired
    private InterceptArgumentEntityRepository interceptArgumentEntityRepository;

    @Autowired
    private InterceptResultEntityRepository interceptResultEntityRepository;

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
        factory.getInstance(HandlerFactory.WRONG_KEY).handle(new Payload());
    }

    /**
     * interceptor wrong support
     */
    @Test(expected = IllegalArgumentException.class)
    public void interceptorWrongSupportException() {
        factory.getInstance(HandlerFactory.WRONG_SUPPORT).handle(new Payload());
    }

    /**
     * validate exception interceptor
     */
    @Test(expected = IllegalArgumentException.class)
    public void interceptorValidationException() {
        factory.getInstance(HandlerFactory.VALIDATE).handle(new Payload());
    }

    /**
     * ok
     */
    @Test
    public void ok() {
        final Payload data = new Payload().setUuid(UUID.randomUUID().toString());
        factory.getInstance(HandlerFactory.INTERCEPT).handle(data);

        final Payload payload = payloadRepository
                .findFirstByOrderById().orElseThrow(EntityNotFoundException::new);
        final InterceptArgumentEntity interceptArgument = interceptArgumentEntityRepository
                .findFirstByOrderById().orElseThrow(EntityNotFoundException::new);
        final InterceptResultEntity interceptResult = interceptResultEntityRepository
                .findFirstByOrderById().orElseThrow(EntityNotFoundException::new);

        Assert.assertEquals(data.getId(), payload.getId());
        Assert.assertEquals(data.getUuid(), payload.getUuid());

        Assert.assertEquals(Payload.class.getName(), interceptArgument.getObject());
        Assert.assertEquals(InterceptArgumentInterceptor.class.getName(), interceptArgument.getInterceptor());

        Assert.assertEquals(Payload.class.getName(), interceptResult.getObject());
        Assert.assertEquals(InterceptResultInterceptor.class.getName(), interceptResult.getInterceptor());
    }

}
