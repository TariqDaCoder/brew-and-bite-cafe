package com.brewandbite.model.items;

import com.brewandbite.model.inventory.Butter;
import com.brewandbite.model.inventory.Flour;

/**
 * A specific pastry variation: Croissant (Butter or Chocolate).
 */
public class Croissant extends Pastry {
    /**
     * The available croissant types.
     */
    public enum CroissantType {
        BUTTER(2.00, "Butter Croissant"), CHOCOLATE(2.25, "Chocolate Croissant");

        @Override
        public String toString() {
            String croissantTypeString = " ";
            switch(this) {
                case BUTTER:
                    croissantTypeString = "Butter";
                break;
                case CHOCOLATE:
                    croissantTypeString = "Chocolate";
                break;
                default:
                break;
            }

            return croissantTypeString;
        }
        
        private final double cost;
        private final String description;

        CroissantType(double cost, String description) {
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

    private final CroissantType type;

    /**
     * Constructs a Croissant with the given id and type.
     *
     * @param id unique identifier
     * @param type variation of croissant
     */
    public Croissant(int id, CroissantType type) {
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
        
        this.requiredIngredients.add(new Flour(10));
        this.requiredIngredients.add(new Butter(5));
    }

    /**
     * @return the croissant variation type
     */
    public CroissantType getType() {
        return type;
    }
}
