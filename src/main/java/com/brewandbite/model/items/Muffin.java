package com.brewandbite.model.items;

/**
 * A specific pastry variation: Muffin (Blueberry or Chocolate-Chip).
 */
public class Muffin extends Pastry {

    /**
     * The available muffin types.
     */
    public enum MuffinType {
        BLUEBERRY, CHOCOLATE_CHIP
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
                (type == MuffinType.CHOCOLATE_CHIP ? "Chocolate-Chip Muffin" : "Blueberry Muffin"),
                // Base price: e.g., $2.25 for chocolate-chip, $2.00 for blueberry
                (type == MuffinType.BLUEBERRY ? 2.00 : 2.25),
                // Description
                (type == MuffinType.CHOCOLATE_CHIP ? "Chocolate-Chip Muffin" : "Blueberry Muffin")
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
