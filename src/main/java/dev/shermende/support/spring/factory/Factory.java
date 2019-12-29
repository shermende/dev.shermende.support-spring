package dev.shermende.support.spring.factory;


public interface Factory<K, C> {

    boolean containsKey(
            K key
    );

    void registry(
            K key,
            Class<? extends C> aClass
    );

    C getInstance(
            K key
    );

}
