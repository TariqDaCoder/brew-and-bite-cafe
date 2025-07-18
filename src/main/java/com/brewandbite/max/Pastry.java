package com.brewandbite.max;

import com.brewandbite.model.MenuItem;

public abstract class Pastry extends MenuItem {

    protected Pastry(int id,
            String name,
            double basePrice,
            String description) {
        super(id, name, basePrice, description);

    }

    @Override
    public double calculatePrice() {
        return getBasePrice();
    }

}
