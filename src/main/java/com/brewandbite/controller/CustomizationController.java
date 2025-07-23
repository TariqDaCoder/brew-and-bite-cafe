package com.brewandbite.controller;
import com.brewandbite.model.items.Beverage;
import com.brewandbite.views.CustomizationView;
import javafx.scene.Node;
import javafx.stage.Stage;

public class CustomizationController {
    public final CustomizationView customizationView;

    public CustomizationController(CustomizationView customizationView) {
        this.customizationView = customizationView;
    }

    public void addCustomization(Beverage.CustomizationType selectedCustomization) {
        //Beverages are the only customizable thing in our system.
        //Should be sufficent for our purposes
        customizationView.selectedBeverage.addCustomization(selectedCustomization);
        customizationView.selectedItem.getItems().set(0, customizationView.selectedBeverage);
    }

    public void removeCustomization(Beverage.CustomizationType selectedCustomization) {
        customizationView.selectedBeverage.removeCustomization(selectedCustomization);
        customizationView.selectedItem.getItems().set(0, customizationView.selectedBeverage);
    }

    public void initialize() {
        //Button initialization
        customizationView.addCustomizationToItem.setOnAction(e -> {
            try {
                int selectionIndex = customizationView.possibleItemCustomizations.getSelectionModel().getSelectedIndex();
                Beverage.CustomizationType selectedCustomization = customizationView.possibleItemCustomizations.getItems().get(selectionIndex);
                if (selectedCustomization != null) {
                    addCustomization(selectedCustomization);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        customizationView.removeCustomizationFromItem.setOnAction(e -> {
            try {
                //MenuItem sel = new Cookie(1, null);
                int selectionIndex = customizationView.possibleItemCustomizations.getSelectionModel().getSelectedIndex();
                Beverage.CustomizationType selectedCustomization = customizationView.possibleItemCustomizations.getItems().get(selectionIndex);
                if (selectedCustomization != null) {
                    removeCustomization(selectedCustomization);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        customizationView.backToOrder.setOnAction(e -> {
            try {
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
