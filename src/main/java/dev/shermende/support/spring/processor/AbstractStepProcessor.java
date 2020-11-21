package dev.shermende.support.spring.processor;

import lombok.Getter;
import org.springframework.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
public abstract class AbstractStepProcessor<O, I> {
    private static final String STEPS_IS_NULL = "Steps is null";
    private static final String STEPS_IS_EMPTY = "Steps is empty";

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