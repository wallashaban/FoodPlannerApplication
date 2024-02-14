package com.example.foodplannerapp.models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class MealPlanConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static Meal fromString(String value) {
        return gson.fromJson(value, Meal.class);
    }

    @TypeConverter
    public static String mealToString(Meal meal) {
        return gson.toJson(meal);
    }
}
