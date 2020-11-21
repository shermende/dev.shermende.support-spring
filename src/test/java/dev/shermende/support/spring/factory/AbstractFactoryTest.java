package dev.shermende.support.spring.factory;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AbstractFactoryTest.AbstractFactoryTestConfiguration.class})
public class AbstractFactoryTest {

    @SpyBean
    private ConverterOne converterOne;

    @SpyBean
    private ConverterTwo converterTwo;

    @Autowired
    private ConverterFactory factory;

    @Test
    public void getInstance() {
        factory.getInstance("one").convert(new Object());
        factory.getInstance("two").convert(new Object());
        then(converterOne).should(times(1)).convert(any());
        then(converterTwo).should(times(1)).convert(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstanceException() {
        factory.getInstance("notok").convert(new Object());
    }

    @ComponentScan
    public static class AbstractFactoryTestConfiguration {

    }

    @Component
    public static class ConverterOne implements Converter<Object, Object> {
        @Override
        public Object convert(Object o) {
            return o;
        }
    }

    @Component
    public static class ConverterTwo implements Converter<Object, Object> {
        @Override
        public Object convert(Object o) {
            return o;
        }
    }

    @Component
    public static class ConverterFactory extends AbstractFactory<String, Converter<Object, Object>> {
        public ConverterFactory(BeanFactory beanFactory) {
            super(beanFactory);
        }

        @Override
        protected void registration() {
            this.registry("one", ConverterOne.class);
            this.registry("two", ConverterTwo.class);
        }
    }

}