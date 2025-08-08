package com.brewandbite.model.inventory;

public class Butter extends Ingredient{
    public Butter(int quantity) {
        super("Butter", quantity, IngredientUnit.GRAMS);
    }
}