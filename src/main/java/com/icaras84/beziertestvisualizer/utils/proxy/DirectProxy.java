package com.icaras84.beziertestvisualizer.utils.proxy;

import com.icaras84.beziertestvisualizer.utils.Publisher;

public class DirectProxy<T> implements VariableProxy<T> {

    private final Publisher<T> publisher;
    private T value;

    public DirectProxy(T value) {
        this.publisher = new Publisher<>();
        this.value = value;
    }

    @Override
    public T get() {
        return this.value;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }

    @Override
    public Publisher<T> getPublisher() {
        return this.publisher;
    }
}
