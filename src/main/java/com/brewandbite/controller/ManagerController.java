package com.brewandbite.controller;

import java.util.List;

import com.brewandbite.model.inventory.Ingredient;
import com.brewandbite.model.items.MenuItem;
import com.brewandbite.util.DataManager;
import com.brewandbite.views.ManagerView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller for manager to oversee menu and inventory.
 */
public class ManagerController {

    private final ManagerView view;
    private final ObservableList<MenuItem> menu;
    private final ObservableList<Ingredient> inventory;
    private static final String MENU_JSON = "menu.json";
    private static final String INVENTORY_JSON = "inventory.json";

    public ManagerController(ManagerView view) {
        this.view = view;

        // Load menu and inventory from JSON via DataManager
        List<MenuItem> loadedMenu = DataManager.loadAllMenuItems();
        List<Ingredient> loadedInv = DataManager.loadAllIngredients();
        this.menu = FXCollections.observableArrayList(loadedMenu);
        this.inventory = FXCollections.observableArrayList(loadedInv);

    }

    /**
     * UI event handlers
     */
    public void initialize() {
        view.menuEditor.setItems(menu);
        view.inventoryList.setItems(inventory);

        // Add a new menu item and save
        view.addItem.setOnAction(e -> {
            try {
                // TODO: Show dialog to add new MenuItem, then after creation, add to menu
                // menu.add(newItem);
                DataManager.saveToJson(MENU_JSON, menu);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Remove selected Item and save
        view.removeItem.setOnAction(e -> {
            try {
                MenuItem sel = view.menuEditor.getSelectionModel().getSelectedItem();
                if (sel != null) {
                    menu.remove(sel);
                    DataManager.saveToJson(MENU_JSON, menu);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Restock selected ingredient and save inventory
        view.restockBtn.setOnAction(e -> {
            try {
                Ingredient ingredient = view.inventoryList.getSelectionModel().getSelectedItem();
                if (ingredient != null) {
                    ingredient.addStock(5);
                }
                view.inventoryList.refresh();
                DataManager.saveToJson(INVENTORY_JSON, inventory);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
