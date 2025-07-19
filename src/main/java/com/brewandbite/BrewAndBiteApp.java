package com.brewandbite;

import java.util.List;
import java.util.Optional;

import com.brewandbite.controller.BaristaController;
import com.brewandbite.controller.CustomerController;
import com.brewandbite.controller.ManagerController;
import com.brewandbite.model.inventory.Ingredient;
import com.brewandbite.model.items.MenuItem;
import com.brewandbite.model.orders.Order;
import com.brewandbite.util.DataManager;
import com.brewandbite.util.InMemoryQueue;
import com.brewandbite.views.BaristaView;
import com.brewandbite.views.CustomerView;
import com.brewandbite.views.ManagerView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

public class BrewAndBiteApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1) Load and flatten data
        List<MenuItem> menu = DataManager.loadAllMenuItems();
        List<Ingredient> inventory = DataManager.loadAllIngredients();

        // 2) Shared queue for orders
        InMemoryQueue<Order> orderQueue = new InMemoryQueue<>();

        // 3) Let the user pick a role
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(
                "Customer", List.of("Customer", "Barista", "Manager")
        );
        choiceDialog.setTitle("Select Role");
        choiceDialog.setHeaderText("Welcome to Brew & Bite Café!\nChoose your role:");
        Optional<String> opt = choiceDialog.showAndWait();
        if (opt.isEmpty()) {
            primaryStage.close();
            return;
        }

        String role = opt.get();
        primaryStage.setTitle("Brew & Bite Cafe  " + role);
        Scene scene;

        switch (role) {
            case "Barista" -> {
                BaristaView bv = new BaristaView();
                BaristaController bc = new BaristaController(bv, orderQueue);
                bc.initialize();
                scene = new Scene(bv, 800, 600);
            }
            case "Manager" -> {
                ManagerView mv = new ManagerView();
                ManagerController mc = new ManagerController(mv);
                mc.initialize();
                scene = new Scene(mv, 800, 600);
            }
            default -> {
                CustomerView cv = new CustomerView();
                CustomerController cc = new CustomerController(cv, menu, orderQueue);
                cc.initialize();
                scene = new Scene(cv, 800, 600);
            }
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
