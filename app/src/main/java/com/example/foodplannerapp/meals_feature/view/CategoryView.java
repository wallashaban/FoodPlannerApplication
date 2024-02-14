package com.example.foodplannerapp.meals_feature.view;

import com.example.foodplannerapp.models.Category;

import java.util.List;

public interface CategoryView {
    public void showData(List<Category> categories);
    public void showErrorMessage(String errorMessage);
}
