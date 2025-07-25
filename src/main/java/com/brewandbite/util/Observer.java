package com.brewandbite.util;

public interface Observer<T> {

    /**
     * This method is called when the observed object is changed.
     *
     * @param data the data that has changed
     */
    void update(T data);
}
