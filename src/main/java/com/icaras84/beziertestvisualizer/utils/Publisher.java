package com.icaras84.beziertestvisualizer.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

/**
 * A class that emulates the publisher-subscriber model using this and the <code>Consumer</code> class
 * @param subscribers initial <code>Consumer</code> list
 * @param <T> datatype that will be consumed
 */
public record Publisher<T>(Vector<Consumer<T>> subscribers) implements Copyable<Publisher<T>> {

    /**
     * This constructor creates this object with an empty list of subscribers
     */
    public Publisher() {
        this(new Vector<>());
    }

    @Override
    public Publisher<T> copy(){
        return new Publisher<>(new Vector<>(this.subscribers));
    }

    /**
     * Add a subscriber to this publisher. This is a wrapper for the subscriber list's <code>add</code> method.
     * @param subscriber a <code>Consumer</code>
     */
    public void add(Consumer<T> subscriber) {
        this.subscribers.add(subscriber);
    }

    /**
     * Removes a subscriber from this publisher. This is a wrapper for the subscriber list's <code>remove</code> method.
     * @param subscriber <code>Consumer</code> object reference
     */
    public void remove(Consumer<T> subscriber) {
        this.subscribers.remove(subscriber);
    }

    /**
     * Updates all subscribers currently on the subscriber list with input data to consume.
     * @param data data for each subscriber to accept
     */
    public void updateAll(T data){
        this.subscribers.forEach(subscriber -> subscriber.accept(data));
    }

    /**
     * Updates all subscribers on the list except the listed with input data to consume.
     * @param data data for each non-excluded subscriber to accept
     * @param excluded a list of <code>Consumer</code> references to exclude
     */
    public void updateExcept(T data, List<Consumer<T>> excluded){
        List<Consumer<T>> filtered = this.subscribers.parallelStream()
                .filter(sub -> !excluded.contains(sub))
                .toList();

        filtered.forEach(s -> s.accept(data));
    }
}
