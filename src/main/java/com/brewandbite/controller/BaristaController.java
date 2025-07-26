package com.brewandbite.controller;

import java.util.List;

import com.brewandbite.model.orders.ObservableOrder;
import com.brewandbite.model.orders.Order;
import com.brewandbite.util.InMemoryQueue;
import com.brewandbite.util.Observer;
import com.brewandbite.views.BaristaView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller for barista to view and update pending orders.
 */
public class BaristaController implements Observer<List<ObservableOrder>> {

    private final BaristaView view;
    private final InMemoryQueue<ObservableOrder> queue;
    private final ObservableList<ObservableOrder> pending;

    public BaristaController(BaristaView view, InMemoryQueue<ObservableOrder> queue) {
        this.view = view;
        this.queue = queue;
        this.pending = FXCollections.observableArrayList();
        try {
            pending.setAll(queue.getAll(false));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        queue.addObserver(this); // Register as observer
    }

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

    @Override
    public void update(List<ObservableOrder> orders) {
        pending.setAll(orders);
    }

    private void updateStatus(Order.OrderStatus status) {
        ObservableOrder o = view.pendingOrders.getSelectionModel().getSelectedItem();
        if (o != null) {
            o.setStatus(status);
            view.pendingOrders.refresh();
        }
    }
}
