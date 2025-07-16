package com.brewandbite.max;


public abstract class MenuItem {
    //total price for the MenuItem
    float price;
    String itemName;

    public MenuItem(float price, String itemName) {
        this.price = price;
        this.itemName = itemName;
    }

    //each menu item must implement this,
    //abstrace because the way we compute the
    //total price different menu items can vary
    abstract float calculatePrice();

    float getPrice() {
        return this.price;
    }

    void setPrice(float newPrice) {
        this.price = newPrice;
    }
}