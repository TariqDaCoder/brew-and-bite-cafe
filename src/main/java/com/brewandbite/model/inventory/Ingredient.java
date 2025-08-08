package com.brewandbite.model.inventory;

public abstract class Ingredient {
    private String name;
    private int quantity;
    private IngredientUnit unit; // "grams", "ml", "pieces"
    protected enum IngredientUnit {GRAMS, ML, PIECES};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public IngredientUnit getUnit() {
        return unit;
    }

    public void setUnit(IngredientUnit unit) {
        this.unit = unit;
    }

    public void reduceQuantity(int amount) {
        this.quantity = this.quantity - amount;
    }

    public void addStock(int amountToAdd) {
        this.quantity += amountToAdd;
    }

    @Override
    public String toString() {
        return "Ingredient{"
                + "name='" + this.name + '\''
                + ", quantity=" + this.quantity
                + ", unit='" + this.unit + '\''
                + '}';
    }

    Ingredient(String name, int quantity, IngredientUnit unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }
}
