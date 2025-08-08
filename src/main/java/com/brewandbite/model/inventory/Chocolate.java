package com.brewandbite.model.inventory;

public class Chocolate extends Ingredient{
    public Chocolate(int quantity) {
        super("Chocolate", quantity, IngredientUnit.GRAMS);
    }
}