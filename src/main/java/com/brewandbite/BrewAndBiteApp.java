package com.brewandbite;

import java.util.List;
import java.util.Optional;

import com.brewandbite.controller.BaristaController;
import com.brewandbite.controller.CustomerController;
import com.brewandbite.controller.ManagerController;
import com.brewandbite.model.inventory.Ingredient;
import com.brewandbite.model.inventory.IngredientManager;
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
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

public class BrewAndBiteApp extends Application {


    @Override
    public void start(Stage primaryStage) {
        // 1) Load and flatten data
        List<MenuItem> menu = DataManager.loadAllMenuItems();
        List<Ingredient> inventory = DataManager.loadAllIngredients();
        
        System.out.println("Ingredients In Inventory: " );
        for (Ingredient ingredient : inventory) {
            System.out.println(ingredient.toString());
        }

        // 2) Shared queue for orders
        InMemoryQueue<Order> orderQueue = new InMemoryQueue<>();
        
        switchRole(primaryStage, menu, inventory, orderQueue);
    }

    // allowing for role switching once already in a role
    private void switchRole(Stage primaryStage, List<MenuItem> menu, List<Ingredient> inventory, InMemoryQueue<Order> orderQueue) {
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

        switch (role) {
            case "Barista" -> openLogin(primaryStage, role, menu, inventory, orderQueue);
            case "Manager" -> openLogin(primaryStage, role, menu, inventory, orderQueue);
            default -> {
                CustomerView cv = new CustomerView();
                cv.switchRoleButton.setOnAction(c -> switchRole(primaryStage, menu, inventory, orderQueue));
                CustomerController cc = new CustomerController(cv, menu, inventory, orderQueue);
                cc.initialize();
                Scene scene = new Scene(cv, 800, 600);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        }
    }
    
    // barista and manager authentication
    // hardcoded usernames and passwords
    private static final String baristaUsername = "barista135";
    private static final String baristaPassword = "barista531";
    private static final String managerUsername = "manager246";
    private static final String managerPassword = "manager642";
    
    // login window for the barista and manager
    private void openLogin(Stage primaryStage, String role, List<MenuItem> menu,
    		List<Ingredient> inventory, InMemoryQueue<Order> orderQueue) {
    	// stage setup
    	Stage lStage = new Stage();
    	Button login = new Button("Login");
    	Label usernameLabel = new Label("Username: ");
    	Label passwordLabel = new Label("Password: ");
    	Label loginError = new Label();
    	
    	// allowing for user input for username and password
    	TextField usernameField = new TextField();
    	PasswordField passwordField = new PasswordField();
    	
    	login.setOnAction (e -> {
    		String usernameInput = usernameField.getText();
    		String passwordInput = passwordField.getText();
    		
    		if (role.equals("Barista") && usernameInput.equals(baristaUsername)
    				&& passwordInput.equals(baristaPassword)) {
    			// barista access is only granted if conditions above are met
                BaristaView bv = new BaristaView();
                bv.switchRoleButton.setOnAction(b -> switchRole(primaryStage, menu, inventory, orderQueue));
                BaristaController bc = new BaristaController(bv, orderQueue);
                bc.initialize();
                Scene scene = new Scene(bv, 800, 600);
                primaryStage.setScene(scene);
                primaryStage.show();
                lStage.close();
    		} else if (role.equals("Manager") && usernameInput.equals(managerUsername)
    				&& passwordInput.equals(managerPassword)) {
    			// manager access is only granted if conditions above are met
                ManagerView mv = new ManagerView();
                mv.switchRoleButton.setOnAction(m -> switchRole(primaryStage, menu, inventory, orderQueue));
                ManagerController mc = new ManagerController(mv);
                mc.initialize();
                Scene scene = new Scene(mv, 800, 600);
                primaryStage.setScene(scene);
                primaryStage.show();
                lStage.close();
    		} else {
    			// clearing password field and displaying error message if login is invalid
    			passwordField.clear();
    			loginError.setText("Username and/or password is incorrect.\nPlease try again.");
    		}
    	});
    	
    	// formatting for lStage
		VBox vBox = new VBox(20);
		vBox.setPadding(new Insets(20));
		vBox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, login, loginError);
		Scene scene = new Scene(vBox, 300, 350);
		lStage.setTitle("Login Window");
		lStage.setScene(scene);
		lStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

