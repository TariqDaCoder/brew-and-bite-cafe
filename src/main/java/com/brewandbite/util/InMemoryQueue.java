package com.brewandbite.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * A simple in-memory FIFO queue for any type of item. Useful for sharing orders
 * between CustomerController and BaristaController. This is an observable
 * queue, which means we can notify when items are added or removed. AKA State
 * management.
 *
 * @param <T> the type of elements in the queue
 */
public class InMemoryQueue<T> extends Observable<List<T>> {

    private final Deque<T> queue = new ArrayDeque<>();

    /**
     * Enqueue an item at the end of the queue (new orders go here).
     *
     * @param item the element to enqueue
     */
    public void enqueue(T item) {
        queue.addLast(item);
        notifyObservers(List.copyOf(queue));
    }

    /**
     * Dequeue an item from the front of the queue (oldest order).
     *
     * @return the head of the queue, or null if the queue is empty
     */
    public T dequeue() {
        if (queue.isEmpty()) {
            return null;
        }

        T item = queue.removeFirst();

        notifyObservers(List.copyOf(queue));

        return item;
    }

    /**
     * Returns a list of all items currently in the queue. If immutable is true,
     * returns an unmodifiable list; otherwise, returns a mutable copy.
     *
     * @param immutable whether the returned list should be immutable
     * @return a list of the queued items, in FIFO order
     */
    public List<T> getAll(boolean immutable) {
        if (immutable) {
            return List.copyOf(queue);
        } else {
            return new ArrayList<>(queue);
        }
    }

    /**
     * Check if the queue has no items.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Get the number of items currently queued.
     *
     * @return the queue size
     */
    public int size() {
        return queue.size();
    }
}
