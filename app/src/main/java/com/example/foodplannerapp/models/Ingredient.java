package com.example.foodplannerapp.models;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("idIngredient")
    private String id;
    @SerializedName("strDescription")
    private String description;
    @SerializedName("strType")
    private String type;
    @SerializedName("strIngredient")
    private String ingredient;


    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getIngredient() {
        return ingredient;
    }
}
