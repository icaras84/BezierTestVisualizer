package com.icaras84.beziertestvisualizer.utils.proxy;

import com.icaras84.beziertestvisualizer.utils.Publisher;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class IndirectProxy<T> implements VariableProxy<T> {

    private final Publisher<T> publisher;
    private final Supplier<T> getter;
    private final Consumer<T> setter;

    public IndirectProxy(Supplier<T> getter, Consumer<T> setter) {
        this.getter = getter;
        this.setter = setter;
        this.publisher = new Publisher<>();
    }

    @Override
    public T get() {
        return this.getter.get();
    }

    @Override
    public void set(T value) {
        this.setter.accept(value);
    }

    @Override
    public Publisher<T> getPublisher() {
        return this.publisher;
    }

    public Supplier<T> getter() {
        return getter;
    }

    public Consumer<T> setter() {
        return setter;
    }
}
