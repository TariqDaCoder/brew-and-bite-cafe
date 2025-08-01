package com.brewandbite.util;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.brewandbite.model.inventory.Ingredient;
import com.brewandbite.model.items.Beverage;
import com.brewandbite.model.items.Cookie;
import com.brewandbite.model.items.Croissant;
import com.brewandbite.model.items.MenuItem;
import com.brewandbite.model.items.Muffin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * A unified JSON data manager that loads menu and inventory data from JSON files
 * and creates proper MenuItem and Ingredient objects.
 */
public class DataManager {

    private static final Gson gson = new Gson();
    private static final String DATA_PATH = "src/main/resources/data/";

    /**
     * Generic loader that reads a resource from the classpath under /data and
     * deserializes it to the given type. Returns null on error.
     */
    public static <T> T loadFromJson(String filename, Type typeOfT) {
        try (Reader r = new InputStreamReader(
                DataManager.class.getClassLoader()
                        .getResourceAsStream("data/" + filename))) {
            if (r == null) {
                System.err.println("Could not find resource: data/" + filename);
                return null;
            }
            return gson.fromJson(r, typeOfT);
        } catch (Exception e) {
            System.err.println("Error loading " + filename + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Saves a list of T to the specified JSON file on disk under DATA_PATH.
     * Falls back silently on error.
     */
    public static <T> void saveToJson(String filename, List<T> data) {
        try {
            String json = gson.toJson(data);
            Files.writeString(Paths.get(DATA_PATH + filename), json);
        } catch (Exception e) {
            System.err.println("Error saving " + filename + ": " + e.getMessage());
        }
    }

    /**
     * Reads the menu wrapper (with beverages & pastries) and flattens it into a
     * single List<MenuItem>. Returns empty list on null.
     */
    public static List<MenuItem> loadAllMenuItems() {
        // Read the menu wrapper object with beverages and pastries arrays
        Type menuType = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> menuData = loadFromJson("menu.json", menuType);

        if (menuData == null) {
            return Collections.emptyList();
        }

        List<MenuItem> allItems = new ArrayList<>();
        int nextId = 1;

        // Process beverages
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> beverages = (List<Map<String, Object>>) menuData.get("beverages");
        if (beverages != null) {
            for (Map<String, Object> bev : beverages) {
                String name = (String) bev.get("name");
                double basePrice = ((Number) bev.get("basePrice")).doubleValue();
                String description = (String) bev.get("description");

                // Create beverage with default medium size
                Beverage beverage = new Beverage(
                        nextId++,
                        name,
                        basePrice,
                        description,
                        Beverage.DrinkSize.MEDIUM
                );
                allItems.add(beverage);
            }
        }

        // Process pastries
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> pastries = (List<Map<String, Object>>) menuData.get("pastries");
        if (pastries != null) {
            for (Map<String, Object> pastry : pastries) {
                String name = (String) pastry.get("name");
                double basePrice = ((Number) pastry.get("basePrice")).doubleValue();

                MenuItem pastryItem = createPastryItem(nextId++, name, basePrice);
                if (pastryItem != null) {
                    allItems.add(pastryItem);
                }
            }
        }

        return allItems;
    }

    /**
     * Creates the appropriate pastry subclass based on the name.
     */
    private static MenuItem createPastryItem(int id, String name, double basePrice) {
        String lowerName = name.toLowerCase();

        if (lowerName.contains("croissant")) {
            Croissant.CroissantType type = lowerName.contains("chocolate")
                    ? Croissant.CroissantType.CHOCOLATE
                    : Croissant.CroissantType.BUTTER;
            return new Croissant(id, type);
        }
        else if (lowerName.contains("muffin")) {
            Muffin.MuffinType type = lowerName.contains("blueberry")
                    ? Muffin.MuffinType.BLUEBERRY
                    : Muffin.MuffinType.CHOCOLATE_CHIP;
            return new Muffin(id, type);
        }
        else if (lowerName.contains("cookie")) {
            Cookie.CookieType type = lowerName.contains("oatmeal")
                    ? Cookie.CookieType.OATMEAL_RAISIN
                    : Cookie.CookieType.CHOCOLATE_CHIP;
            return new Cookie(id, type);
        }

        // If we can't determine the type, skip it
        System.err.println("Unknown pastry type: " + name);
        return null;
    }

    /**
     * Reads the inventory wrapper (with ingredients array) and returns the ingredient list.
     * Returns empty list on null.
     */
    public static List<Ingredient> loadAllIngredients() {
        // Read the inventory wrapper object with ingredients array
        Type inventoryType = new TypeToken<Map<String, List<Ingredient>>>() {}.getType();
        Map<String, List<Ingredient>> inventoryData = loadFromJson("inventory.json", inventoryType);

        if (inventoryData == null || inventoryData.get("ingredients") == null) {
            return Collections.emptyList();
        }

        return inventoryData.get("ingredients");
    }
}