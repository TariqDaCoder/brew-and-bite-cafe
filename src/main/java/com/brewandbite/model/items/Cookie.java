package com.brewandbite.model.items;

/**
 * A specific pastry variation: Cookie (Chocolate-Chip or Oatmeal-Raisin).
 */
public class Cookie extends Pastry {

    /**
     * The available cookie types.
     */
    public enum CookieType {
        CHOCOLATE_CHIP(1.75, "Chocolate-Chip Cookie"), OATMEAL_RAISIN(1.75, "Oatmeal-Raisin Cookie");

        @Override
        public String toString() {
            String cookieTypeString = " ";
            switch(this) {
                case CHOCOLATE_CHIP:
                    cookieTypeString = "Chocolate-Chip";
                break;
                case OATMEAL_RAISIN:
                    cookieTypeString = "Oatmeal-Raisin";
                break;
                default:
                break;
            }

            return cookieTypeString;
        }
        
        private final double cost;
        private final String description;

        CookieType(double cost, String description) {
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

    private final CookieType type;

    /**
     * Constructs a Cookie with the given id and type.
     *
     * @param id unique identifier
     * @param type variation of cookie
     */
    public Cookie(int id, CookieType type) {
        super(
                id,
                // Display name based on type
                type.toString(),
                // Base price: flat $1.75
                type.getCost(),
                // Description
                type.getDescription()
        );
        this.type = type;
    }

    /**
     * @return the cookie variation type
     */
    public CookieType getType() {
        return type;
    }
}
