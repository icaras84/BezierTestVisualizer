package com.icaras84.beziertestvisualizer.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

public record Publisher<T>(Vector<Consumer<T>> subscribers) {

    public Publisher() {
        this(new Vector<>());
    }

    public Publisher<T> copy(){
        return new Publisher<>(new Vector<>(this.subscribers));
    }

    public void add(Consumer<T> subscriber) {
        this.subscribers.add(subscriber);
    }

    public void remove(Consumer<T> subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void updateAll(T data){
        this.subscribers.forEach(subscriber -> subscriber.accept(data));
    }

    @SafeVarargs
    public final void updateExcept(T data, Consumer<T>... excluded){
        Vector<Consumer<T>> exclusionList = new Vector<>(Arrays.asList(excluded));

        List<Consumer<T>> filtered = this.subscribers.parallelStream()
                .filter(sub -> !exclusionList.contains(sub))
                .toList();

        filtered.forEach(s -> s.accept(data));
    }
}
