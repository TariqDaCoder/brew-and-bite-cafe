package com.brewandbite.model.items;

//coffee beverage base class
public class Coffee extends Beverage {
    public enum CoffeeType {
        BLACK(2.50, "A Basic Black Coffee"), 
        LATTE(2.25, "A Latte"),
        ESPRESSO(2.50, "A shot of espresso"),
        CAPPUCCINO(2.50, "A Cappuccino");

        @Override
        public String toString() {
            String coffeeType = " ";
            switch(this) {
                case BLACK:
                    coffeeType = "Black Coffee";
                break;
                case LATTE:
                    coffeeType = "Latte";
                break;
                case ESPRESSO:
                    coffeeType = "Espresso";
                break;
                case CAPPUCCINO:
                    coffeeType = "Cappuccino";
                break;
                default:
                break;
            }

            return coffeeType;
        }
        
        private final double cost;
        private final String description;

        CoffeeType(double cost, String description) {
            this.cost = cost;
            this.description = description;
        }

        public double getCost() {
            return this.cost;
        }

        public String getDescription() {
            return this.description;
        }
    }

    private final CoffeeType type;

    public Coffee(int id, CoffeeType type) {
        super(
                id,
                // Display name
                type.toString(),
                // Base price based on type
                type.getCost(),
                // Description
                type.getDescription()
        );
        this.type = type;
    }

    public CoffeeType getType() {
        return this.type;
    }
}
