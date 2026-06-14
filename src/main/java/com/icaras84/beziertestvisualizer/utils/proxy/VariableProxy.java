package com.icaras84.beziertestvisualizer.utils.proxy;

import com.icaras84.beziertestvisualizer.utils.Publisher;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface VariableProxy<T> {
    T get();
    void set(T value);
    default void modify(Function<T, T> modifier){
        set(modifier.apply(get()));
    }

    Publisher<T> getPublisher();

    default void updateAll(){
        getPublisher().updateAll(get());
    }

    default void updateExcept(Consumer<T> source){
        getPublisher().updateExcept(get(), List.of(source));
    }
}
