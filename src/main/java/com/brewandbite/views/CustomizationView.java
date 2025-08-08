package com.brewandbite.views;
import com.brewandbite.controller.CustomizationController;
import com.brewandbite.model.items.MenuItem;
import com.brewandbite.model.items.Beverage;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomizationView extends VBox {
    public Scene customerView;
    public Beverage selectedBeverage;
    public final ListView<Beverage.Customization> possibleItemCustomizations = new ListView<>();
    public final ListView<MenuItem> selectedItem = new ListView<>();
    public final Button addCustomizationToItem = new Button("Add Customization");
    public final Button removeCustomizationFromItem = new Button("Remove Customization from Item");
    public final Button backToOrder = new Button("Back To Order"); // Move items into cartlist, not actually submit it
    public final HBox customizationButtons = new HBox(10, addCustomizationToItem, removeCustomizationFromItem, backToOrder);
    private static final String CUSTOMIZATION_MENU_LABEL = "Customizations";
    private static final String SELECTED_ITEM_LABEL = "Your Item";

    public CustomizationView() {
        //wire up the controller to this view
        CustomizationController customizationController = new CustomizationController(this);
        customizationController.initialize();

        setSpacing(15);
        setPadding(new Insets(10));

        Label customizationLabel = new Label(CUSTOMIZATION_MENU_LABEL);
        Label selectedItemLabel = new Label(SELECTED_ITEM_LABEL);

        //initialize the list of possible customizations
        for (Beverage.Customization customization : Beverage.Customization.values()) {
            possibleItemCustomizations.getItems().add(customization);
        }

        //make it so the user just sees the name (and customizations if applicable) of the menuItem
        //not the overridden toString() method
        this.selectedItem.setCellFactory(param -> new ListCell<MenuItem>() {
            @Override
            protected void updateItem(MenuItem menuItem, boolean empty) {
                    super.updateItem(menuItem, empty);
                    if (empty || menuItem == null || menuItem.toString() == null) {
                        setText(null);
                    } else {
                        setText(selectedBeverage.getItemName() + "\n" + selectedBeverage.customizationsToString());
                    }
            }
        });
        
        this.possibleItemCustomizations.setCellFactory(param -> new ListCell<Beverage.Customization>() {
            @Override
            protected void updateItem(Beverage.Customization customization, boolean empty) {
                    super.updateItem(customization, empty);
                    if (empty || customization == null || customization.toString() == null) {
                        setText(null);
                    } else {
                        setText(customization.toString());
                    }
            }
        });

        getChildren().addAll(
            customizationLabel,
            possibleItemCustomizations,
            selectedItemLabel,
                selectedItem,
                customizationButtons
        );
    }
}
