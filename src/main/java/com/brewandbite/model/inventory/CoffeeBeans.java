package com.brewandbite.model.inventory;

public class CoffeeBeans extends Ingredient {
    public CoffeeBeans(int quantity) {
        super("Coffee Beans", quantity, IngredientUnit.GRAMS);
    }
}