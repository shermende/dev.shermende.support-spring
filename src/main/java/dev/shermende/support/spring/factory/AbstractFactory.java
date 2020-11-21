package dev.shermende.support.spring.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractFactory<K, C> implements Factory<K, C> {
    private static final String KEY_NOT_FOUND = "Key '%s' not found";
    private static final String KEY_IS_NULL = "Key is null";
    private static final String CLASS_IS_NULL = "Class is null";

    private final BeanFactory beanFactory;
    private final Map<K, Class<? extends C>> registry = new ConcurrentHashMap<>();

    protected AbstractFactory(
        BeanFactory beanFactory
    ) {
        this.beanFactory = beanFactory;
        this.registration();
    }

    public boolean containsKey(
        K key
    ) {
        Assert.notNull(key, KEY_IS_NULL);
        return registry.containsKey(key);
    }

    public void registry(
        K key,
        Class<? extends C> aClass
    ) {
        Assert.notNull(key, KEY_IS_NULL);
        Assert.notNull(aClass, CLASS_IS_NULL);
        registry.put(key, aClass);
    }

    public C getInstance(
        K key
    ) {
        Assert.notNull(key, KEY_IS_NULL);
        if (containsKey(key)) return beanFactory.getBean(registry.get(key));
        throw new IllegalArgumentException(String.format(KEY_NOT_FOUND, key.toString()));
    }

    protected abstract void registration();

}