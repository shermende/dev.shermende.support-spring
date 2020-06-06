package dev.shermende.support.spring.handler;

public interface ReturnHandler<R, T> {

    R handle(T t);

}
