package com.brewandbite.views;

import com.brewandbite.model.orders.Order;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BaristaView extends VBox {

    public final ListView<Order> pendingOrders = new ListView<>();
    public final Button markInProgress = new Button("In Progress");
    public final Button markReady = new Button("Ready");
    public final Button refreshBtn = new Button("Refresh");
    public final Button switchRoleButton = new Button("Switch Roles");
    public final HBox actionButtons = new HBox(10, markInProgress, markReady, refreshBtn);
    private static final String PENDING_ORDER_LABEL = "Pending Orders";

    public BaristaView() {
        setSpacing(15);
        setPadding(new Insets(10));

        Label pendingOrderHeader = new Label(PENDING_ORDER_LABEL);

        // Custom cell factory for orders - shows user-friendly format
        pendingOrders.setCellFactory(listView -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (empty || order == null) {
                    setText(null);
                } else {
                    // Format: "Order #1 - John Doe - $12.50 - Status: PENDING"
                    setText(String.format("Order #%d - %s - $%.2f - Status: %s",
                            order.getOrderId(),
                            order.getCustomerName(),
                            order.getTotalPrice(),
                            order.getStatus()));
                }
            }
        });

        getChildren().addAll(
                pendingOrderHeader, pendingOrders,
                actionButtons, switchRoleButton
        );
    }
}