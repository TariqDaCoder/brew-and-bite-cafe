package com.brewandbite.model.items;

public abstract class MenuItem {

    //total price for the MenuItem
    private int id;
    private double basePrice;
    private String name;
    private String description;

    /**
     * @param id unique identifier for this item
     * @param name display name
     * @param basePrice base price (before any size or customization surcharges)
     * @param description short description for manager/menu use
     */
    public MenuItem(int id, String name, double basePrice, String description) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.description = description;
    }

    //each menu item must implement this,
    //abstrace because the way we compute the
    //total price different menu items can vary
    public abstract double calculatePrice();

    // Getters
    public int getId() {
        return id;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuItem that)) {
            return false;
        }
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "MenuItem{"
                + "basePrice=" + basePrice
                + ", id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + '}';
    }

}
