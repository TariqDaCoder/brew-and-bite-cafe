package com.brewandbite.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * A simple in-memory FIFO queue for any type of item. Useful for sharing orders
 * between CustomerController and BaristaController.
 *
 * @param <T> the type of elements in the queue
 */
public class InMemoryQueue<T> {

    private final Deque<T> buffer = new ArrayDeque<>();

    /**
     * Enqueue an item at the end of the queue (new orders go here).
     *
     * @param item the element to enqueue
     */
    public void enqueue(T item) {
        buffer.addLast(item);
    }

    /**
     * Dequeue an item from the front of the queue (oldest order).
     *
     * @return the head of the queue, or null if the queue is empty
     */
    public T dequeue() {
        return buffer.pollFirst();
    }

    /**
     * Peek at all items currently in the queue without removing them.
     *
     * @return a list of the queued items, in FIFO order
     */
    public List<T> all() {
        return new ArrayList<>(buffer);
    }

    /**
     * Check if the queue has no items.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    /**
     * Get the number of items currently queued.
     *
     * @return the queue size
     */
    public int size() {
        return buffer.size();
    }
}
