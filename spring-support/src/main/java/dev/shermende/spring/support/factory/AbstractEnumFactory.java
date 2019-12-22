package dev.shermende.spring.support.factory;

import org.springframework.beans.factory.BeanFactory;

public abstract class AbstractEnumFactory<K extends Enum<?>, C> extends AbstractFactory<K, C> {

    public AbstractEnumFactory(
            BeanFactory beanFactory
    ) {
        super(beanFactory);
    }

}
