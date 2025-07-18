package com.brewandbite.views;

import com.brewandbite.model.items.MenuItem;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomerView extends VBox {

    public final ListView<MenuItem> menuList = new ListView<>();
    public final ListView<MenuItem> cartList = new ListView<>();
    public final Button placeOrder = new Button("Place Order"); // Move items into cartlist, not actually submit it
    public final Button submitOrder = new Button("Submit Order"); // submit orders in cart list to workers (Before submiting order and sending it to workers, you would call a third party payment api to handle transaction payments and if successful, sumbmission of order is confirmed and then ask if order to be printed, text, or email to customers before sending their order to baristas to make )
    public final Button clearOrder = new Button("Clear Order");
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

        getChildren().addAll(
                customerNameHeader, nameField,
                menuHeader, menuList,
                orderButtons,
                cartHeader, cartList
        );
    }
}
