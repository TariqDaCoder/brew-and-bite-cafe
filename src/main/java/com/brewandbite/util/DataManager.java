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

import com.brewandbite.model.inventory.Butter;
import com.brewandbite.model.inventory.Chocolate;
import com.brewandbite.model.inventory.CoffeeBeans;
import com.brewandbite.model.inventory.Flour;
import com.brewandbite.model.inventory.Ingredient;
import com.brewandbite.model.inventory.Milk;
import com.brewandbite.model.items.Coffee;
import com.brewandbite.model.items.Coffee.CoffeeType;
import com.brewandbite.model.items.Tea;
import com.brewandbite.model.items.Tea.TeaType;
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
        List<Map<String, Object>> coffees = (List<Map<String, Object>>) menuData.get("coffees");
        if (coffees != null) {
            for (Map<String, Object> coffee : coffees) {
                String name = (String) coffee.get("name");
                // double basePrice = ((Number) coffee.get("basePrice")).doubleValue();
                String description = (String) coffee.get("description");
                CoffeeType type = getCoffeeType(name);

                // Create coffee
                MenuItem newCoffee = new Coffee(
                    nextId++,
                    type
                );
                newCoffee.setDescription(description);
                
                allItems.add(newCoffee);
            }
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> teas = (List<Map<String, Object>>) menuData.get("teas");
        if (teas != null) {
            for (Map<String, Object> tea : teas) {
                String name = (String) tea.get("name");
                // double basePrice = ((Number) coffee.get("basePrice")).doubleValue();
                String description = (String) tea.get("description");
                TeaType type = getTeaType(name);

                // Create tea
                MenuItem newTea = new Tea(
                    nextId++,
                    type
                );
                newTea.setDescription(description);
                
                allItems.add(newTea);
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

    //get the type of tea from the name
    private static TeaType getTeaType(String typeStr) {
        TeaType teaTypeToRet = TeaType.BLACK;
        if (typeStr.equals("Black Coffee")) {
            teaTypeToRet = TeaType.BLACK;
        } else if (typeStr.equals("Green Tea")) {
            teaTypeToRet = TeaType.GREEN;
        } else if (typeStr.equals("Herbal Tea")) {
            teaTypeToRet = TeaType.HERBAL;
        }
        
        return teaTypeToRet;
    }

    //get the type of coffee from the name
    private static CoffeeType getCoffeeType(String typeStr) {
        CoffeeType coffeeTypeToRet = CoffeeType.BLACK;
        if (typeStr.equals("Black Coffee")) {
            coffeeTypeToRet = CoffeeType.BLACK;
        } else if (typeStr.equals("Latte")) {
            coffeeTypeToRet = CoffeeType.LATTE;
        } else if (typeStr.equals("Cappuccino")) {
            coffeeTypeToRet = CoffeeType.CAPPUCCINO;
        } else if (typeStr.equals("Espresso")) {
            coffeeTypeToRet = CoffeeType.ESPRESSO;
        }
        
        return coffeeTypeToRet;
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
        // Read the menu wrapper object with beverages and pastries arrays
        Type menuType = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> ingredientData = loadFromJson("inventory.json", menuType);

        if (ingredientData == null) {
            return Collections.emptyList();
        }

        List<Ingredient> allIngredients = new ArrayList<>();

        // Process ingredients
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> ingredients = (List<Map<String, Object>>) ingredientData.get("ingredients");
        if (ingredients != null) {
            for (Map<String, Object> ingredient : ingredients) {
                String name = (String) ingredient.get("name");
                int quantity = ((Number) ingredient.get("quantity")).intValue();

                System.out.println(name + " " + Integer.toString(quantity));

                if (name.equals("Milk")) {
                    allIngredients.add(new Milk(quantity));
                } else if (name.equals("Flour")) {
                    allIngredients.add(new Flour(quantity));
                } else if (name.equals("Coffee Beans")) {
                    allIngredients.add(new CoffeeBeans(quantity));
                } else if (name.equals("Chocolate")) {
                    allIngredients.add(new Chocolate(quantity));
                } else if (name.equals("Butter")) {
                    allIngredients.add(new Butter(quantity));
                }
            }
        }

        return allIngredients;
    }
}