package com.brewandbite.model.inventory;

import java.util.ArrayList;
import java.util.List;

import com.brewandbite.util.Observable;

/**
 * ObservableInventory is a class that extends Observable to manage a list of
 * ingredients. It allows observers to be notified when the inventory changes,
 * such as when stock is added or when the inventory is updated.
 */
public class ObservableInventory extends Observable<List<Ingredient>> {

    private final List<Ingredient> inventory = new ArrayList<>();

    /**
     * Default constructor initializes with an empty inventory. This can be used
     * to create an empty observable inventory.
     *
     * @param initial the initial list of ingredients
     */
    public ObservableInventory(List<Ingredient> initial) {
        inventory.addAll(initial);
    }

    /**
     * Adds a new ingredient to the inventory.
     *
     * @param ingredient the ingredient to add
     */
    public void addStock(String ingredientName, int ingredientAmount) {
        for (Ingredient ingredient : inventory) {
            // Check if the ingredient name matches, case-insensitive comparison
            if (ingredient.getName().equals(ingredientName)) {
                // Add stock to the ingredient
                ingredient.addStock(ingredientAmount);
                break; // Exit loop after updating the ingredient
            }
        }
        notifyObservers(List.copyOf(inventory)); // Notify observers about the change
    }

    /**
     * Get all ingredients in the inventory, by returning an unmodifiable list
     *
     * @return an unmodifiable list of ingredients
     */
    public List<Ingredient> getAllIngredients() {
        return List.copyOf(inventory);
    }

}
