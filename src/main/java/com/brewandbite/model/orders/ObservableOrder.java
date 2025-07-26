package com.brewandbite.model.orders;

import java.util.List;

import com.brewandbite.model.items.MenuItem;
import com.brewandbite.util.Observable;

public class ObservableOrder extends Observable<Order> {

    private final Order order;

    /**
     * Constructs an ObservableOrder that wraps the given Order.
     *
     * @param order the Order to be observed
     */
    public ObservableOrder(Order order) {
        this.order = order;
    }

    /**
     * Returns the underlying Order object.
     *
     * @return the Order being observed
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the status of the order and notifies observers.
     *
     * @param status the new status of the order
     */
    public void setStatus(Order.OrderStatus status) {
        order.setStatus(status);
        notifyObservers(order);
    }

    /**
     * Adds an item to the order and notifies observers.
     *
     * @param item the MenuItem to add to the order
     */
    public void addItem(MenuItem item) {
        order.addItem(item);
        notifyObservers(order);
    }

    /**
     * sets the order items and notifies observers
     *
     * @param items the list of MenuItems to set for the order
     */
    public void setItems(List<MenuItem> items) {
        order.setItems(items);
        order.calculateTotal();
        notifyObservers(order);
    }

    /**
     * Sets the customer name for the order and notifies observers.
     *
     * @param name the new customer name
     */
    public void setCustomerName(String name) {
        order.setCustomerName(name);
        notifyObservers(order);
    }

}
