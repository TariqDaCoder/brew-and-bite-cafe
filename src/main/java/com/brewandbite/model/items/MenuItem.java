package com.brewandbite.model.items;
import java.util.ArrayList;

import com.brewandbite.model.inventory.Ingredient;

public abstract class MenuItem {
    //total price for the MenuItem
    private int id;
    private double basePrice;
    private String itemName;
    private String description;

    //need to incorporate the required ingredients; then we can check and
    //reduce them from the system based on what ingredients are
    //required in a MenuItem
    //E.g butter croissant requires some quantity of butter and flour
    //if we put it here we can check if we have the required ingredients
    protected ArrayList<Ingredient> requiredIngredients;

    /**
     * @param id unique identifier for this item
     * @param name display name
     * @param basePrice base price (before any size or customization surcharges)
     * @param description short description for manager/menu use
     */
    public MenuItem(int id, String name, double basePrice, String description) {
        this.id = id;
        this.itemName = name;
        this.basePrice = basePrice;
        this.description = description;
        this.requiredIngredients = new ArrayList<>();
    }

    //each menu item must implement this,
    //abstract because the way we compute the
    //total price of different menu items can vary
    public abstract double calculatePrice();

    // Getters
    public int getId() {
        return id;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Ingredient> getRequiredIngredients() {
        return this.requiredIngredients;
    }

    // Setters
    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuItem that)) {
            return false;
        }
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "MenuItem{"
                + "basePrice=" + basePrice
                + ", id=" + id
                + ", name='" + itemName + '\''
                + ", description='" + description + '\''
                + '}';
    }

}
