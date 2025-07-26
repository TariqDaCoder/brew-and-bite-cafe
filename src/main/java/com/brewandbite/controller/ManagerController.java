package com.brewandbite.controller;

import java.util.List;

import com.brewandbite.model.inventory.Ingredient;
import com.brewandbite.model.inventory.ObservableInventory;
import com.brewandbite.model.items.MenuItem;
import com.brewandbite.util.DataManager;
import com.brewandbite.util.Observer;
import com.brewandbite.views.ManagerView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller for manager to oversee menu and inventory.
 */
public class ManagerController implements Observer<List<Ingredient>> {

    private final ManagerView view;
    private final ObservableList<MenuItem> menu;
    private final ObservableInventory inventory;
    private static final String MENU_JSON = "menu.json";
    private static final String INVENTORY_JSON = "inventory.json";

    public ManagerController(ManagerView view, ObservableInventory inventory) {
        this.view = view;
        this.inventory = inventory;
        inventory.addObserver(this);

        // Load menu from JSON via DataManager
        List<MenuItem> loadedMenu = DataManager.loadAllMenuItems();
        this.menu = FXCollections.observableArrayList(loadedMenu);
    }

    @Override
    public void update(List<Ingredient> updatedInventory) {
        view.inventoryList.getItems().setAll(updatedInventory);
        view.inventoryList.refresh();
    }

    /**
     * UI event handlers
     */
    public void initialize() {
        view.menuEditor.setItems(menu);
        view.inventoryList.setItems(FXCollections.observableArrayList(inventory.getAllIngredients()));

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
                    inventory.addStock(ingredient.getName(), 5); // Use ObservableInventory method
                    DataManager.saveToJson(INVENTORY_JSON, inventory.getAllIngredients());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
