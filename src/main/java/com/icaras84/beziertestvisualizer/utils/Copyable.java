package com.icaras84.beziertestvisualizer.utils;

/**
 * Interface to denote if a class's object can be deep-copied
 * @param <T> the implementing class
 */
public interface Copyable<T> {

    /**
     * Method to return a deep copy of this object
     * @return deep copy
     */
    T copy();
}
