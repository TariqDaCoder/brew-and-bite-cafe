package com.brewandbite.max;
import java.util.*;

class Size {
    public enum DrinkSizes {SMALL, MEDIUM, LARGE};
    DrinkSizes sizeOfDrink;
    float price;//price of size
};

class Customization {
    public enum CustomizationType {EXTRA_SHOT, DECAF, OAT_MILK, ALMOND_MILK, SUGAR_FREE_SYRUP};
    CustomizationType customizationType;
    float price;
};

public class Beverage extends MenuItem {
    ArrayList<Customization> customizationsOnBeverage = new ArrayList<Customization>();
    Size size;

    public Beverage(float price, String itemName, Size size) {
        super(price, itemName);
        this.size = size;
    }

    @Override
    float calculatePrice() {
        float totalPrice = 0.0f;

        //add on all the customizations
        for (Customization customization : this.customizationsOnBeverage) {
            totalPrice += customization.price;
        }

        totalPrice += size.price;

        return totalPrice;
    }
}