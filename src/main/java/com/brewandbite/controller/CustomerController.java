package com.brewandbite.controller;

// import com.brewandbite.service.InMemoryQueue;

import java.util.List;

import com.brewandbite.model.items.MenuItem;
import com.brewandbite.model.orders.Order;
import com.brewandbite.util.InMemoryQueue;
import com.brewandbite.views.CustomerView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerController {
    private final CustomerView view;
    private final ObservableList<MenuItem> menuItems;
    private final ObservableList<MenuItem> cartItems = FXCollections.observableArrayList();
    private final InMemoryQueue<Order> orderQueue;
    private int nextOrderId = 1;

    public CustomerController(CustomerView view,
                              List<MenuItem> menu,
                              InMemoryQueue<Order> queue) {
        this.view = view;
        this.menuItems = FXCollections.observableArrayList(menu);
        this.orderQueue = queue;
    }

 public void initialize() {
    // Populate menu and cart list views
        view.menuList.setItems(menuItems);
        view.cartList.setItems(cartItems);

// When placeorder is pressed, add that selection to cart
        view.placeOrder.setOnAction(e -> {
            MenuItem sel = view.menuList.getSelectionModel().getSelectedItem();
            if (sel != null) cartItems.add(sel);
        });

        view.clearOrder.setOnAction(e -> cartItems.clear()); // empty cart

         view.submitOrder.setOnAction(e -> submitOrder());
    }

    // Expand on this submit order function - currently bare bones 
    private void submitOrder() {
               if (cartItems.isEmpty() || view.nameField.getText().isBlank()) {
            // show an error label here instead of nothing
            return;
        }
        // Build the Order
        Order order = new Order(nextOrderId++, view.nameField.getText().trim());
        cartItems.forEach(order::addItem);

        // Put order in queue to workers
        orderQueue.enqueue(order);

        // clear cart and name field
        cartItems.clear();
        view.nameField.clear(); 
    }
}