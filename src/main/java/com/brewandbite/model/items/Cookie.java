package com.brewandbite.model.items;

/**
 * A specific pastry variation: Cookie (Chocolate-Chip or Oatmeal-Raisin).
 */
public class Cookie extends Pastry {

    /**
     * The available cookie types.
     */
    public enum CookieType {
        CHOCOLATE_CHIP, OATMEAL_RAISIN
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
                (type == CookieType.CHOCOLATE_CHIP ? "Chocolate-Chip Cookie" : "Oatmeal-Raisin Cookie"),
                // Base price: flat $1.75
                1.75,
                // Description
                (type == CookieType.CHOCOLATE_CHIP ? "Chocolate-Chip Cookie" : "Oatmeal-Raisin Cookie")
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
