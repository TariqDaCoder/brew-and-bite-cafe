package com.brewandbite.controller;

import java.util.List;
import com.brewandbite.model.items.Cookie;
import com.brewandbite.model.items.Coffee;
import com.brewandbite.model.items.MenuItem;
import com.brewandbite.model.items.Coffee.CoffeeType;
import com.brewandbite.model.items.Cookie.CookieType;
import com.brewandbite.model.items.Beverage;
import com.brewandbite.model.orders.Order;
import com.brewandbite.util.Customizable;
import com.brewandbite.util.InMemoryQueue;
import com.brewandbite.views.CustomerView;
import com.brewandbite.views.CustomizationView;
import com.brewandbite.util.MenuItemFactory;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class CustomerController {
    private final MenuItemFactory menuItemFactory;
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

        this.menuItems.add(new Cookie(1, CookieType.OATMEAL_RAISIN));
        this.menuItems.add(new Cookie(1, CookieType.CHOCOLATE_CHIP));
        this.menuItems.add(new Coffee(1, CoffeeType.BLACK));

        menuItemFactory = new MenuItemFactory();
        this.orderQueue = queue;
    }

    public void addSelectionToCart(MenuItem sel) {
        //create new instance of selection via factory
        //and add to cart
        MenuItem menuItemToAdd = menuItemFactory.initializeAndGetMenuItem(sel);
        cartItems.add(menuItemToAdd);
    }

    public void removeFromOrder(MenuItem sel) {
        cartItems.remove(sel);
    }

    public void clearCart() {
        cartItems.clear();
        //cartItemsToDisplay.clear();
    }

    //gets the selected MenuItem from the selected string that the Customer sees
    public <T> MenuItem getSelectedItem(ListView<T> list, ObservableList<MenuItem> relaventList) {
        int selectionIndex = list.getSelectionModel().getSelectedIndex();
        return relaventList.get(selectionIndex);
    }

    public void goToCustomizationScene(Stage currentStage, MenuItem selectedItem) {
        System.err.println("test");        
        CustomizationView customizationView = new CustomizationView();
        Scene scene = new Scene(customizationView, 800, 600);
        
        //add the menu item they want to customize
        customizationView.selectedBeverage = (Beverage)selectedItem;
        customizationView.selectedItem.getItems().add(customizationView.selectedBeverage);
        customizationView.customerView = view.customizeItem.getScene();

        currentStage.setScene(scene);
        currentStage.show();
    }

    public void initialize() {
        // 1) Populate menu & cart
        //this is what the user sees
        view.menuList.setItems(menuItems);
        view.cartList.setItems(cartItems);

        // 2) Place Order → add selected item to cart
        view.addToOrder.setOnAction(e -> {
            try {
                //MenuItem sel = new Cookie(1, null);
                MenuItem sel = getSelectedItem(view.menuList, menuItems);
                if (sel != null) {
                    addSelectionToCart(sel);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.removeFromOrder.setOnAction(e -> {
            try {
                //MenuItem sel = view.menuList.getSelectionModel().getSelectedItem();
                MenuItem sel = getSelectedItem(view.cartList, cartItems);
                if (sel != null) {
                    removeFromOrder(sel);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.customizeItem.setOnAction(e -> {
            try {
                MenuItem sel = getSelectedItem(view.cartList, cartItems);
                Boolean selectionIsCustomizable = (sel instanceof Customizable);
                if (sel != null && selectionIsCustomizable) {
                    Stage currentStage = (Stage)((Node) e.getSource()).getScene().getWindow();
                    goToCustomizationScene(currentStage, sel);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 3) Clear Order → empty the cart
        view.clearOrder.setOnAction(e -> {
            try {
                clearCart();
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
