package com.brewandbite.views;

import com.brewandbite.model.items.MenuItem;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomerView extends VBox {

    public final ListView<MenuItem> menuList = new ListView<>();
    public final ListView<MenuItem> cartList = new ListView<>();
    public final Button placeOrder = new Button("Add to Cart");
    public final Button submitOrder = new Button("Submit Order");
    public final Button clearOrder = new Button("Clear Cart");
    public final HBox orderButtons = new HBox(10, placeOrder, submitOrder, clearOrder);
    public final TextField nameField = new TextField();
    private static final String CUSTOMER_NAME_LABEL = "Customer Name:";
    private static final String MENU_LABEL = "Menu";
    private static final String YOUR_CART_LABEL = "Your Cart";

    public CustomerView() {
        setSpacing(15);
        setPadding(new Insets(10));

        Label customerNameHeader = new Label(CUSTOMER_NAME_LABEL);
        Label menuHeader = new Label(MENU_LABEL);
        Label cartHeader = new Label(YOUR_CART_LABEL);

        // Custom cell factory for menu list - shows user-friendly format
        menuList.setCellFactory(listView -> new ListCell<MenuItem>() {
            @Override
            protected void updateItem(MenuItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Format: "Name - $Price (Description)"
                    setText(String.format("%s - $%.2f (%s)",
                            item.getName(),
                            item.calculatePrice(),
                            item.getDescription()));
                }
            }
        });

        // Custom cell factory for cart list - shows user-friendly format
        cartList.setCellFactory(listView -> new ListCell<MenuItem>() {
            @Override
            protected void updateItem(MenuItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Format: "Name - $Price"
                    setText(String.format("%s - $%.2f",
                            item.getName(),
                            item.calculatePrice()));
                }
            }
        });

        getChildren().addAll(
                customerNameHeader, nameField,
                menuHeader, menuList,
                orderButtons,
                cartHeader, cartList
        );
    }
}