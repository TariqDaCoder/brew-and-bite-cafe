package com.brewandbite.views;

import com.brewandbite.model.inventory.Ingredient;
import com.brewandbite.model.items.MenuItem;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ManagerView extends VBox {

    public final ListView<MenuItem> menuEditor = new ListView<>();
    public final Button addItem = new Button("Add Item");
    public final Button removeItem = new Button("Remove Item");
    public final HBox menuButtons = new HBox(10, addItem, removeItem);
    public final ListView<Ingredient> inventoryList = new ListView<>();
    public final Button restockBtn = new Button("Restock");
    private static final String MENU_MANAGEMENT_LABEL = "Menu Management";
    private static final String INVENTORY_MANAGEMENT_LABEL = "Inventory Mangement";

    public ManagerView() {
        setSpacing(15);
        setPadding(new Insets(10));

        Label menuHeader = new Label(MENU_MANAGEMENT_LABEL);
        Label inventoryHeader = new Label(INVENTORY_MANAGEMENT_LABEL);
        Separator separator = new Separator();

        getChildren().addAll(
                menuHeader,
                menuEditor,
                menuButtons,
                separator,
                inventoryHeader,
                inventoryList,
                restockBtn
        );
    }
}
