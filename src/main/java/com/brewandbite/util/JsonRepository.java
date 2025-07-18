package com.brewandbite.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * A generic JSON repository for loading and saving lists of any type. Uses Gson
 * under the hood and TypeToken to preserve generic type information.
 *
 * @param <T> the element type of the list to load/save
 */
public class JsonRepository<T> {

    private final Gson gson;
    private final Type listType;

    /**
     * Construct a repository for the given class type.
     *
     * @param clazz the class of the elements
     */
    public JsonRepository(Class<T> clazz) {
        // Pretty printing for human-readable JSON
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        // Create a Type representing List<T>
        this.listType = TypeToken.getParameterized(List.class, clazz).getType();
    }

    /**
     * Load a list of T from the given JSON file path.
     *
     * @param path filesystem path to JSON file
     * @return an unmodifiable List<T>, or empty list on error
     */
    public List<T> load(String path) {
        try (FileReader reader = new FileReader(path)) {
            List<T> data = gson.fromJson(reader, listType);
            return data != null ? Collections.unmodifiableList(data) : Collections.emptyList();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Save the given list of T to the specified JSON file path.
     *
     * @param path filesystem path to write JSON
     * @param data the list of elements to save
     */
    public void save(String path, List<T> data) {
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(data, listType, writer);
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
    }
}
