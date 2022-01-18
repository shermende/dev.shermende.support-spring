package dev.shermende.support.spring.processor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AbstractStepProcessorTest.AbstractStepProcessorTestConfiguration.class})
public class AbstractStepProcessorTest {

    @SpyBean
    private AbstractStepProcessorTestStepOne stepOne;

    @SpyBean
    private AbstractStepProcessorTestStepTwo stepTwo;

    @Autowired
    private AbstractStepProcessorTestStepProcessor stepProcessor;

    @Test
    public void execute() {
        stepProcessor.execute(new Object());
        final InOrder inOrder = inOrder(stepOne, stepTwo);
        then(stepOne).should(inOrder, times(1)).execute(any());
        then(stepTwo).should(inOrder, times(1)).execute(any());
    }

    @ComponentScan
    public static class AbstractStepProcessorTestConfiguration {

    }

    @Component
    public static class AbstractStepProcessorTestStepProcessor extends AbstractStepProcessor<Object, Object> {
        protected AbstractStepProcessorTestStepProcessor(BeanFactory factory) {
            super(factory);
        }

        @Override
        protected Collection<Class<? extends Step<Object, Object>>> steps() {
            return Arrays.asList(AbstractStepProcessorTestStepOne.class, AbstractStepProcessorTestStepTwo.class);
        }
    }

    @Component
    public static class AbstractStepProcessorTestStepOne implements Step<Object, Object> {
        @Override
        public Object execute(Object o) {
            return o;
        }
    }

    @Component
    public static class AbstractStepProcessorTestStepTwo implements Step<Object, Object> {
        @Override
        public Object execute(Object o) {
            return o;
        }
    }

}