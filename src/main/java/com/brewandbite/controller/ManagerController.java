package com.brewandbite.controller;

import java.util.List;

import com.brewandbite.model.inventory.Ingredient;
import com.brewandbite.model.items.MenuItem;
import com.brewandbite.util.JsonRepository;
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
    private final JsonRepository<MenuItem> menuRepo;
    private final JsonRepository<Ingredient> invRepo;

    public ManagerController(ManagerView view) {
        this.view = view;
        this.menuRepo = new JsonRepository<>(MenuItem.class);
        this.invRepo = new JsonRepository<>(Ingredient.class);

        // Load data with exception handling
        ObservableList<MenuItem> tempMenu;
        ObservableList<Ingredient> tempInv;
        try {
            List<MenuItem> loadedMenu = menuRepo.load("data/menu.json");
            tempMenu = FXCollections.observableArrayList(loadedMenu);
        } catch (Exception ex) {
            ex.printStackTrace();
            tempMenu = FXCollections.observableArrayList();
        }
        try {
            List<Ingredient> loadedInv = invRepo.load("data/inventory.json");
            tempInv = FXCollections.observableArrayList(loadedInv);
        } catch (Exception ex) {
            ex.printStackTrace();
            tempInv = FXCollections.observableArrayList();
        }
        this.menu = tempMenu;
        this.inventory = tempInv;
    }

    /**
     * UI event handlers
     */
    public void initialize() {
        view.menuEditor.setItems(menu);
        view.inventoryList.setItems(inventory);

        view.addItem.setOnAction(e -> {
            try {
                // TODO: Show dialog to add new MenuItem, then:
                // menu.add(newItem);
                // menuRepo.save("data/menu.json", menu);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.removeItem.setOnAction(e -> {
            try {
                MenuItem sel = view.menuEditor.getSelectionModel().getSelectedItem();
                if (sel != null) {
                    menu.remove(sel);
                    menuRepo.save("data/menu.json", menu);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.restockBtn.setOnAction(e -> {
            try {
                Ingredient ingredient = view.inventoryList.getSelectionModel().getSelectedItem();
                if (ingredient != null) {
                    ingredient.addStock(5);
                }
                view.inventoryList.refresh();
                invRepo.save("data/inventory.json", inventory);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
