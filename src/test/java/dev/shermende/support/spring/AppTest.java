package dev.shermende.support.spring;

import dev.shermende.support.spring.configuration.TestingConfiguration;
import dev.shermende.support.spring.db.entity.InterceptArgumentEntity;
import dev.shermende.support.spring.db.entity.InterceptResultEntity;
import dev.shermende.support.spring.db.entity.Payload;
import dev.shermende.support.spring.db.repository.InterceptArgumentEntityRepository;
import dev.shermende.support.spring.db.repository.InterceptResultEntityRepository;
import dev.shermende.support.spring.db.repository.PayloadRepository;
import dev.shermende.support.spring.factory.impl.HandlerFactory;
import dev.shermende.support.spring.interceptor.InterceptArgumentInterceptor;
import dev.shermende.support.spring.interceptor.InterceptResultInterceptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestingConfiguration.class})
public class AppTest {

    @Autowired
    private HandlerFactory factory;

    @Autowired
    private PayloadRepository payloadRepository;

    @Autowired
    private InterceptArgumentEntityRepository interceptArgumentEntityRepository;

    @Autowired
    private InterceptResultEntityRepository interceptResultEntityRepository;

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
    @Test(expected = IllegalStateException.class)
    public void interceptorValidationException() {
        factory.getInstance(HandlerFactory.VALIDATE).handle(new Payload());
    }

    /**
     * ok
     */
    @Test
    public void ok() {
        final Payload data = Payload.builder().uuid(UUID.randomUUID().toString()).build();
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