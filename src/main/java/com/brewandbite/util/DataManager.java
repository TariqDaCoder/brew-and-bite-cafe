package com.brewandbite.util;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.List;

import com.brewandbite.model.BeverageDTO;
import com.brewandbite.model.InventoryData;
import com.brewandbite.model.MenuDataDTO;
import com.brewandbite.model.PastryDTO;
import com.brewandbite.model.inventory.Ingredient;
import com.brewandbite.model.items.Beverage;
import com.brewandbite.model.items.Croissant;
import com.brewandbite.model.items.MenuItem;
import com.brewandbite.model.items.Muffin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * A unified JSON data manager that combines the convenience of a static facade
 * (with a fixed data directory) and the type-safety of generic loading via Gson
 * and TypeToken. It can load wrapper objects or flatten menu/inventory into
 * flat lists.
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
        // 1) Read the raw DTO wrapper
        Type dtoType = new TypeToken<MenuDataDTO>() {
        }.getType();
        MenuDataDTO dto = loadFromJson("menu.json", dtoType);
        if (dto == null) {
            return Collections.emptyList();
        }

        List<MenuItem> flat = new ArrayList<>();
        int nextId = 1;

        // 2) Convert each BeverageDTO → your concrete Beverage subclass
        for (BeverageDTO b : dto.beverages) {
            // pick a default size, or you could loop sizes too
            Beverage bev = new Beverage(
                    nextId++,
                    b.name,
                    b.basePrice,
                    b.description
            );
            flat.add(bev);
        }

        // 3) Convert each PastryDTO → a specific subclass
        for (PastryDTO p : dto.pastries) {
            MenuItem pastry;
            if (p.name.toLowerCase().contains("croissant")) {
                // assume butter unless name contains "chocolate"
                var type = p.name.toLowerCase().contains("chocolate")
                        ? Croissant.CroissantType.CHOCOLATE
                        : Croissant.CroissantType.BUTTER;
                pastry = new Croissant(nextId++, type);
            } else if (p.name.toLowerCase().contains("muffin")) {
                var type = p.name.toLowerCase().contains("blueberry")
                        ? Muffin.MuffinType.BLUEBERRY
                        : Muffin.MuffinType.CHOCOLATE_CHIP;
                pastry = new Muffin(nextId++, type);
            } else {
                // TODO: Add a fallback
                return null;
            }
            flat.add(pastry);
        }

        return flat;
    }

    /**
     * Reads the inventory wrapper (with ingredients) and returns its list.
     * Returns empty list on null.
     */
    public static List<Ingredient> loadAllIngredients() {
        Type invType = new TypeToken<InventoryData>() {
        }.getType();
        InventoryData id = loadFromJson("inventory.json", invType);
        return (id == null || id.ingredients == null)
                ? emptyList()
                : id.ingredients;
    }
}
