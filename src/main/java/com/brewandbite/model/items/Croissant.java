package com.brewandbite.model.items;

public class Croissant extends Pastry {

    public enum CroissantType {
        BUTTER, CHOCOLATE
    }
    private final CroissantType croissantType;

    public Croissant(int id, CroissantType croissantType) {
        super(
                id,
                croissantType.name().charAt(0) + croissantType.name().substring(1).toLowerCase() + " Croissant",
                (croissantType == CroissantType.BUTTER ? 2.00 : 2.25),
                (croissantType.name().charAt(0) + croissantType.name().substring(1).toLowerCase() + " Croissant")
        );
        this.croissantType = croissantType;
    }

    public CroissantType getType() {
        return croissantType;
    }

}
