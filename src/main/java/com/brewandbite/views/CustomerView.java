package com.brewandbite.views;

import com.brewandbite.model.items.MenuItem;
import com.brewandbite.model.items.Beverage;

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
    public final Button switchRoleButton = new Button("Switch Roles");
    public final Button addToOrder = new Button("Add To Order"); // Move items into cartlist, not actually submit it
    public final Button removeFromOrder = new Button("Remove From Order");
    public final Button customizeItem = new Button("Customize Item");
    public final Button submitOrder = new Button("Submit Order"); // submit orders in cart list to workers (Before submiting order and sending it to workers, you would call a third party payment api to handle transaction payments and if successful, sumbmission of order is confirmed and then ask if order to be printed, text, or email to customers before sending their order to baristas to make )
    public final Button clearOrder = new Button("Clear Order");
    public final HBox cartButtons = new HBox(10, addToOrder, customizeItem, removeFromOrder);
    public final HBox orderButtons = new HBox(10, placeOrder, submitOrder, clearOrder);
    public final TextField nameField = new TextField();
    private static final String CUSTOMER_NAME_LABEL = "Customer Name:";
    private static final String MENU_LABEL = "Menu";
    private static final String YOUR_CART_LABEL = "Your Cart";
    public static final String ITEM_ADDED_STATUS_LABEL = "";
    public Label outOfStockMessage;

    public CustomerView() {
        setSpacing(15);
        setPadding(new Insets(10));

        Label customerNameHeader = new Label(CUSTOMER_NAME_LABEL);
        Label menuHeader = new Label(MENU_LABEL);
        Label cartHeader = new Label(YOUR_CART_LABEL);
        outOfStockMessage = new Label(ITEM_ADDED_STATUS_LABEL);

        //make it so the user just sees the name (and customizations if applicable) of the menuItem
        //not the overridden toString() method
        this.menuList.setCellFactory(param -> new ListCell<MenuItem>() {
            @Override
            protected void updateItem(MenuItem menuItem, boolean empty) {
                    super.updateItem(menuItem, empty);
                    if (empty || menuItem == null || menuItem.getItemName() == null) {
                        setText(null);
                    } else {
                        //setText(menuItem.getItemName());
                                            // Format: "Name - $Price (Description)"
                    setText(String.format("%s - $%.2f (%s)",
                            menuItem.getItemName(),
                            menuItem.getBasePrice(),
                            menuItem.getDescription()));
                    }
            }
        });
        
        this.cartList.setCellFactory(param -> new ListCell<MenuItem>() {
            @Override
            protected void updateItem(MenuItem menuItem, boolean empty) {
                    super.updateItem(menuItem, empty);
                    if (empty || menuItem == null || menuItem.getItemName() == null) {
                        setText(null);
                    } else {
                        //display customizations when applicable
                        if (menuItem instanceof Beverage) {
                            //setText(((Beverage)menuItem).nameWithCustomizationsToString());
                            // Format: "Name: 
                            //         Customizations
                            //         $Price"
                            setText(String.format("%s:\n%s$%.2f",
                                    menuItem.getItemName(),
                                    ((Beverage)menuItem).customizationsToString(),
                                    menuItem.calculatePrice())
                                    );
                        } else {
                            //setText(menuItem.getItemName());
                            // Format: "Name - $Price"
                            setText(String.format("%s - $%.2f",
                                    menuItem.getItemName(),
                                    menuItem.calculatePrice()));
                        }
                    }
            }
        });

        getChildren().addAll(
                customerNameHeader, nameField,
                menuHeader, menuList,
                orderButtons,
                switchRoleButton,
                cartButtons,
                cartHeader, cartList, outOfStockMessage, orderButtons
        );
    }
}
