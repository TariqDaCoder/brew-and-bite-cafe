package com.brewandbite.controller;

import java.util.List;
import com.brewandbite.model.items.Cookie;
import com.brewandbite.model.items.Coffee;
import com.brewandbite.model.items.MenuItem;
import com.brewandbite.model.items.Muffin;
import com.brewandbite.model.items.Muffin.MuffinType;
import com.brewandbite.model.items.Tea;
import com.brewandbite.model.items.Tea.TeaType;
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
        this.menuItems.add(new Coffee(1, CoffeeType.LATTE));
        this.menuItems.add(new Coffee(1, CoffeeType.ESPRESSO));
        this.menuItems.add(new Coffee(1, CoffeeType.CAPPUCCINO));
        this.menuItems.add(new Tea(1, TeaType.BLACK));
        this.menuItems.add(new Tea(1, TeaType.GREEN));
        this.menuItems.add(new Tea(1, TeaType.HERBAL));
        this.menuItems.add(new Muffin(1, MuffinType.BLUEBERRY));
        this.menuItems.add(new Muffin(1, MuffinType.CHOCOLATE_CHIP));

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

    public void initialize() {
        // 1) Populate menu & cart
        view.menuList.setItems(menuItems);
        view.cartList.setItems(cartItems);
        
        //Related to order manipulation------------------------------------
        view.addToOrder.setOnAction(e -> {
            try {
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
        //end of order manipulation--------------------------

        // 2) Place Order → add selected item to cart
        view.placeOrder.setOnAction(e -> {
            try {
                MenuItem sel = view.menuList.getSelectionModel().getSelectedItem();
                if (sel != null) {
                    cartItems.add(sel);
                    System.out.println("✅ Added to cart: " + sel.getName() + " - $" + sel.calculatePrice());
                    System.out.println("📦 Cart now has " + cartItems.size() + " items");
                } else {
                    System.out.println("❌ No item selected to add to cart");
                }
            } catch (Exception ex) {
                System.out.println("🚫 Error adding item to cart: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // 3) Clear Order → empty the cart
        view.clearOrder.setOnAction(e -> {
            try {
                int itemCount = cartItems.size();
                cartItems.clear();
                System.out.println("🧹 Cleared cart (" + itemCount + " items removed)");
            } catch (Exception ex) {
                System.out.println("🚫 Error clearing cart: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // 4) Submit Order → build, enqueue, clear UI
        view.submitOrder.setOnAction(e -> {
            try {
                submitOrder();
            } catch (Exception ex) {
                System.out.println("🚫 Error in submit order button: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    private void submitOrder() {
        try {
            // Validation checks with feedback
            if (cartItems.isEmpty()) {
                System.out.println("❌ Cannot submit: Cart is empty");
                return;
            }

            if (view.nameField.getText().isBlank()) {
                System.out.println("❌ Cannot submit: Customer name is required");
                return;
            }

            // Build the order
            String customerName = view.nameField.getText().trim();
            Order order = new Order(nextOrderId++, customerName);

            // Add items and calculate total
            cartItems.forEach(order::addItem);

            // Enqueue the order
            orderQueue.enqueue(order);

            // Success feedback
            System.out.println("🎉 SUCCESS: Order submitted!");
            System.out.println("👤 Customer: " + order.getCustomerName());
            System.out.println("🛒 Items: " + order.getItems().size());
            System.out.println("💰 Total: $" + String.format("%.2f", order.getTotalPrice()));
            System.out.println("📝 Order ID: " + order.getOrderId());
            System.out.println("🔄 Queue now has " + orderQueue.size() + " orders");
            System.out.println("📋 Order details: " + order);
            System.out.println("─────────────────────────────────────────");

            // Reset UI
            cartItems.clear();
            view.nameField.clear();

            System.out.println("✨ UI cleared - ready for next order");

        } catch (Exception ex) {
            System.out.println("🚫 CRITICAL ERROR submitting order: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}