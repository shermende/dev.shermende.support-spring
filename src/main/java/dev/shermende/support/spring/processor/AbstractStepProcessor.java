package dev.shermende.support.spring.processor;

import org.springframework.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class AbstractStepProcessor<O, I> {
    private final BeanFactory factory;
    private final List<Class<? extends Step<O, I>>> steps = new ArrayList<>();

    protected AbstractStepProcessor(
        BeanFactory factory
    ) {
        this.factory = factory;
        this.registration();
    }

    protected final void registration() {
        this.steps.addAll(steps());
    }

    public O execute(
        I input
    ) {
        O output = null;
        for (Class<? extends Step<O, I>> step : steps) output = factory.getBean(step).execute(input);
        return Objects.requireNonNull(output);
    }

    protected abstract Collection<Class<? extends Step<O, I>>> steps();

}