package com.example.foodplannerapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class IngredientsObject {
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public IngredientsObject() {
        ingredients = new ArrayList<>();
    }

    @SerializedName("meals")
    List<Ingredient> ingredients;

}
