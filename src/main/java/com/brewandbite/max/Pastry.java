package com.brewandbite.max;

public class Pastry extends MenuItem {
    public Pastry(float price, String itemName) {
        super(price, itemName);
    }

    @Override
    float calculatePrice() {
        float totalPrice = this.price;
        return totalPrice;
    }
}