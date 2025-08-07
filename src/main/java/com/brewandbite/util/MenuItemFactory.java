package com.brewandbite.util;

import com.brewandbite.model.items.Coffee;
import com.brewandbite.model.items.Tea;
import com.brewandbite.model.items.Cookie;
import com.brewandbite.model.items.Muffin;
import com.brewandbite.model.items.Croissant;
import com.brewandbite.model.items.MenuItem;
import com.brewandbite.model.items.Coffee.CoffeeType;


public class MenuItemFactory {
    public MenuItemFactory() {

    }
    
    public MenuItem initializeAndGetMenuItem(MenuItem menuItem) {
        MenuItem menuItemToReturn = null;

        if (menuItem instanceof Coffee) {
            menuItemToReturn = new Coffee(0, ((Coffee)menuItem).getType());
        } else if (menuItem instanceof Tea) {
            menuItemToReturn = new Tea(0, ((Tea)menuItem).getType());
        } else if (menuItem instanceof Cookie) {
            menuItemToReturn = new Cookie(0, ((Cookie)menuItem).getType());
        } else if (menuItem instanceof Croissant) {
            menuItemToReturn = new Croissant(0, ((Croissant)menuItem).getType());
        } else if (menuItem instanceof Muffin) {
            menuItemToReturn = new Muffin(0, ((Muffin)menuItem).getType());
        }

        return menuItemToReturn;
    }


}
