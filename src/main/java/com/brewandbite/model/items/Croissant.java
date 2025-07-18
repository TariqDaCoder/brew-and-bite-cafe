package com.brewandbite.model.items;

/**
 * A specific pastry variation: Croissant (Butter or Chocolate).
 */
public class Croissant extends Pastry {

    /**
     * The available croissant types.
     */
    public enum CroissantType {
        BUTTER, CHOCOLATE
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
                type == CroissantType.BUTTER ? "Butter Croissant" : "Chocolate Croissant",
                // Base price based on type
                type == CroissantType.BUTTER ? 2.00 : 2.25,
                // Description
                (type == CroissantType.BUTTER ? "Butter Croissant" : "Chocolate Croissant")
        );
        this.type = type;
    }

    /**
     * @return the croissant variation type
     */
    public CroissantType getType() {
        return type;
    }
}
