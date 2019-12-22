package dev.shermende.spring.intercept.component;

import java.lang.annotation.Annotation;

public class InterceptArgumentHolder {

    private Annotation annotation;

    private Object argument;

    public Object getArgument() {
        return argument;
    }

    public InterceptArgumentHolder setArgument(Object argument) {
        this.argument = argument;
        return this;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public InterceptArgumentHolder setAnnotation(Annotation annotation) {
        this.annotation = annotation;
        return this;
    }

}
