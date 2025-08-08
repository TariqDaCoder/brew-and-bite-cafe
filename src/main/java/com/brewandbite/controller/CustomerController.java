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
import com.brewandbite.model.inventory.Ingredient;
import com.brewandbite.model.inventory.IngredientManager;
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
    private final IngredientManager ingredientManager;
    private final MenuItemFactory menuItemFactory;
    private final CustomerView view;
    private final ObservableList<MenuItem> menuItems;
    private final ObservableList<MenuItem> cartItems = FXCollections.observableArrayList();
    private final InMemoryQueue<Order> orderQueue;
    private int nextOrderId = 1;

    public CustomerController(CustomerView view,
                              List<MenuItem> menu,
                              List<Ingredient> inventory,
                              InMemoryQueue<Order> queue) {
        this.view = view;
        this.menuItems = FXCollections.observableArrayList(menu);

        // this.menuItems.add(new Cookie(1, CookieType.OATMEAL_RAISIN));
        // this.menuItems.add(new Cookie(1, CookieType.CHOCOLATE_CHIP));
        // this.menuItems.add(new Coffee(1, CoffeeType.BLACK));
        // this.menuItems.add(new Coffee(1, CoffeeType.LATTE));
        // this.menuItems.add(new Coffee(1, CoffeeType.ESPRESSO));
        // this.menuItems.add(new Coffee(1, CoffeeType.CAPPUCCINO));
        // this.menuItems.add(new Tea(1, TeaType.BLACK));
        // this.menuItems.add(new Tea(1, TeaType.GREEN));
        // this.menuItems.add(new Tea(1, TeaType.HERBAL));
        // this.menuItems.add(new Muffin(1, MuffinType.BLUEBERRY));
        // this.menuItems.add(new Muffin(1, MuffinType.CHOCOLATE_CHIP));
        
        for (Ingredient ingredient : inventory) {
            System.out.println(ingredient);
        }

        this.ingredientManager = new IngredientManager(inventory);
        this.menuItemFactory = new MenuItemFactory();

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
        //need to "give back" all the ingredients that were in the order, since we did not
        //end up using them
        for (MenuItem menuItem : cartItems) {
            ingredientManager.addQuantityOfIngredientsFromSystem(menuItem.getRequiredIngredients());
        }

        cartItems.clear();

        view.outOfStockMessage.setText("");
    }

    public Boolean verifyWeCanAddItemToCartBasedOnStock(MenuItem item) {
        return ingredientManager.verifyWeHaveIngredients(item.getRequiredIngredients());
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
        view.menuList.setItems(menuItems);
        view.cartList.setItems(cartItems);
        
        //Related to order manipulation------------------------------------
        view.addToOrder.setOnAction(e -> {
            try {
                MenuItem sel = getSelectedItem(view.menuList, menuItems);
                Boolean haveEnoughIngredients = verifyWeCanAddItemToCartBasedOnStock(sel);
                if ((sel != null) && haveEnoughIngredients) {
                    addSelectionToCart(sel);

                    //if we successfully added, remove the used ingredients
                    ingredientManager.removeQuantityOfIngredientsFromSystem(sel.getRequiredIngredients());

                    view.outOfStockMessage.setText("");

                    //debugging
                    ingredientManager.printAllIngredientsInSystem();
                } else if(!haveEnoughIngredients) {
                    System.err.println("Do not have sufficient ingredients");
                    view.outOfStockMessage.setText("We are out of stock of " + sel.getItemName() + "!");
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

                    //put the ingredients back if they were not used
                    ingredientManager.addQuantityOfIngredientsFromSystem(sel.getRequiredIngredients());

                    view.outOfStockMessage.setText("");
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
                    //cartItems.add(sel);
                    addSelectionToCart(sel);
                    System.out.println("✅ Added to cart: " + sel.getItemName() + " - $" + sel.calculatePrice());
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
                //cartItems.clear();
                clearCart();
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