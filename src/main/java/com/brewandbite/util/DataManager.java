package com.brewandbite.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class DataManager {
    private static final Gson gson = new Gson();
    private static final String DATA_PATH = "src/main/resources/data/";

    public static <T> List<T> loadFromJson(String filename, TypeToken<List<T>> typeToken) {
        try {
            String content = Files.readString(Paths.get(DATA_PATH + filename));
            return gson.fromJson(content, typeToken.getType());
        } catch (Exception e) {
            System.out.println("Error loading " + filename + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static <T> void saveToJson(String filename, List<T> data) {
        try {
            String json = gson.toJson(data);
            Files.writeString(Paths.get(DATA_PATH + filename), json);
        } catch (Exception e) {
            System.out.println("Error saving " + filename + ": " + e.getMessage());
        }
    }
}