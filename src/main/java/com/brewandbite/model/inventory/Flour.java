package com.brewandbite.model.inventory;

public class Flour extends Ingredient{
    public Flour(int quantity) {
        super("Flour", quantity, IngredientUnit.GRAMS);
    }
}