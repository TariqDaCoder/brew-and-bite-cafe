package com.brewandbite.model.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.brewandbite.util.Customizable;

public class Beverage extends MenuItem implements Customizable{
    private Customization drinkSize;
    public List<Customization> customizationsOnBeverage = new ArrayList<>(); // This will be the lists of addons for customer's drinks

    public Beverage(int id,
            String name,
            double basePrice,
            String description) {
        super(id, name, basePrice, description);
        this.drinkSize = Customization.SMALL;
        //this.fullNameWithCustomizations = name;
    }

    //for customer view, so they can see the item name and the current
    //customizations on it
    public String nameWithCustomizationsToString() {
        String retString = "";

        retString = this.getItemName() + "\n" + this.getDrinkSize() + "\n";
        for (Customization customizationType : customizationsOnBeverage) {
            retString = retString + customizationType.toString() + "\n";
        }
        
        return retString;
    }

    public Customization getDrinkSize() {
        return drinkSize;
    }

    public void setDrinkSize(Customization drinkSize) {
        this.drinkSize = drinkSize;
    }

    /**
     * Customization add on
     */
    public void addCustomization(Customization customAddOn) {
        customAddOn.applyCustomization(this);
    }

    public void removeCustomization(Customization customAddOn) {
        customAddOn.removeCustomization(this);
    }

    /**
     * Read‑only view of customizations
     */
    public List<Customization> getCustomizations() {
        return Collections.unmodifiableList(customizationsOnBeverage);
    }

    /**
     * calculate price
     */
    @Override
    public double calculatePrice() {
        double total = getBasePrice();

        total += drinkSize.getCustomizationCost(); // Add drink size on top of base price

        for (Customization customAddOn : customizationsOnBeverage) {
            total += customAddOn.getCustomizationCost(); // add up each add on from customizations list
        }

        return total;
    }
}
