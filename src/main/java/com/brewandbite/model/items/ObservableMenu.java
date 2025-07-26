package com.brewandbite.model.items;

import java.util.ArrayList;
import java.util.List;

import com.brewandbite.util.Observable;

public class ObservableMenu extends Observable<List<MenuItem>> {

    private final List<MenuItem> menu = new ArrayList<>();

    /**
     * Default constructor initializes with an empty menu. This can be used to
     * create an empty observable menu.
     *
     * @param initial the initial list of menu items
     */
    public ObservableMenu(List<MenuItem> initial) {
        menu.addAll(initial);
    }

    /**
     * Adds a new item to the menu.
     *
     * @param item the menu item to add
     */
    public void addItem(MenuItem item) {
        menu.add(item);
        notifyObservers(List.copyOf(menu));
    }

    /**
     * Removes an item from the menu.
     *
     * @param item the menu item to remove
     */
    public void removeItem(MenuItem item) {
        menu.remove(item);
        notifyObservers(List.copyOf(menu));
    }

    /**
     * Returns a list of all items currently in the menu. If immutable is true,
     * returns an unmodifiable list; otherwise, returns a mutable copy.
     *
     * @param immutable whether the returned list should be immutable
     * @return a list of the menu items
     */
    public List<MenuItem> getAll() {
        return List.copyOf(menu);
    }
}
