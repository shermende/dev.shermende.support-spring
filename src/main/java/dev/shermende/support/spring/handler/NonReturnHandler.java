package dev.shermende.support.spring.handler;

public interface NonReturnHandler<T> {

    void handle(T t);

}
