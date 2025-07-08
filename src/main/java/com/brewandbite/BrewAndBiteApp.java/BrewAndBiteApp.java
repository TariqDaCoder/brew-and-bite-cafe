package com.brewandbite;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BrewAndBiteApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Label welcomeLabel = new Label("Welcome to Brew & Bite Cafe!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-padding: 20px;");
        
        VBox root = new VBox(welcomeLabel);
        root.setStyle("-fx-alignment: center; -fx-spacing: 20px;");
        
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Brew & Bite Cafe System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}