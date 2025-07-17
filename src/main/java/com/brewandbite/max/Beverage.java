package com.brewandbite.max;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Beverage extends MenuItem {

    /**
     * Drink Size enum with their own surcharge
     */
    public enum DrinkSize {
        SMALL(2.50f),
        MEDIUM(3.50f),
        LARGE(4.50f);

        private final double surcharge;

        DrinkSize(double surcharge) {
            this.surcharge = surcharge;
        }

        public double getSurcharge() {
            return surcharge;
        }
    }

    /**
     * Cusomization types with their price (AKA Add ons)
     */
    public enum CustomizationType {
        EXTRA_SHOT(0.75f),
        DECAF(0.25f),
        OAT_MILK(0.50f),
        ALMOND_MILK(0.50f),
        SUGAR_FREE_SYRUP(0.30f);

        private final double cost;

        CustomizationType(double cost) {
            this.cost = cost;
        }

        public double getCustomizationCost() {
            return cost;
        }
    }

    private DrinkSize drinkSize;
    private final List<CustomizationType> customizationsOnBeverage = new ArrayList<>(); // This will be the lists of addons for customer's drinks

    public Beverage(int id,
            String name,
            double basePrice,
            String description,
            DrinkSize size) {
        super(id, name, basePrice, description);
        this.drinkSize = size;
    }

    public DrinkSize getDrinkSize() {
        return drinkSize;
    }

    public void setDrinkSize(DrinkSize drinkSize) {
        this.drinkSize = drinkSize;
    }

    /**
     * Customization add on
     */
    public void addCustomization(CustomizationType customAddOn) {
        customizationsOnBeverage.add(customAddOn); // Added to lists of customizations
    }

    /**
     * Read‑only view of customizations
     */
    public List<CustomizationType> getCustomizations() {
        return Collections.unmodifiableList(customizationsOnBeverage);
    }

    /**
     * calculate price
     */
    @Override
    public double calculatePrice() {
        double total = getBasePrice();

        total += drinkSize.getSurcharge(); // Add drink size on top of base price

        for (CustomizationType customAddOn : customizationsOnBeverage) {
            total += customAddOn.getCustomizationCost(); // add up each add on from customizations list
        }

        return total;
    }
}
