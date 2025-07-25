package com.brewandbite.controller;

import com.brewandbite.model.orders.Order;
import com.brewandbite.util.InMemoryQueue;
import com.brewandbite.views.BaristaView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller for barista to view and update pending orders.
 */
public class BaristaController {

    private final BaristaView view;
    private final InMemoryQueue<Order> queue;
    private final ObservableList<Order> pending;

    public BaristaController(BaristaView view, InMemoryQueue<Order> queue) {
        this.view = view;
        this.queue = queue;
        // Initialize the list with current queue contents
        this.pending = FXCollections.observableArrayList();
        try {
            pending.setAll(queue.getAll(false));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Wire up UI event handlers with try-catch guards.
     */
    public void initialize() {
        view.pendingOrders.setItems(pending);

        view.refreshBtn.setOnAction(e -> {
            try {
                pending.setAll(queue.getAll(false));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.markInProgress.setOnAction(e -> {
            try {
                updateStatus(Order.OrderStatus.IN_PROGRESS);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.markReady.setOnAction(e -> {
            try {
                updateStatus(Order.OrderStatus.READY);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void updateStatus(Order.OrderStatus status) {
        Order o = view.pendingOrders.getSelectionModel().getSelectedItem();
        if (o != null) {
            o.setStatus(status);
            view.pendingOrders.refresh();
        }
    }
}
