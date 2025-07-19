package com.brewandbite.controller;

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
        // 1) Populate menu & cart
        view.menuList.setItems(menuItems);
        view.cartList.setItems(cartItems);

        // 2) Place Order → add selected item to cart
        view.placeOrder.setOnAction(e -> {
            try {
                MenuItem sel = view.menuList.getSelectionModel().getSelectedItem();
                if (sel != null) {
                    cartItems.add(sel);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 3) Clear Order → empty the cart
        view.clearOrder.setOnAction(e -> {
            try {
                cartItems.clear();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 4) Submit Order → build, enqueue, clear UI
        view.submitOrder.setOnAction(e -> {
            try {
                submitOrder();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void submitOrder() {
        try {
            if (cartItems.isEmpty() || view.nameField.getText().isBlank()) {
                // Optionally show an error label here
                return;
            }

            // Build and enqueue the order
            Order order = new Order(nextOrderId++, view.nameField.getText().trim());
            cartItems.forEach(order::addItem);
            orderQueue.enqueue(order);

            // Reset UI
            cartItems.clear();
            view.nameField.clear();

        } catch (Exception ex) {
            // Catch any unexpected errors during order submission
            ex.printStackTrace();
        }
    }
}
