package com.brewandbite.max;

public class Cookie extends Pastry {

    public enum CookieType {
        CHOCOLATE_CHIP, OATMEAL_RAISIN
    }

    private final CookieType cookieType;

    public Cookie(int id, CookieType type) {
        super(
            id,
            type == CookieType.CHOCOLATE_CHIP ? "Chocolate-Chip Cookie" : "Oatmeal-Raisin Cookie",
            1.75,
            (type == CookieType.CHOCOLATE_CHIP ? "Chocolate-Chip Cookie" : "Oatmeal-Raisin Cookie")
        );
        this.cookieType = type;
    }

    public CookieType getType() {
        return cookieType;
    }

}
