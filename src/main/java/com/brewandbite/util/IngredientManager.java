package com.brewandbite.util;

import java.util.ArrayList;
import java.util.List;

import com.brewandbite.model.inventory.Butter;
import com.brewandbite.model.inventory.Chocolate;
import com.brewandbite.model.inventory.CoffeeBeans;
import com.brewandbite.model.inventory.Flour;
import com.brewandbite.model.inventory.Ingredient;
import com.brewandbite.model.inventory.Milk;

//IngredientManagers handles functionality
//related to monitoring and changing ingredient
//levels in the system
public class IngredientManager {
    static CoffeeBeans coffeeInSystem;
    static Flour flourInSystem;
    static Milk milkInSystem;
    static Chocolate chocolateInSystem;
    static Butter butterInSystem;

    public IngredientManager(List<Ingredient> ingredients) {
        for (Ingredient ingredient: ingredients) {
            if (ingredient instanceof CoffeeBeans) {
                coffeeInSystem = (CoffeeBeans)ingredient;
            } else if (ingredient instanceof Flour) {
                flourInSystem = (Flour)ingredient;
            } else if (ingredient instanceof Milk) {
                milkInSystem = (Milk)ingredient;
            } else if (ingredient instanceof Chocolate) {
                chocolateInSystem = (Chocolate)ingredient;
            } else if (ingredient instanceof Butter) {
                butterInSystem = (Butter)ingredient;
            }
        }
    }

    //checks that we have a sufficient quantities of ingredients
    //for the given list
    public Boolean verifyWeHaveIngredients(ArrayList<Ingredient> ingredients) {
        Boolean weHaveEnoughOfAllIngredients = true;

        Boolean sufficientQuantityForIngredient = false;
        for (Ingredient ingredient : ingredients) {
            sufficientQuantityForIngredient = checkIfWeHaveQuantity(ingredient);
            
            if(!sufficientQuantityForIngredient) {
                weHaveEnoughOfAllIngredients = false;
                break;
            }
        }

        return weHaveEnoughOfAllIngredients;
    }

    public Boolean checkIfWeHaveQuantity(Ingredient ingredientToCheck) {
        Boolean sufficentQuantity = false;
        if (ingredientToCheck instanceof CoffeeBeans) {
            if (ingredientToCheck.getQuantity() <= coffeeInSystem.getQuantity()) {
                sufficentQuantity = true;
            }
        } else if (ingredientToCheck instanceof Flour) {
            if (ingredientToCheck.getQuantity() <= flourInSystem.getQuantity()) {
                sufficentQuantity = true;
            }
        } else if (ingredientToCheck instanceof Milk) {
            if (ingredientToCheck.getQuantity() <= milkInSystem.getQuantity()) {
                sufficentQuantity = true;
            }
        } else if (ingredientToCheck instanceof Chocolate) {
            if (ingredientToCheck.getQuantity() <= chocolateInSystem.getQuantity()) {
                sufficentQuantity = true;
            }
        } else if (ingredientToCheck instanceof Butter) {
            if (ingredientToCheck.getQuantity() <= butterInSystem.getQuantity()) {
                sufficentQuantity = true;
            }
        }

        return sufficentQuantity;
    }

    public void removeQuantityOfIngredientsFromSystem(ArrayList<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            removeQuantityOfIngredientToSystem(ingredient, ingredient.getQuantity());
        }
    }

    public void addQuantityOfIngredientsFromSystem(ArrayList<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            addQuantityOfIngredientToSystem(ingredient, ingredient.getQuantity());
        }
    }

    public void addQuantityOfIngredientToSystem(Ingredient ingredient, int quantity) {
        if (ingredient instanceof CoffeeBeans) {
            coffeeInSystem.addStock(quantity);
        } else if (ingredient instanceof Flour) {
            flourInSystem.addStock(quantity);
        } else if (ingredient instanceof Milk) {
            milkInSystem.addStock(quantity);
        } else if (ingredient instanceof Chocolate) {
            chocolateInSystem.addStock(quantity);
        } else if (ingredient instanceof Butter) {
            butterInSystem.addStock(quantity);
        }
    }

    void removeQuantityOfIngredientToSystem(Ingredient ingredient, int quantity) {
        if (ingredient instanceof CoffeeBeans) {
            coffeeInSystem.reduceQuantity(quantity);
        } else if (ingredient instanceof Flour) {
            flourInSystem.reduceQuantity(quantity);
        } else if (ingredient instanceof Milk) {
            milkInSystem.reduceQuantity(quantity);
        } else if (ingredient instanceof Chocolate) {
            chocolateInSystem.reduceQuantity(quantity);
        } else if (ingredient instanceof Butter) {
            butterInSystem.reduceQuantity(quantity);
        }
    }

    public void printAllIngredientsInSystem() {
        System.out.println(this.coffeeInSystem.toString());
        System.out.println(this.flourInSystem.toString());
        System.out.println(this.milkInSystem.toString());
        System.out.println(this.chocolateInSystem.toString());
        System.out.println(this.butterInSystem.toString());
    }
}
