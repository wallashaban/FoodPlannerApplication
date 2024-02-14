package com.example.foodplannerapp.models;

import java.util.ArrayList;
import java.util.List;

public class CategoryObject {
    private List<Category> categories;

    public CategoryObject() {
        categories = new ArrayList<>();
    }

    public List<Category> getCategories() {
        return categories;
    }
}
