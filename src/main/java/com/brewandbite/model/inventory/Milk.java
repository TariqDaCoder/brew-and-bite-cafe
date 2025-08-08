package com.brewandbite.model.inventory;

public class Milk extends Ingredient{
    public Milk(int quantity) {
        super("Milk", quantity, IngredientUnit.ML);
    }
}
