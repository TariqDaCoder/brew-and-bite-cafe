package com.brewandbite.util;

import com.brewandbite.model.items.Beverage;

//Group and abstract customization.
//Drink size and add ons can both be
//considered a customization
public interface Customizable {
    public enum CustomizationType {
        SIZE,
        CUSTOMIZATION;
    }

    /**
     * Cusomization types with their price (AKA Add ons)
    */
    public enum Customization {
        SMALL(2.50f, CustomizationType.SIZE),
        MEDIUM(3.50f, CustomizationType.SIZE),
        LARGE(4.50f, CustomizationType.SIZE),
        EXTRA_SHOT(0.75f, CustomizationType.CUSTOMIZATION),
        DECAF(0.25f, CustomizationType.CUSTOMIZATION),
        OAT_MILK(0.50f, CustomizationType.CUSTOMIZATION),
        ALMOND_MILK(0.50f, CustomizationType.CUSTOMIZATION),
        SUGAR_FREE_SYRUP(0.30f, CustomizationType.CUSTOMIZATION);

        private final double cost;
        private final CustomizationType type;

        Customization(double cost, CustomizationType type) {
            this.cost = cost;
            this.type = type;
        }

        public double getCustomizationCost() {
            return this.cost;
        }

        public CustomizationType getType() {
            return this.type;
        }

        public void applyCustomization(Beverage beverage) {
            switch(this.getType()) {
                case CustomizationType.SIZE:
                    beverage.setDrinkSize(this);
                break;
                case CustomizationType.CUSTOMIZATION:
                    beverage.customizationsOnBeverage.add(this); // Added to lists of customizations
                break;
                default:
                break;
            }
        }
        
        public void removeCustomization(Beverage beverage) {
            switch(this.getType()) {
                case CustomizationType.CUSTOMIZATION:
                    beverage.customizationsOnBeverage.remove(this); // Added to lists of customizations
                break;
                default:
                break;
            }
        }

        @Override
        public String toString() {
            String customizationToString = " ";
            switch(this) {
                case EXTRA_SHOT:
                    customizationToString = "Extra Shot";
                break;
                case DECAF:
                    customizationToString = "Decaf";
                break;     
                case OAT_MILK:
                    customizationToString = "Oat Milk";
                break;       
                case ALMOND_MILK:
                    customizationToString = "Almond Milk";
                break;
                case SUGAR_FREE_SYRUP:
                    customizationToString = "Sugar Free Syrup";
                break;
                case SMALL:
                    customizationToString = "Small";
                break;
                case MEDIUM:
                    customizationToString = "Medium";
                break;     
                case LARGE:
                    customizationToString = "Large";
                break;  
                default:
                break;
            }

            return customizationToString;
        }
    }
}