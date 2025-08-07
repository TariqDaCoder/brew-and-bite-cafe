package com.brewandbite.controller;
import com.brewandbite.model.items.Beverage;
import com.brewandbite.util.Customizable.Customization;
import com.brewandbite.views.CustomizationView;
import javafx.scene.Node;
import javafx.stage.Stage;

public class CustomizationController {
    public final CustomizationView customizationView;

    public CustomizationController(CustomizationView customizationView) {
        this.customizationView = customizationView;
    }

    public void addCustomization(Customization selectedCustomization) {
        //Beverages are the only customizable thing in our system.
        //Should be sufficent for our purposes
        selectedCustomization.applyCustomization(customizationView.selectedBeverage);
        customizationView.selectedItem.getItems().set(0, customizationView.selectedBeverage);
    }

    public void removeCustomization(Customization selectedCustomization) {
        selectedCustomization.removeCustomization(customizationView.selectedBeverage);
        customizationView.selectedItem.getItems().set(0, customizationView.selectedBeverage);
    }

    public void initialize() {
        //Button initialization
        customizationView.addCustomizationToItem.setOnAction(e -> {
            try {
                int selectionIndex = customizationView.possibleItemCustomizations.getSelectionModel().getSelectedIndex();
                Customization selectedCustomization = customizationView.possibleItemCustomizations.getItems().get(selectionIndex);
                if (selectedCustomization != null) {
                    addCustomization(selectedCustomization);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        customizationView.removeCustomizationFromItem.setOnAction(e -> {
            try {
                int selectionIndex = customizationView.possibleItemCustomizations.getSelectionModel().getSelectedIndex();
                Customization selectedCustomization = customizationView.possibleItemCustomizations.getItems().get(selectionIndex);
                if (selectedCustomization != null) {
                    removeCustomization(selectedCustomization);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        customizationView.backToOrder.setOnAction(e -> {
            try {
                //update 
                // customizationView.

                //return to the customer view
                Stage currentStage = (Stage)((Node) e.getSource()).getScene().getWindow();
                currentStage.setScene(customizationView.customerView);
                currentStage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
