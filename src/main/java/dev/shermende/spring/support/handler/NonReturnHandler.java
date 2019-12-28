package dev.shermende.spring.support.handler;

public interface NonReturnHandler<T> {

    void handle(T t);

}
