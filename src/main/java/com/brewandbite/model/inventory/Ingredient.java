package com.brewandbite.model.inventory;

public class Ingredient {

    private String name;
    private int quantity;
    private String unit; // "grams", "ml", "pieces"

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void reduceQuantity(int amount) {
        this.quantity = this.quantity - amount;
    }

    @Override
    public String toString() {
        return "Ingredient{"
                + "name='" + name + '\''
                + ", quantity=" + quantity
                + ", unit='" + unit + '\''
                + '}';
    }
}
