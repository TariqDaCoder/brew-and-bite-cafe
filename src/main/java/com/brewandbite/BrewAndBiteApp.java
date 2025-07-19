package com.brewandbite;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Main extends Application {
	@Override
	public void start(Stage mainStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 600, 400);
			mainStage.setTitle("Brew and Bite Cafe System");

			// buttons for different roles and login
			Button customer = new Button("Customer");
			Button barista = new Button("Barista");
			Button manager = new Button("Manager");

			// giving buttons purpose
			customer.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
				@Override
				public void handle(javafx.event.ActionEvent e) {
					openCustomerWindow();
				}
			});

			barista.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
				@Override
				public void handle(javafx.event.ActionEvent e) {
					openLoginWindow();
				}
			});

			manager.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
				@Override
				public void handle(javafx.event.ActionEvent e) {
					openLoginWindow();
				}
			});

			// BUTTON FORMATTING
			// setting the color of the buttons
			customer.setStyle("-fx-background-color: tan");
			barista.setStyle("-fx-background-color: tan");
			manager.setStyle("-fx-background-color: tan");

			// setting button fonts
			customer.setFont(new Font("Arial", 30));
			barista.setFont(new Font("Arial", 30));
			manager.setFont(new Font("Arial", 30));

			VBox vBox = new VBox(20);
			vBox.setPadding(new Insets(75));
			vBox.getChildren().addAll(customer, barista, manager);

			root.setLeft(vBox); // sets the buttons on the left side of the stage
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			mainStage.setScene(scene);
			mainStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// barista and manager role designated username and passwords
	private static final String baristaUsername = "barista135";
	private static final String baristaPassword = "barista531";
	private static final String managerUsername = "manager246";
	private static final String managerPassword = "manager642";

	// making a login window for the barista and manager
	private void openLoginWindow() {
		Stage lStage = new Stage();
		Button login = new Button("Login");
		Label usernameLabel = new Label("Username: ");
		Label passwordLabel = new Label("Password: ");
		// allowing for input for username and password
		TextField usernameField = new TextField();
		PasswordField passwordField = new PasswordField();
		Label loginError = new Label();
		loginError.setStyle("-fx-text-fill: red;");

		login.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
			@Override
			public void handle(javafx.event.ActionEvent e) {
				String usernameInput = usernameField.getText();
				String passwordInput = passwordField.getText();

				if (usernameInput.equals(baristaUsername) && passwordInput.equals(baristaPassword)) {
					openBaristaWindow();
					lStage.close(); // closes login screen if credentials are correct
				} else if (usernameInput.equals(managerUsername) && passwordInput.equals(managerPassword)) {
					openManagerWindow();
					lStage.close();
				} else {
					// clearing password field and displaying error message if invalid
					passwordField.clear();
					loginError.setText("Username and/or password in incorrect.\nPlease try again.");
				}
			}
		});

		// formatting of buttons and scene
		VBox vBox = new VBox(20);
		vBox.setPadding(new Insets(20));
		vBox.getChildren().addAll(usernameLabel, usernameField,
				passwordLabel, passwordField, login, loginError);
		Scene scene = new Scene(vBox, 300, 350);
		lStage.setTitle("Login Window");
		lStage.setScene(scene);
		lStage.show();
	}

	// making new stages/windows for the separate user roles
	// I would like to eventually switch panes or scenes rather than stages (rather than having multiple windows)
	private void openCustomerWindow() {
		Stage cStage = new Stage();
		Label customerLabel = new Label("Please enter a name for the order: ");
		TextField customerField = new TextField();

		Button startOrder = new Button("Enter");
		startOrder.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
			@Override
			public void handle(javafx.event.ActionEvent e) {
				String customerInput = customerField.getText();
				openOrderWindow(customerInput);
			}
		});
		VBox vBox = new VBox(15, navigationButtons(cStage));
		vBox.setPadding(new Insets(15));
		vBox.getChildren().addAll(customerLabel, customerField, startOrder);
		Scene scene = new Scene(vBox, 600, 500);
		cStage.setTitle("Customer Window");
		cStage.setScene(scene);
		cStage.show();
	}

	private void openBaristaWindow() {
		Stage bStage = new Stage();
		Label label = new Label("You have opened the Barista window");
		VBox box = new VBox(label, navigationButtons(bStage));
		box.setPadding(new Insets(15));
		Scene scene = new Scene(box, 600, 500);
		bStage.setTitle("Barista Window");
		bStage.setScene(scene);
		bStage.show();
	}

	private void openManagerWindow() {
		Stage mStage = new Stage();
		Label label = new Label("You have opened the Manager window");
		VBox box = new VBox(label, navigationButtons(mStage));
		Scene scene = new Scene(box, 600, 500);
		mStage.setTitle("Manager Window");
		mStage.setScene(scene);
		mStage.show();
	}
	
	private void openOrderWindow(String customerName) {
		Stage oStage = new Stage();
		Label label = new Label("Hi " +  customerName + "! Please add items to your order:" );
		VBox box = new VBox(label, navigationButtons(oStage));
		Scene scene = new Scene(box, 600, 500);
		oStage.setTitle("Order Window");
		oStage.setScene(scene);
		oStage.show();
	}
	
	private HBox navigationButtons(Stage currentStage) {
		// making a back button
		Button back = new Button("Back");
		back.setOnAction(e -> currentStage.close());
		// making a close all windows button
		Button close = new Button("Close all windows");
		close.setOnAction(e -> Platform.exit());
		
		HBox box = new HBox(10, back, close);
		box.setPadding(new Insets(10));
		return box;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
