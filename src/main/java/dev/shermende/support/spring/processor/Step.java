package dev.shermende.support.spring.processor;

public interface Step<O, I> {
    O execute(I input);
}