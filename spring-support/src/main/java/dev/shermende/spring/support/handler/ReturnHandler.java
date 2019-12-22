package dev.shermende.spring.support.handler;

public interface ReturnHandler<R, T> {

    R handle(T t);

}
