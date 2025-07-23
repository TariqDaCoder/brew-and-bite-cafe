package com.brewandbite.util;

import com.brewandbite.model.items.Coffee;
import com.brewandbite.model.items.Cookie;
import com.brewandbite.model.items.Muffin;
import com.brewandbite.model.items.Croissant;
import com.brewandbite.model.items.MenuItem;

public class MenuItemFactory {
    public MenuItemFactory() {

    }
    
    public MenuItem initializeAndGetMenuItem(MenuItem menuItem) {
        MenuItem menuItemToReturn = null;

        if (menuItem instanceof Coffee) {
            menuItemToReturn = new Coffee();
        } else if (menuItem instanceof Cookie) {
            menuItemToReturn = new Cookie(1, ((Cookie)menuItem).getType());
        } else if (menuItem instanceof Croissant) {
            menuItemToReturn = new Croissant(1, ((Croissant)menuItem).getType());
        } else if (menuItem instanceof Muffin) {
            menuItemToReturn = new Muffin(1, ((Muffin)menuItem).getType());
        } 

        return menuItemToReturn;
    }


}
