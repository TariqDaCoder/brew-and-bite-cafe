package com.brewandbite.model.items;

public class Tea extends Beverage {
    public enum TeaType {
        BLACK(2.50, "A black tea"), 
        GREEN(2.25, "A green tea"),
        HERBAL(2.50, "A herbal tea");

        @Override
        public String toString() {
            String teaType = " ";
            switch(this) {
                case BLACK:
                    teaType = "Black Tea";
                break;
                case GREEN:
                    teaType = "Green Tea";
                break;
                case HERBAL:
                    teaType = "Herbal Tea";
                break;
                default:
                break;
            }

            return teaType;
        }
        
        private final double cost;
        private final String description;

        TeaType(double cost, String description) {
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

    private final TeaType type;

    public Tea(int id, TeaType type) {
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

    public TeaType getType() {
        return this.type;
    }
}
