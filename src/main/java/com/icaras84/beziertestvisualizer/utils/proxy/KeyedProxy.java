package com.icaras84.beziertestvisualizer.utils.proxy;

import com.icaras84.beziertestvisualizer.utils.Publisher;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class KeyedProxy<K, T> implements VariableProxy<T>{

    private final Publisher<T> publisher;
    private K key;
    private final Function<K, T> getter;
    private final BiConsumer<K, T> setter;

    public KeyedProxy(K key, Function<K, T> getter, BiConsumer<K, T> setter) {
        this.publisher = new Publisher<>();
        this.key = key;
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public T get() {
        return this.getter.apply(this.key);
    }

    @Override
    public void set(T value) {
        this.setter.accept(this.key, value);
    }

    @Override
    public Publisher<T> getPublisher() {
        return this.publisher;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public static <T1> KeyedProxy<Integer, T1> ofList(int idx, List<T1> source) {
        return new KeyedProxy<>(idx, source::get, source::set);
    }

    public static <T1> KeyedProxy<Integer, T1> ofArray(int idx, T1[] source) {
        return new KeyedProxy<>(idx, arrIdx -> source[arrIdx], (arrIdx, t1) -> source[arrIdx] = t1);
    }

    public static <K1, T1>  KeyedProxy<K1, T1> ofMap(K1 key, Map<K1, T1> source) {
        return new KeyedProxy<>(key, source::get, source::put);
    }
}
