package com.brewandbite.controller;
import com.brewandbite.model.items.Beverage;
import com.brewandbite.model.items.MenuItem;
import com.brewandbite.util.Customizable.Customization;
import com.brewandbite.views.CustomizationView;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.stage.Stage;

public class CustomizationController {
    public final ListView<MenuItem> customerViewCartList;
    public final CustomizationView customizationView;

    public CustomizationController(CustomizationView customizationView, ListView<MenuItem> customerCartList) {
        this.customizationView = customizationView;
        this.customerViewCartList = customerCartList;
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
                //update customizationView with the new customizations
                int indexOfChangedItem = 0;
                ObservableList<MenuItem> copy = customerViewCartList.getItems();
                for(int i = 0; i < copy.size(); i++) {
                    if(copy.get(i) == customizationView.selectedBeverage) {
                        indexOfChangedItem = i;
                        customerViewCartList.getItems().set(indexOfChangedItem, customizationView.selectedBeverage);
                        break;
                    }
                }
                
                
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
