package com.brewandbite.views;

import com.brewandbite.model.inventory.Ingredient;
import com.brewandbite.model.items.MenuItem;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ManagerView extends VBox {

    public final ListView<MenuItem> menuEditor = new ListView<>();
    public final Button addItem = new Button("Add Item");
    public final Button removeItem = new Button("Remove Item");
    public final Button switchRoleButton = new Button("Switch Roles");
    public final HBox menuButtons = new HBox(10, addItem, removeItem);
    public final ListView<Ingredient> inventoryList = new ListView<>();
    public final Button restockBtn = new Button("Restock (+5)");
    private static final String MENU_MANAGEMENT_LABEL = "Menu Management";
    private static final String INVENTORY_MANAGEMENT_LABEL = "Inventory Management";

    public ManagerView() {
        setSpacing(15);
        setPadding(new Insets(10));

        Label menuHeader = new Label(MENU_MANAGEMENT_LABEL);
        Label inventoryHeader = new Label(INVENTORY_MANAGEMENT_LABEL);
        Separator separator = new Separator();

        // Custom cell factory for menu items
        menuEditor.setCellFactory(listView -> new ListCell<MenuItem>() {
            @Override
            protected void updateItem(MenuItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Format: "Name - $Price (ID: 1)"
                    setText(String.format("%s - $%.2f (ID: %d)",
                            item.getName(),
                            item.getBasePrice(),
                            item.getId()));
                }
            }
        });

        // Custom cell factory for ingredients
        inventoryList.setCellFactory(listView -> new ListCell<Ingredient>() {
            @Override
            protected void updateItem(Ingredient ingredient, boolean empty) {
                super.updateItem(ingredient, empty);
                if (empty || ingredient == null) {
                    setText(null);
                } else {
                    // Format: "Coffee Beans: 1000 grams"
                    setText(String.format("%s: %d %s",
                            ingredient.getName(),
                            ingredient.getQuantity(),
                            ingredient.getUnit()));
                }
            }
        });

        getChildren().addAll(
                menuHeader,
                menuEditor,
                menuButtons,
                separator,
                inventoryHeader,
                inventoryList,
                restockBtn,
                switchRoleButton
        );
    }
}