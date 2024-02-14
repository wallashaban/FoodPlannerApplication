package com.example.foodplannerapp.models;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("strCategory")
    private String category;
    @SerializedName("idCategory")
    private String id;
    @SerializedName("strCategoryDescription")
    private String description;
    @SerializedName("strCategoryThumb")
    private String thumb;

    public String getCategory() {
        return category;
    }
}
