package com.brewandbite.max;

public class Muffin extends Pastry {

    /**
     * The available muffin types.
     */
    public enum MuffinType {
        BLUEBERRY,
        CHOCOLATE_CHIP
    }

    private final MuffinType muffinType;

    public Muffin(int id, MuffinType type) {
        super(
                id,
                type == MuffinType.CHOCOLATE_CHIP ? "Chocolate-Chip Muffin" : "Blueberry Muffin",
                (type == MuffinType.BLUEBERRY ? 2.00 : 2.25),
                (type == MuffinType.CHOCOLATE_CHIP ? "Chocolate-Chip Muffin" : "Blueberry Muffin")
        );
        this.muffinType = type;
    }

    public MuffinType getType() {
        return muffinType;
    }
}
