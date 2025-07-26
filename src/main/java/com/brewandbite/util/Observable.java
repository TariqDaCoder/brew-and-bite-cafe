package com.brewandbite.util;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    private final List<Observer<T>> observers = new ArrayList<>();

    /**
     * Adds an observer to the list of observers.
     *
     * @param observer the observer to add
     */
    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer the observer to remove
     */
    public void removeObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers about a change.
     *
     * @param data the data that has changed
     */
    protected void notifyObservers(T data) {
        for (Observer<T> observer : observers) {
            observer.update(data);
        }
    }
}
