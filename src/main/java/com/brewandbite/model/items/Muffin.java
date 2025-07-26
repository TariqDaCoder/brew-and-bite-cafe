package com.brewandbite.model.items;

/**
 * A specific pastry variation: Muffin (Blueberry or Chocolate-Chip).
 */
public class Muffin extends Pastry {

    /**
     * The available muffin types.
     */
    public enum MuffinType {
        BLUEBERRY(2.00, "Blueberry Muffin"), CHOCOLATE_CHIP(2.25, "Chocolate-Chip Muffin");

        @Override
        public String toString() {
            String cookieTypeString = " ";
            switch(this) {
                case BLUEBERRY:
                    cookieTypeString = "Blueberry Muffin";
                break;
                case CHOCOLATE_CHIP:
                    cookieTypeString = "Chocolate-Chip Muffin";
                break;
                default:
                break;
            }

            return cookieTypeString;
        }
        
        private final double cost;
        private final String description;

        MuffinType(double cost, String description) {
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

    private final MuffinType type;

    /**
     * Constructs a Muffin with the given id and type.
     *
     * @param id unique identifier
     * @param type variation of muffin
     */
    public Muffin(int id, MuffinType type) {
        super(
                id,
                // Display name based on type
                type.toString(),
                // Base price: e.g., $2.25 for chocolate-chip, $2.00 for blueberry
                type.getCost(),
                // Description
                type.getDescription()
        );
        this.type = type;
    }

    /**
     * @return the muffin variation type
     */
    public MuffinType getType() {
        return type;
    }
}
